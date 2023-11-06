package com.capstone.vieweeapp.presentation.view.interview.real_interview

import android.content.Context
import android.graphics.Rect
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.presentation.view.feedback.ReFeedbackBadge
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.ui.theme.noToSansKr
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
    startListening: () -> Unit,
    text: String,
    onTextChanged: (String) -> Unit,
    isInterviewerTurn: Boolean,
    isReInterview: Boolean
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

    var size by remember { mutableStateOf(IntSize.Zero) }

    var isMicClick by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = isInterviewerTurn) {
        if (isInterviewerTurn) {
            isMicClick = false
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent, RoundedCornerShape(15.dp))
            .clipToBounds()
            .onSizeChanged { size = it },
        contentAlignment = Alignment.Center
    ) {

        // CameraView, STT(VoiceToText)
        AndroidView(
            factory = { previewView },
            modifier = Modifier
                .then(
                    with(LocalDensity.current) {
                        Modifier.size(
                            width = size.width.toDp(),
                            height = size.height.toDp()
                        )
                    }
                )
                .align(Alignment.Center),
            update = {
                it.clipToOutline = true
                it.clipChildren = false
            }
        )

        if (isReInterview) {
            ReFeedbackBadge(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp),
                count = "0",
                backgroundColor = VieweeColorMain,
                textColor = Color(0xFFD9D9D9),
                forInterview = true
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 65.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                18.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            Text(
//                text = if (isInterviewerTurn) "" else "답변 시 클릭 후 말해주세요",
                text = if (!isInterviewerTurn && !isMicClick) {
                    "답변 시 클릭 후 말해주세요"
                } else {
                    ""
                },
                fontFamily = noToSansKr,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFD9D9D9),
                textAlign = TextAlign.Center
            )
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(53.dp)
                    .border(
                        3.dp,
                        if (isInterviewerTurn) Color.Transparent else VieweeColorMain,
                        CircleShape
                    )
                    .clickable {
                        startListening()
                        isMicClick = true
                    },
                painter = painterResource(id = R.drawable.ic_mic),
                contentDescription = "말하기",
            )
        }

//        TextField(
//            value = text,
//            onValueChange = { onTextChanged(it) }
//        )
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
        startListening = {},
        text = "",
        onTextChanged = {},
        isInterviewerTurn = true,
        isReInterview = true
    )
}