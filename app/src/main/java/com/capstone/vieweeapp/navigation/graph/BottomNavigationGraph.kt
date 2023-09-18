package com.capstone.vieweeapp.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.capstone.vieweeapp.navigation.Screen
import com.capstone.vieweeapp.presentation.view.calendar.CalendarScreen
import com.capstone.vieweeapp.presentation.view.home.HomeScreen
import com.capstone.vieweeapp.presentation.view.interview.InterviewScreen
import com.capstone.vieweeapp.presentation.view.profile.ProfileScreen
import com.capstone.vieweeapp.presentation.viewmodel.HomeViewModel
import com.capstone.vieweeapp.utils.Constants

@Composable
fun BottomNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startSelectResume: () -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            val homeViewModel: HomeViewModel = viewModel()

            HomeScreen(
                searchText = homeViewModel.searchText.value,
                onSearchTextChanged = homeViewModel::onSearchTextChanged,
                username = "김길동", // SharedPreference 사용 예정
                interviewResults = Constants.getInterviewResultList()
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
            ProfileScreen()
        }
    }
}