package com.capstone.vieweeapp

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.capstone.vieweeapp.navigation.nav.VieweeBottomNavigation
import com.capstone.vieweeapp.ui.theme.VieweeAppTheme
import com.capstone.vieweeapp.utils.Constants
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.OpenCVLoader
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity(LOG)"

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val interviewActivityIntent = Intent(this, InterviewActivity::class.java)

        setContent {

            VieweeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Text(text = "Hello!")

//                    val navController = rememberNavController()
                    val navController = rememberAnimatedNavController()
                    VieweeBottomNavigation(
                        navController = navController,
                        startSelectResume = {
                            interviewActivityIntent.putExtra(
                                Constants.PUT_EXTRA_IS_RE_INTERVIEW,
                                false
                            )
                            startActivity(interviewActivityIntent)
                        },
                        onStartReInterview = { id ->
                            interviewActivityIntent.putExtra(
                                Constants.PUT_EXTRA_IS_RE_INTERVIEW,
                                true
                            )
                            interviewActivityIntent.putExtra(
                                Constants.PUT_EXTRA_PREVIOUS_INTERVIEW_RESULT_INDEX,
                                id
                            )
                            startActivity(interviewActivityIntent)
                        }
                    )
                }
            }
        }
    }
}