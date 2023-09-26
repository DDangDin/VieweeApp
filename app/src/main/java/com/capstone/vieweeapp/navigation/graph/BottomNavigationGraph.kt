package com.capstone.vieweeapp.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

            FeedbackScreenForHome(
                interviewResult = interviewResultState.value.interviewResults[interviewResultIndex],
                onNavigateHome = { navController.popBackStack() },
                name = homeViewModel.username.value,
                uiEvent = feedbackForHomeViewModel::uiEvent,
            )
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