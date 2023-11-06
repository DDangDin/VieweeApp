package com.capstone.vieweeapp.navigation.graph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.capstone.vieweeapp.navigation.Screen
import com.capstone.vieweeapp.presentation.view.feedback.FeedbackScreenForHome
import com.capstone.vieweeapp.presentation.view.home.HomeScreen
import com.capstone.vieweeapp.presentation.view.interview.InterviewScreen
import com.capstone.vieweeapp.presentation.view.profile.ProfileScreen
import com.capstone.vieweeapp.presentation.viewmodel.FeedbackForHomeViewModel
import com.capstone.vieweeapp.presentation.viewmodel.HomeViewModel
import com.capstone.vieweeapp.presentation.viewmodel.ProfileViewModel
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.utils.Constants
import com.capstone.vieweeapp.utils.CustomSharedPreference
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startSelectResume: () -> Unit,
    onStartReInterview: (Int) -> Unit
) {

    val context = LocalContext.current
    val homeViewModel: HomeViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()

    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(
            route = Screen.Home.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            val interviewResultsState = homeViewModel.interviewResultsState.collectAsState()

            LaunchedEffect(Unit) {
                homeViewModel.getInterviewResults()
                if (CustomSharedPreference(context).isContain(Constants.USER_SHARED_PREFERENCE)) {
                    val username =
                        CustomSharedPreference(context).getUserPrefs(Constants.USER_SHARED_PREFERENCE)
                    homeViewModel.updateUsername(username)
                }
            }

            HomeScreen(
                searchText = homeViewModel.searchText.value,
                onSearchTextChanged = homeViewModel::onSearchTextChanged,
                username = homeViewModel.username.value,
                interviewResultsState = interviewResultsState.value,
                openInterviewResult = { index ->
                    navController.navigate("${Screen.FeedbackForHomeScreen.route}/$index")
                },
                onMenuClick = {
                    navController.navigate(Screen.Profile.route) {
                        navController.graph.startDestinationRoute?.let {
                            // 첫번째 화면만 스택에 쌓이게 -> 백버튼 클릭 시 첫번째 화면으로 감
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true  // 화면 인스턴스 하나만 만들어지게
                        restoreState = true     // 버튼을 재클릭했을 때 이전 상태가 남아있게
                    }
                }
            )
        }

        composable(
            route = "${Screen.FeedbackForHomeScreen.route}/{index}",
            arguments = listOf(
                navArgument("index") {
                    type = NavType.IntType
                }
            ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Up
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.Down
                )
            }
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Screen.Home.route)
            }

            val feedbackForHomeViewModel: FeedbackForHomeViewModel = hiltViewModel()

            val interviewResultState = homeViewModel.interviewResultsState.collectAsStateWithLifecycle()
            val interviewResultIndex = backStackEntry.arguments?.getInt("index") ?: 0

            val reInterviewState = feedbackForHomeViewModel.reInterviewState.collectAsStateWithLifecycle()

            // 만약 피드백 데이터 삭제 시, DB 작업 딜레이가 있으므로 인덱스 값이 어긋남
            // 이 오류는 마지막 피드백 데이터만 해당되고 그 외에는 인덱스 허용범위이므로 오류 발생하지 않음
            if (interviewResultState.value.interviewResults.size > interviewResultIndex) {
                FeedbackScreenForHome(
                    interviewResult = interviewResultState.value.interviewResults[interviewResultIndex],
                    onNavigateHome = { navController.popBackStack() },
                    uiEvent = feedbackForHomeViewModel::uiEvent,
                    eachReInterviewState = reInterviewState.value,
                    isReInterview = interviewResultState.value.interviewResults[interviewResultIndex].etc == "1",
                    onStartReInterview = { id ->
//                        feedbackForHomeViewModel.requestServerForReInterview()
                        onStartReInterview(id)
                        scope.launch {
                            delay(1500) // 바로 창이 사라 지는 부자연스러움을 위한 딜레이
                            navController.popBackStack()
                            /*TODO 임시*/
                            // 밑에 코드 추가 이유:
                            // 재면접 후 피드백 화면 에서 홈버튼 클릭 시(popBackStack()호출)
                            // 첫번째 면접과 달리 홈 화면으로 나가지는데 이때 재면접 피드백이 업데이트 바로 안됨...
                            navController.navigate(Screen.Interview.route) {
                                navController.graph.startDestinationRoute?.let {
                                    // 첫번째 화면만 스택에 쌓이게 -> 백버튼 클릭 시 첫번째 화면으로 감
                                    popUpTo(it) { saveState = true }
                                }
                                launchSingleTop = true  // 화면 인스턴스 하나만 만들어지게
                                restoreState = true     // 버튼을 재클릭했을 때 이전 상태가 남아있게
                            }
                        }
                    },
                    interviewResultIndex = interviewResultIndex
                )
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(50.dp),
                        strokeWidth = 2.dp,
                        color = VieweeColorMain
                    )
                }
            }
        }

        composable(
            route = Screen.Interview.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            InterviewScreen(
                startSelectResume = startSelectResume
            )
        }

        composable(
            route = Screen.Profile.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {

            val profileViewModel: ProfileViewModel = hiltViewModel()

            var (name, onNameChanged) = remember { mutableStateOf(homeViewModel.username.value) }

            LaunchedEffect(key1 = Unit) {
                profileViewModel.getResumes()
            }

            val resumes = profileViewModel.resumes.collectAsStateWithLifecycle()

            ProfileScreen(
                name = name,
                onNameChanged = onNameChanged,
                saveName = { username ->
                    CustomSharedPreference(context).setUserPrefs(
                        Constants.USER_SHARED_PREFERENCE,
                        username.trim()
                    )
                    homeViewModel.updateUsername(username)
                    name = ""
                },
                resumes = resumes.value
            )
        }
    }
}