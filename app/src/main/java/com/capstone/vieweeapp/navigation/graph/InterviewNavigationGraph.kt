package com.capstone.vieweeapp.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.capstone.vieweeapp.navigation.Screen
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

@Composable
fun InterviewNavigationGraph(
    navController: NavHostController,
    onFinish: () -> Unit,
    interviewViewModel: InterviewViewModel,
    inputProfileViewModel: InputProfileViewModel,
    requestPermissions: () -> Unit,
) {

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

                InterviewPrepareScreen(
                    onPopUp = {
                        navController.popBackStack(
                            route = Screen.RealInterviewNavigation.route,
                            inclusive = true
                        )
                    },
                    startReadyToInterview = {
                        interviewViewModel.createQuestions()
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
                        navController.navigate(Screen.RealInterview.route) {
                            popUpTo(Screen.InterviewLoading.route) {
                                inclusive = true
                            }
                        }
                    },
                    onPopBackStack = {  }
                )
            }

            composable(route = Screen.RealInterview.route) {
                RealInterviewScreen()
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