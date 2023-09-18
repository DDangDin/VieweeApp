package com.capstone.vieweeapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.capstone.vieweeapp.utils.opencv.FacialExpressionRecognition
import com.capstone.vieweeapp.navigation.graph.InterviewNavigationGraph
import com.capstone.vieweeapp.presentation.viewmodel.InputProfileViewModel
import com.capstone.vieweeapp.presentation.viewmodel.InterviewViewModel
import com.capstone.vieweeapp.ui.theme.VieweeAppTheme
import com.capstone.vieweeapp.utils.VieweePermissions
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.core.Core
import java.io.IOException

@AndroidEntryPoint
class InterviewActivity : ComponentActivity() {

    private val TAG = "InterviewActivity"

    private val shouldShowCamera = mutableStateOf(false)
    private val shouldRecordAudio = mutableStateOf(false)

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        var permissionGranted = true

        permissions.entries.forEach {
            if (it.key in VieweePermissions.REQUIRED_PERMISSIONS && !it.value) {
                permissionGranted = false
            }
        }

        if (!permissionGranted) {
            // permission denied
            Log.d(TAG, "permission denied")
        } else {
            shouldShowCamera.value = true
            shouldRecordAudio.value = true
            Log.d(TAG, "permission granted")
        }
    }

    // For OpenCV
    private lateinit var facialExpressionRecognition: FacialExpressionRecognition
    private val mLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                SUCCESS -> {
                    run {
                        Log.i(TAG, "OpenCv Is loaded")
                    }
                    run { super.onManagerConnected(status) }
                }

                else -> {
                    Log.i(TAG, "OpenCv Is Not loaded")
                    super.onManagerConnected(status)
                }
            }
        }
    }

    private fun requestPermissions() {

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            shouldShowCamera.value = true
        } else if (
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.CAMERA
            )
        ) {
            ActivityCompat.requestPermissions(
                this,
                kotlin.arrayOf(android.Manifest.permission.CAMERA),
                VieweePermissions.REQUEST_CODE
            )
        }

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            shouldRecordAudio.value = true
        } else if (
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.RECORD_AUDIO
            )
        ) {
            ActivityCompat.requestPermissions(
                this,
                kotlin.arrayOf(android.Manifest.permission.RECORD_AUDIO),
                VieweePermissions.REQUEST_CODE
            )
        }

        if (shouldShowCamera.value && shouldRecordAudio.value) {
            // permissions granted
        } else {
            requestPermissionLauncher.launch(VieweePermissions.REQUIRED_PERMISSIONS)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- for OpenCV Settings (start) ---
        // Load in HomeActivity
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
        // --- for OpenCV Settings (finish) ---

        setContent {

            val interviewViewModel: InterviewViewModel = hiltViewModel()
            val inputProfileViewModel: InputProfileViewModel = hiltViewModel()

            // 나중에 권한 두 번 거부 이후에도 요청 가능하게끔 수정하기
            LaunchedEffect(shouldShowCamera.value) {
                Log.d(TAG, "camera_permission: ${shouldShowCamera.value}")
                interviewViewModel.updatePermissions(
                    Manifest.permission.CAMERA,
                    shouldShowCamera.value
                )
            }
            LaunchedEffect(shouldRecordAudio.value) {
                Log.d(TAG, "mic_permission: ${shouldRecordAudio.value}")
                interviewViewModel.updatePermissions(
                    Manifest.permission.RECORD_AUDIO,
                    shouldRecordAudio.value
                )
            }

            VieweeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    InterviewNavigationGraph(
                        navController = navController,
                        onFinish = { finish() },
                        interviewViewModel = interviewViewModel,
                        inputProfileViewModel = inputProfileViewModel,
                        requestPermissions = { requestPermissions() },
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (OpenCVLoader.initDebug()) {
            //if load success
            Log.d(TAG, "Opencv initialization is done")
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)

            try {
                // input size of model is 48
                val inputSize = 48
                facialExpressionRecognition = FacialExpressionRecognition(
                    assets, this@InterviewActivity,
                    "model300.tflite", inputSize
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            //if not loaded
            Log.d(TAG, "Opencv is not loaded. try again")
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, mLoaderCallback)
        }
    }

    override fun onDestroy() {
//        cameraExecutor.shutdown()
//        if (textVoiceSpeechViewModel != null) {
//            textVoiceSpeechViewModel.onDestroy()
//        }
        super.onDestroy()
    }
}