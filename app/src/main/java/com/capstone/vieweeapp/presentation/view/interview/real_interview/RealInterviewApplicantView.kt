package com.capstone.vieweeapp.presentation.view.interview.real_interview

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import org.opencv.core.Mat
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun RealInterviewApplicantView(
    modifier: Modifier = Modifier,
    onImageAnalysis: (ImageProxy) -> Unit,
    startListening: () -> Unit
) {

    // 이미지 캡처 시 필요한 변수,
    // 사용하게 된다면 onDestroy 생명주기나 면접화면이 끝났을 때
    // cameraExecutor.shutdown() -> 필요
    val cameraExecutor = Executors.newSingleThreadExecutor()

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }

    // camera settings
    var lensFacing by remember {
        mutableStateOf(CameraSelector.LENS_FACING_FRONT)
    }
    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    val imageAnalysis = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()
    imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
        // 로직 처리 끝나고 반드시

        // open cv 적용 할 곳
        onImageAnalysis(imageProxy)
//        Log.d(
//            "ApplicantScreen" + "_onImageAnalysis",
//            "mRgba: ${mRgba.width()}, ${mRgba.height()}"
//        )

        imageProxy.close()
    }

    LaunchedEffect(key1 = lensFacing) {
        Log.d("LensFacing_Log", "Change LensFacing")
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture,
            imageAnalysis
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent, RoundedCornerShape(15.dp))
            .clipToBounds(),
        contentAlignment = Alignment.Center
    ) {
        // CameraView, STT(VoiceToText)
        AndroidView(
            factory = { previewView.also {
//                it.clipToOutline = true
                it.clipChildren = false
            } },
            modifier = Modifier
                .matchParentSize()
                .align(Alignment.Center),
//                .verticalScroll(scrollState)
        )

        // --- Test Block ---
        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp)
                .background(VieweeColorMain)
                .clickable { startListening() },
            imageVector = Icons.Filled.Mic,
            contentDescription = "말하기",
            tint = Color.White
        )
    }
}

private fun takePhoto(
    filenameFormat: String,
    imageCapture: ImageCapture,
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {

    val photoFile = File(
        outputDirectory,
        SimpleDateFormat(filenameFormat, Locale.KOREA).format(System.currentTimeMillis()) + ".jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            val saveUri = Uri.fromFile(photoFile)
            onImageCaptured(saveUri)
        }

        override fun onError(exception: ImageCaptureException) {
            Log.e("TakePhoto Error", "TakePhotoError", exception)
            onError(exception)
        }
    })
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }

@androidx.compose.ui.tooling.preview.Preview
@Composable
fun RealInterviewApplicantViewPreview() {
    RealInterviewApplicantView(
        onImageAnalysis = {},
        startListening = {}
    )
}