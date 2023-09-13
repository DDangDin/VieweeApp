package com.capstone.vieweeapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.capstone.vieweeapp.ui.theme.VieweeAppTheme
import org.opencv.android.OpenCVLoader

class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity(LOG)"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isInitialized = OpenCVLoader.initDebug()
        Log.d(TAG, "OpenCV_initDebug(): $isInitialized")

        setContent {
            VieweeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}