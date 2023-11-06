package com.capstone.vieweeapp.navigation.graph

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.capstone.vieweeapp.navigation.Screen
import com.capstone.vieweeapp.presentation.event.RealInterviewUiEvent
import com.capstone.vieweeapp.presentation.view.feedback.FeedbackScreen
import com.capstone.vieweeapp.presentation.view.interview.input_profile.InputProfileFinishScreen
import com.capstone.vieweeapp.presentation.view.interview.input_profile.InputProfileScreen1
import com.capstone.vieweeapp.presentation.view.interview.input_profile.InputProfileScreen2
import com.capstone.vieweeapp.presentation.view.interview.input_profile.InputProfileScreen3
import com.capstone.vieweeapp.presentation.view.interview.real_interview.RealInterviewScreen
import com.capstone.vieweeapp.presentation.view.interview.real_interview.loading.InterviewLoadingScreen
import com.capstone.vieweeapp.presentation.view.interview.real_interview.prepare.InterviewPrepareScreen
import com.capstone.vieweeapp.presentation.view.interview.select_resume.SelectResumeScreen
import com.capstone.vieweeapp.presentation.viewmodel.InputProfileViewModel
import com.capstone.vieweeapp.presentation.viewmodel.InterviewViewModel
import com.capstone.vieweeapp.presentation.viewmodel.TextVoiceViewModel
import com.capstone.vieweeapp.utils.opencv.FacialExpressionRecognition

@Composable
fun InterviewNavigationGraph(
    navController: NavHostController,
    onFinish: () -> Unit,
    interviewViewModel: InterviewViewModel,
    inputProfileViewModel: InputProfileViewModel,
    requestPermissions: () -> Unit,
    facialExpressionRecognition: FacialExpressionRecognition,
    isReInterview: Boolean,
    previousInterviewResultId: Int
) {

    LaunchedEffect(key1 = Unit) {
        if (isReInterview) {
            navController.navigate(Screen.RealInterviewNavigation.route)
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.SelectResume.route
    ) {
        composable(route = Screen.SelectResume.route) {

            LaunchedEffect(key1 = Unit) {
                interviewViewModel.getResumes()
            }

            val resumeState = interviewViewModel.resumeState.collectAsState()

            SelectResumeScreen(
                resumeState = resumeState.value,
                onFinish = onFinish,
                onAddResume = { navController.navigate(Screen.InputProfileNavigation.route) },
                selectResumeUiEvent = interviewViewModel::selectResumeUiEvent,
                prepareInterview = { navController.navigate(Screen.RealInterviewNavigation.route) }
            )
        }

        navigation(
            route = Screen.RealInterviewNavigation.route,
            startDestination = Screen.InterviewPrepare.route
        ) {
            composable(route = Screen.InterviewPrepare.route) {

                LaunchedEffect(Unit) {
                    requestPermissions()
                }

                BackHandler(
                    onBack = {
                        onFinish()
                    }
                )

                InterviewPrepareScreen(
                    onPopUp = {
                        if (isReInterview) {
                            onFinish()
                        } else {
                            navController.popBackStack(
                                route = Screen.RealInterviewNavigation.route,
                                inclusive = true
                            )
                        }
                    },
                    startReadyToInterview = {
                        if (isReInterview) {
                            interviewViewModel.createLocalQuestions(previousInterviewResultId)
                        } else {
                            interviewViewModel.createQuestions()
                        }
                        navController.navigate(Screen.InterviewLoading.route) {
                            popUpTo(Screen.InterviewPrepare.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(route = Screen.InterviewLoading.route) {

                val questionsState = interviewViewModel.questionsState.collectAsState()
                val hasPermissionState = interviewViewModel.hasPermissionsForInterview

                LaunchedEffect(hasPermissionState) {
                    if (!hasPermissionState.value) {
                        requestPermissions()
                    }
                }

                InterviewLoadingScreen(
                    questionsState = questionsState.value,
                    backToMain = onFinish,
                    startInterview = {
                        interviewViewModel.realInterviewUiEvent(RealInterviewUiEvent.StartInterview)
                        navController.navigate(Screen.RealInterview.route) {
                            popUpTo(Screen.InterviewLoading.route) {
                                inclusive = true
                            }
                        }
                    },
                    onPopBackStack = { navController.popBackStack() }
                )
            }

            composable(route = Screen.RealInterview.route) {

                val textVoiceSpeechViewModel: TextVoiceViewModel = hiltViewModel()
                val interviewTime = interviewViewModel.interviewTime.collectAsState()
                val interviewerTurnState = interviewViewModel.interviewerTurnState.collectAsState()
                val questionsState = interviewViewModel.questionsState.collectAsState()
                val interviewFinishState = interviewViewModel.finishState.collectAsState()

                LaunchedEffect(key1 = Unit) {
                    Log.d(
                        "RealInterview_Log",
                        "${interviewViewModel.questionsState.value.questions}"
                    )
                }


                RealInterviewScreen(
                    interviewerTurnState = interviewerTurnState.value,
                    time = interviewTime.value,
                    onStop = onFinish,
                    uiEvent = interviewViewModel::realInterviewUiEvent,
                    recognizeImage = { mRgba, mGray, rotationDegrees ->
                        facialExpressionRecognition.recognizeImage(
                            mRgba,
                            interviewViewModel,
                            rotationDegrees
                        )
                    },
                    textVoiceSpeechViewModel = textVoiceSpeechViewModel,
                    questionsState = questionsState.value,
                    interviewFinishState = interviewFinishState.value,
                    onNavigateFeedbackScreen = {
                        navController.navigate(Screen.FeedbackNavigation.route) {
                            popUpTo(Screen.RealInterviewNavigation.route) {
                                inclusive = true
                            }
                        }
                    },
                    startFeedback = {
                        Log.d("reInterview_Log", "isReInterview: $isReInterview")
                        interviewViewModel.realInterviewUiEvent(
                            RealInterviewUiEvent.StartFeedback(
                                isReInterview = isReInterview,
                                previousInterviewResultId = previousInterviewResultId
                            )
                        )
                    },
                    isReInterview = isReInterview
                )
            }
        }

        navigation(
            route = Screen.FeedbackNavigation.route,
            startDestination = Screen.Feedback.route
        ) {
            composable(route = Screen.Feedback.route) {

                BackHandler(
                    onBack = {
                        onFinish()
                    }
                )

                val feedbackState = interviewViewModel.feedbackState.collectAsState()
                val questionsState = interviewViewModel.questionsState.collectAsState()
                val answerList = interviewViewModel.answerList
                val emotionList = interviewViewModel.emotionList
                val textSentimentList = interviewViewModel.textSentimentList

                FeedbackScreen(
                    questionState = questionsState.value,
                    answerList = answerList,
                    feedbackState = feedbackState.value,
                    emotionList = emotionList,
                    textSentimentList = textSentimentList,
                    onNavigateHome = onFinish,
                    saveInterviewResult = {
                        if (isReInterview) {
                            feedbackState.value.previousInterviewResult?.let { it1 ->
                                interviewViewModel.saveReInterviewResult(
                                    it1
                                )
                            }
                        } else {
                            interviewViewModel.saveInterviewResult()
                        }
                    },
                    isReInterview = isReInterview
                )
            }
        }

        navigation(
            route = Screen.InputProfileNavigation.route,
            startDestination = Screen.InputProfileFirst.route
        ) {

            composable(route = Screen.InputProfileFirst.route) {
                InputProfileScreen1(
                    inputProfileViewModel = inputProfileViewModel,
                    onNavigateNext = { navController.navigate(Screen.InputProfileSecond.route) },
                    onExit = {
                        inputProfileViewModel.initializeInputData()
                        navController.popBackStack(
                            route = Screen.InputProfileNavigation.route,
                            inclusive = true
                        )
                    }
                )
            }
            composable(route = Screen.InputProfileSecond.route) {
                InputProfileScreen2(
                    inputProfileViewModel = inputProfileViewModel,
                    onNavigateNext = { navController.navigate(Screen.InputProfileThird.route) },
                    onExit = {
                        inputProfileViewModel.initializeInputData()
                        navController.popBackStack(
                            route = Screen.InputProfileNavigation.route,
                            inclusive = true
                        )
                    }
                )
            }
            composable(route = Screen.InputProfileThird.route) {
                InputProfileScreen3(
                    onNavigateNext = { navController.navigate(Screen.InputProfileFinish.route) },
                    inputProfileViewModel = inputProfileViewModel,
                    onExit = {
                        inputProfileViewModel.initializeInputData()
                        navController.popBackStack(
                            route = Screen.InputProfileNavigation.route,
                            inclusive = true
                        )
                    }
                )
            }

            composable(route = Screen.InputProfileFinish.route) {
                InputProfileFinishScreen(
                    onFinish = {
                        inputProfileViewModel.initializeInputData()
                        navController.popBackStack(
                            route = Screen.InputProfileNavigation.route,
                            inclusive = true
                        )
                    }
                )
            }
        }
    }
}