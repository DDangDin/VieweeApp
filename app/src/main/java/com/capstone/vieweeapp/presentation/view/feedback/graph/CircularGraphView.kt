package com.capstone.vieweeapp.presentation.view.feedback.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.data.source.local.entity.Emotion
import com.capstone.vieweeapp.presentation.view.feedback.graph.CircularGraphColor.Angry
import com.capstone.vieweeapp.presentation.view.feedback.graph.CircularGraphColor.Fear
import com.capstone.vieweeapp.presentation.view.feedback.graph.CircularGraphColor.Happy
import com.capstone.vieweeapp.presentation.view.feedback.graph.CircularGraphColor.Neutral
import com.capstone.vieweeapp.presentation.view.feedback.graph.CircularGraphColor.Sad
import com.capstone.vieweeapp.presentation.view.feedback.graph.CircularGraphColor.Surprise
import com.capstone.vieweeapp.presentation.view.feedback.graph.CircularGraphColor.Disgust
import com.capstone.vieweeapp.utils.FacialEmotionList
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


object CircularGraphColor {
    val BlueGraph = Color(0xFF1F77B4)

    val UsOrange = Color(0xFFFF7F0E)
    val UsGrey = Color(0xFF525965)

    val Surprise= Color(0xB9B899B8)     //0x461F77B4
    val Fear= Color(0xDA7D93B8)         //0xFF7DA2C9
    val Angry = Color(0xFFF7F0C6)
    val Neutral = Color(0xFCC4D8C0)
    val Sad = Color(0xD85D7599)
    val Disgust = Color(0xB9EBD5EB)     //0xFFCACEB7
    val Happy = Color(0xFCB3CCAF)       //0xFCCCB7AF 0xFF7DA2C9
}

@Composable
fun CircularGraphView(
    modifier: Modifier = Modifier,
    emotions: List<Emotion> = emptyList()
) {
    val data = listOf(10f, 15f, 25f, 10f, 15f, 5f, 20f) // 각 섹션의 비율
//    val data = listOf(30f, 0f, 0f, 60f, 10f, 0f, 0f) // 각 섹션의 비율
    val colors = listOf(Surprise, Fear, Angry, Neutral, Sad, Disgust, Happy) // 각 섹션의 색상

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // PieChartWithWhiteCenter를 중앙에 배치
        PieChartWithWhiteCenter(
            modifier = Modifier
                .align(Alignment.Center)
                .size(200.dp), // 크기 조절 가능
            data = data,
            colors = colors,
            colorNames = FacialEmotionList.emotionNames
        )
        // ExplainCircleGraph를 위에 겹쳐서 배치
//        ExplainCircleGraph(
//            modifier = Modifier
//                .align(Alignment.Center)
//                .fillMaxSize()
//                .wrapContentSize(Alignment.TopCenter)
//                .height(300.dp)
//        )
    }
}


@Composable
fun PieChartWithWhiteCenter(
    modifier: Modifier = Modifier,
    data: List<Float>,
    colors: List<Color?>,
    colorNames: List<String>,
    startAngle: Float = 0f
) {
    var currentAngle = startAngle

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            onDraw = {
                val centerX = size.width / 2f
                val centerY = size.height / 2f
                val radius = size.width / 3f
//            val centerX = size.width / 5f
//            val centerY = size.height / 5f
//            val radius = min(size.width, size.height) / 5f
                val total = data.sum()

                data.forEachIndexed { i, value ->
                    val sweepAngle = 360f * (value / total)
                    val percentage = (value / total) * 100

                    drawArc(
                        color = colors[i] ?: Color.Gray,
                        startAngle = currentAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true,
                        topLeft = Offset(centerX - radius, centerY - radius),
                        size = Size(radius * 2, radius * 2)
                    )

                    currentAngle += sweepAngle

                    val textAngle = currentAngle - sweepAngle / 2
                    val textX = centerX + (radius + 80) * cos(Math.toRadians(textAngle.toDouble())).toFloat()
                    val textY = centerY + (radius + 80) * sin(Math.toRadians(textAngle.toDouble())).toFloat()
                    drawIntoCanvas {
                        if (percentage.toInt() != 0) {
                            it.nativeCanvas.drawText(
                                colorNames[i] + " " + "${percentage.toInt()}%",
                                textX,
                                textY,
                                android.graphics.Paint().apply {
                                    color = Color.Gray.toArgb()
                                    textSize = 12.sp.toPx()
                                    textAlign = android.graphics.Paint.Align.CENTER
                                    isAntiAlias = true
                                    typeface = android.graphics.Typeface.DEFAULT_BOLD
                                }
                            )
                        }
                    }
                }

                drawCircle(
                    color = Color.White,
                    center = Offset(centerX, centerY),
                    radius = radius / 2f
                )
            }
        )
    }
}

@Composable
fun ExplainCircleGraph(modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent),
    ) {
        data class ColorInfo(
            val color: Color,
            val text: String,
            val x: Float,
            val y: Float
        )

        val colorAndText = listOf(
            ColorInfo(Neutral, "보통", 650f, 700f),
            ColorInfo(Happy, "행복", 650f, 750f),
            ColorInfo(Angry, "화남", 650f, 800f),
            ColorInfo(Disgust, "혐오", 650f, 850f),
            ColorInfo(Surprise, "놀람", 650f, 900f),
            ColorInfo(Fear, "두려움", 650f, 950f),
            ColorInfo(Sad, "슬픔", 650f, 1000f)
        )

        colorAndText.forEach { (colors, text, x, y) ->
            drawCircle(
                color = colors,
                center = Offset(x, y),
                radius = 7f
            )

            drawIntoCanvas { canvas ->
                val textPaint = android.graphics.Paint().apply {
                    color = Color.Gray.toArgb()
                    textSize = 8.sp.toPx()
                    textAlign = android.graphics.Paint.Align.LEFT
                    isAntiAlias = true
                }

                canvas.nativeCanvas.drawText(
                    text,
                    x + 20f,
                    y + 10f,
                    textPaint
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExplainCircleGraphPreview() {
    ExplainCircleGraph()
}

@Preview(showBackground = true)
@Composable
fun PieChartWithWhiteCenterPreview() {

    val data = listOf(10f, 15f, 25f, 10f, 15f, 5f, 10f) // 각 섹션의 비율
    val colors = listOf(Surprise, Fear, Angry, Neutral, Sad, Disgust, Happy) // 각 섹션의 색상

    PieChartWithWhiteCenter(
        data = data,
        colors = colors,
        colorNames = FacialEmotionList.emotionNames
    )
}

@Preview(showBackground = true)
@Composable
fun CircularGraphViewPreview() {
    CircularGraphView()
}