package com.capstone.vieweeapp.presentation.view.interview.real_interview.prepare

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.presentation.view.interview.input_profile.CustomTitleText
import com.capstone.vieweeapp.presentation.view.interview.input_profile.NextButton
import com.capstone.vieweeapp.ui.theme.VieweeColorBackgroundGrey
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.ui.theme.VieweeColorText
import kotlinx.coroutines.delay
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PreparePlayPagerScreen(
    modifier: Modifier,
    images: List<Int>,
    pagerState: PagerState
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VieweeColorBackgroundGrey),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(state = pagerState) {
                if (it == 3) {
                    PrepareCam()
                } else {
                    Box(modifier = Modifier.fillMaxWidth()){
                        Image(
                            modifier = Modifier.align(Center),
                            painter = painterResource(id = images[it]),
                            contentScale = ContentScale.Crop,
                            contentDescription = ""
                        )
                    }
                }
            }

            var prepareText by remember { mutableStateOf("") }
            prepareText = when (pagerState.currentPage) {
                0 -> stringResource(id = R.string.interview_prepare_text_1)
                1 -> stringResource(id = R.string.interview_prepare_text_2)
                2 -> stringResource(id = R.string.interview_prepare_text_3)
                else -> {
                    ""
                }
            }

            Text(
                text = prepareText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light,
                fontSize = 15.sp,
                color = VieweeColorText,
                letterSpacing = 1.sp
            )

            val pageCount = 4

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(5.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(5.dp)
                    )
                }
            }
        }
    }


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InterviewPrepareScreen(
    modifier: Modifier = Modifier,
    onPopUp: () -> Unit,
    startReadyToInterview: () -> Unit
) {
    val pagerState = rememberPagerState { 4 }
    var isButtonVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VieweeColorBackgroundGrey),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = onPopUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "back",
                    modifier = Modifier
                        .alpha(.7f)
                )
            }
        }

        CustomTitleText(
            modifier = Modifier,
            text = "면접 진행 시 꼭 지켜주세요!"
        )

        PreparePlayPagerScreen(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f),
            images = listOf(
                R.drawable.img_prepare_1,
                R.drawable.img_prepare_2,
                R.drawable.img_prepare_3,
                R.drawable.img_prepare_3,
            ),
            pagerState = pagerState
        )

        LaunchedEffect(pagerState.currentPage) {
            // 나중엔 Face Detection 통해서 통과 되었을 때 넘어가게끔 수정
            Log.d("pagerState", pagerState.currentPage.toString())
            if (pagerState.currentPage == 3) {
                delay(3000L)
                isButtonVisible = true
            } else {
                isButtonVisible = false
            }
        }

        Box(
            modifier = Modifier
                .height(100.dp)
                .padding(vertical = 20.dp)
        ) {

            androidx.compose.animation.AnimatedVisibility(
                visible = isButtonVisible,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut(),
            ) {
                NextButton(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 20.dp),
                    onClick = startReadyToInterview
                )
            }
        }

    }


}


@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PrepareScreenPreview() {

    InterviewPrepareScreen(onPopUp = {}, startReadyToInterview = {})
}


// 준비화면_카메라화면_준비_단계
@Composable
fun PrepareCam(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VieweeColorBackgroundGrey),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "조용하고 밝은 장소에서 카메라를 \n" +
                    "눈높이에 맞춰 고정시킨 후, \n" +
                    "면접을 시행해주세요.\n" +
                    "\n" +
                    "준비가 되셨다면 다음버튼을 눌러 주세요.",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Normal,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = VieweeColorMain

        )

        PrepareCameraView(modifier = Modifier.weight(2f))
    }
}


@Composable
fun PrepareCameraView(
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // camera settings
    var lensFacing by remember {
        mutableStateOf(CameraSelector.LENS_FACING_FRONT)
    }
    val preview = androidx.camera.core.Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    LaunchedEffect(key1 = lensFacing) {
        Log.d("LensFacing_Log", "Change LensFacing")
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clipToBounds(),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            { previewView },
            modifier = Modifier
                .matchParentSize()
                .align(Alignment.Center)
        )
    }
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }
