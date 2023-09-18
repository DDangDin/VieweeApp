package com.capstone.vieweeapp.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.capstone.vieweeapp.navigation.Screen
import com.capstone.vieweeapp.presentation.view.interview.input_profile.InputProfileFinishScreen
import com.capstone.vieweeapp.presentation.view.interview.input_profile.InputProfileScreen1
import com.capstone.vieweeapp.presentation.view.interview.input_profile.InputProfileScreen2
import com.capstone.vieweeapp.presentation.view.interview.input_profile.InputProfileScreen3
import com.capstone.vieweeapp.presentation.view.interview.select_resume.SelectResumeScreen
import com.capstone.vieweeapp.presentation.viewmodel.InputProfileViewModel
import com.capstone.vieweeapp.presentation.viewmodel.InterviewViewModel
import com.capstone.vieweeapp.utils.Constants

@Composable
fun InterviewNavigationGraph(
    navController: NavHostController,
    onBackStack: () -> Unit,
) {

    val interviewViewModel: InterviewViewModel = hiltViewModel()
    val inputProfileViewModel: InputProfileViewModel = hiltViewModel()

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
                onBackStack = onBackStack,
                onAddResume = { navController.navigate(Screen.InputProfileNavigation.route) },
                prepareInterview = {  }
            )
        }

        navigation(
            route = Screen.InputProfileNavigation.route,
            startDestination = Screen.InputProfileFirst.route
        ) {

            composable(route = Screen.InputProfileFirst.route) {
                InputProfileScreen1(
                    inputProfileViewModel = inputProfileViewModel,
                    onNavigateNext = { navController.navigate(Screen.InputProfileSecond.route) },
                    onExit = {  }
                )
            }
            composable(route = Screen.InputProfileSecond.route) {
                InputProfileScreen2(
                    inputProfileViewModel = inputProfileViewModel,
                    onNavigateNext = { navController.navigate(Screen.InputProfileThird.route) },
                    onExit = {  }
                )
            }
            composable(route = Screen.InputProfileThird.route) {
                InputProfileScreen3(
                    onNavigateNext = { navController.navigate(Screen.InputProfileFinish.route) },
                    inputProfileViewModel = inputProfileViewModel,
                    onExit = {  }
                )
            }

            composable(route = Screen.InputProfileFinish.route) {
                InputProfileFinishScreen(
                    onFinish = {  }
                )
            }
        }
    }
}