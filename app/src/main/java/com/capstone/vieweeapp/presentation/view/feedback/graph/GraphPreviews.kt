package com.capstone.vieweeapp.presentation.view.feedback.graph

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.capstone.vieweeapp.utils.FacialEmotionList


@Preview(showBackground = true)
@Composable
fun PieChartWithWhiteCenterPreview2() {

    val data = listOf(10f, 15f, 25f, 10f, 15f, 5f, 10f) // 각 섹션의 비율
//    val data = listOf(30f, 0f, 0f, 60f, 10f, 0f, 0f) // 각 섹션의 비율
    val colors = listOf(
        CircularGraphColor.Surprise,
        CircularGraphColor.Fear,
        CircularGraphColor.Angry,
        CircularGraphColor.Neutral,
        CircularGraphColor.Sad,
        CircularGraphColor.Disgust,
        CircularGraphColor.Happy
    ) // 각 섹션의 색상

    PieChartWithWhiteCenter(
        data = data,
        colors = colors,
        colorNames = FacialEmotionList.emotionNames
    )
}

@Preview(showBackground = true)
@Composable
fun TriangleGraphPreview2() {
    // 범례
    ExplainTriangleGraph()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        StandardTriangleGraphDemo(
            values = listOf(50f, 50f, 50f)
        )
        IntervieweeTriangleGraphDemo(
            values = listOf(30f, 100f, 30f)
        )
        StandardCircleTriangleGraph()
    }
}

