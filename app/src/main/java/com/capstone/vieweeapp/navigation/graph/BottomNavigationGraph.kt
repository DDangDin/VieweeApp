package com.capstone.vieweeapp.navigation.graph

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.capstone.vieweeapp.navigation.Screen
import com.capstone.vieweeapp.presentation.view.calendar.CalendarScreen
import com.capstone.vieweeapp.presentation.view.home.HomeScreen
import com.capstone.vieweeapp.presentation.view.interview.InterviewScreen
import com.capstone.vieweeapp.presentation.view.profile.ProfileScreen

@Composable
fun BottomNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen()
        }

        composable(route = Screen.Interview.route) {
            InterviewScreen()
        }

        composable(route = Screen.Calendar.route) {
            CalendarScreen()
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen()
        }
    }
}