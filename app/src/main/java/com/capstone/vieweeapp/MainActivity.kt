package com.capstone.vieweeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.capstone.vieweeapp.navigation.nav.VieweeBottomNavigation
import com.capstone.vieweeapp.ui.theme.VieweeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.OpenCVLoader

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity(LOG)"

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

                    val navController = rememberNavController()
                    VieweeBottomNavigation(
                        navController = navController,
                        startSelectResume = {
                            startActivity(interviewActivityIntent)
                        }
                    )
                }
            }
        }
    }
}