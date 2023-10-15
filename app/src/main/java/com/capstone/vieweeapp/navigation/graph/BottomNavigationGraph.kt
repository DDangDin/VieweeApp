package com.capstone.vieweeapp.navigation.graph

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.capstone.vieweeapp.navigation.Screen
import com.capstone.vieweeapp.presentation.event.HomeUiEvent
import com.capstone.vieweeapp.presentation.view.calendar.CalendarScreen
import com.capstone.vieweeapp.presentation.view.feedback.FeedbackScreenForHome
import com.capstone.vieweeapp.presentation.view.home.HomeScreen
import com.capstone.vieweeapp.presentation.view.interview.InterviewScreen
import com.capstone.vieweeapp.presentation.view.profile.ProfileScreen
import com.capstone.vieweeapp.presentation.viewmodel.FeedbackForHomeViewModel
import com.capstone.vieweeapp.presentation.viewmodel.HomeViewModel
import com.capstone.vieweeapp.presentation.viewmodel.InterviewViewModel
import com.capstone.vieweeapp.presentation.viewmodel.ProfileViewModel
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.utils.Constants
import com.capstone.vieweeapp.utils.CustomSharedPreference

@Composable
fun BottomNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startSelectResume: () -> Unit
) {

    val context = LocalContext.current
    val homeViewModel: HomeViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            val interviewResultsState = homeViewModel.interviewResultsState.collectAsState()

            LaunchedEffect(Unit) {
                homeViewModel.getInterviewResults()
                if (CustomSharedPreference(context).isContain(Constants.USER_SHARED_PREFERENCE)) {
                    val username = CustomSharedPreference(context).getUserPrefs(Constants.USER_SHARED_PREFERENCE)
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
            )
        }

        composable(
            route = "${Screen.FeedbackForHomeScreen.route}/{index}",
            arguments = listOf(
                navArgument("index") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Screen.Home.route)
            }

            val feedbackForHomeViewModel: FeedbackForHomeViewModel = hiltViewModel()

            val interviewResultState = homeViewModel.interviewResultsState.collectAsState()
            val interviewResultIndex = backStackEntry.arguments?.getInt("index") ?: 0

            val reInterviewState = feedbackForHomeViewModel.reInterviewState.collectAsState()

            // 만약 피드백 데이터 삭제 시, DB 작업 딜레이가 있으므로 인덱스 값이 어긋남
            // 이 오류는 마지막 피드백 데이터만 해당되고 그 외에는 인덱스 허용범위이므로 오류 발생하지 않음
            if (interviewResultState.value.interviewResults.size > interviewResultIndex) {
                FeedbackScreenForHome(
                    interviewResult = interviewResultState.value.interviewResults[interviewResultIndex],
                    onNavigateHome = { navController.popBackStack() },
                    name = homeViewModel.username.value,
                    uiEvent = feedbackForHomeViewModel::uiEvent,
                    reInterviewState = reInterviewState.value
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

        composable(route = Screen.Interview.route) {
            InterviewScreen(
                startSelectResume = startSelectResume
            )
        }

        composable(route = Screen.Calendar.route) {
            CalendarScreen()
        }

        composable(route = Screen.Profile.route) {

            val profileViewModel: ProfileViewModel = viewModel()

            var (text, onTextChanged) = remember { mutableStateOf("") }

            ProfileScreen(
                text = text,
                onTextChanged = onTextChanged,
                saveName = {
                    val username = text.trim()
                    CustomSharedPreference(context).setUserPrefs(Constants.USER_SHARED_PREFERENCE, username)
                    homeViewModel.updateUsername(username)
                    text = ""
                }
            )
        }
    }
}