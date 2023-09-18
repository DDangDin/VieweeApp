package com.capstone.vieweeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.capstone.vieweeapp.navigation.graph.InterviewNavigationGraph
import com.capstone.vieweeapp.ui.theme.VieweeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InterviewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VieweeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()

                    InterviewNavigationGraph(
                        navController = navController,
                        onBackStack = { finish() }
                    )
                }
            }
        }
    }
}