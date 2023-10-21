package com.capstone.vieweeapp.presentation.view.feedback.graph

import android.graphics.Typeface
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.presentation.view.feedback.graph.TriangleGraphColor.BlueGraph
import com.capstone.vieweeapp.presentation.view.feedback.graph.TriangleGraphColor.UsOrange
import com.capstone.vieweeapp.ui.theme.VieweeColorText
import com.capstone.vieweeapp.ui.theme.noToSansKr
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

object TriangleGraphColor {
    val BlueGraph = Color(0xFF1F77B4)
    val UsOrange = Color(0xFFFF7F0E)
}

@Composable
fun TriangleGraphView(
    modifier: Modifier = Modifier,
    standardValues: List<Float> = listOf(50f, 50f, 50f),
    intervieweeValues: List<Float>,
) {

//    val standardValues = remember { listOf(50f, 50f, 50f) }
//    val intervieweeValues = remember { listOf(60f, 100f, 30f) }

     // values 값 순서: 긍정-보통-부정

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        StandardTriangleGraphDemo(values = standardValues)
        IntervieweeTriangleGraphDemo(values = intervieweeValues)
        StandardCircleTriangleGraph()
    }
}

@Composable
fun ExplainTriangleGraph(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp, alignment = Alignment.CenterHorizontally)
    ) {
        Icon(
            imageVector = Icons.Filled.Circle,// 사용할 아이콘 선택
            contentDescription = "trianglegraph_blue",
            tint = BlueGraph.copy(.7f),
            modifier = Modifier
                .size(9.dp)
        )
        Text(
            text = "평균",
            modifier = Modifier.padding(end = 3.dp),
            color = VieweeColorText.copy(alpha = 0.5f),
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
        )
        Icon(
            imageVector = Icons.Filled.Circle, // 사용할 아이콘 선택
            contentDescription = "trianglegraph_orange",
            tint = UsOrange.copy(.7f),
            modifier = Modifier
                .size(9.dp)
        )
        Text(
            text = "본인",
            modifier = Modifier,
            color = VieweeColorText.copy(alpha = 0.5f),
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
        )
    }
}

//평균 그래프 파랑
@Composable
fun StandardTriangleGraph(
    modifier: Modifier = Modifier,
    values: List<Float> = listOf(25f, 75f, 50f),
    backgroundColor: Color = Color.White,
    lineColor: Color = BlueGraph,
    fillColor: Color = Color(0xFF1F77B4).copy(alpha = 0.25f)
) {
    val maxValue by remember { mutableFloatStateOf(100f) }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .scale(1.5f),
        ) {
            val centerX = size.width / 2f
            val centerY = size.height / 2f
            val radius = size.width / 3f

            val step = (2 * PI / values.size).toFloat()

            val path = Path()

            values.forEachIndexed { index, value ->
                val angle = (index * step).toFloat() - (PI / 2).toFloat()
                val x = centerX + radius * cos(angle) * (value / maxValue)
                val y = centerY + radius * sin(angle) * (value / maxValue)

                if (index == 0) {
                    path.moveTo(x, y)
                } else {
                    path.lineTo(x, y)
                }

                val label = when (index) {
                    0 -> "긍정"
                    1 -> "보통"
                    2 -> "부정"
                    else -> ""
                }

                drawCircle(
                    color = BlueGraph,
                    center = Offset(x, y),
                    radius = 7f
                )

                val textOffsetX = if (x > centerX) 35.dp.toPx() else -20.dp.toPx()
                val textOffsetY = if (y > centerY) 45.dp.toPx() else -35.dp.toPx()

                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                        label,
                        x + textOffsetX,
                        y + textOffsetY,
                        android.graphics.Paint().apply {
                            color = Color.DarkGray.toArgb()
                            textSize = 9.sp.toPx()
                            textAlign = android.graphics.Paint.Align.CENTER
                            isAntiAlias = true
//                            typeface = ???
                        }
                    )
                }
            }

            path.close()

            drawPath(path, color = fillColor)
            drawPath(path, color = lineColor, style = Stroke(1.5.dp.toPx()))

            for (index in 0 until values.size) {
                val angle = (index * (2 * PI / values.size)).toFloat() - (PI / 2).toFloat()
                val x = centerX + 150f * cos(angle)
                val y = centerY + radius * sin(angle)
                drawLine(
                    color = Color.Gray.copy(alpha = 0.5f),
                    start = Offset(centerX, centerY),
                    end = Offset(x, y)
                )
            }
        }
    }
}

//면접자 척도 그래프 출력
@Composable
fun StandardCircleTriangleGraph(
    centerCircleRadii: List<Float> = listOf(30f, 60f, 90f, 120f, 150f)
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .scale(1.5f),
    ) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f

        drawCircle(
            color = Color.Gray,
            center = Offset(centerX, centerY),
            radius = 1f
        )

        centerCircleRadii.forEach { radius ->
            drawCircle(
                color = Color.Gray.copy(alpha = 0.5f),
                center = Offset(centerX, centerY),
                radius = radius,
                style = Stroke(0.5.dp.toPx())
            )
        }
    }
}

//평균 그래프 출력 파랑
@Composable
fun StandardTriangleGraphDemo(values: List<Float>) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        StandardTriangleGraph(
            values = values,
            modifier = Modifier.size(200.dp),
            backgroundColor = Color.White,
            lineColor = BlueGraph
        )
    }
}

//면접자 그래프 출력 오렌지
@Composable
fun IntervieweeTriangleGraph(
    modifier: Modifier = Modifier,
    values: List<Float> = listOf(50f, 100f, 30f),
    lineColor: Color = UsOrange,
    fillColor: Color = UsOrange.copy(alpha = 0.25f),
) {
    val maxValue = remember { Animatable(2000f) }

    LaunchedEffect(maxValue) {
        maxValue.animateTo(
            targetValue = 100f,
            animationSpec = tween(durationMillis = 3000, easing = LinearOutSlowInEasing)
        )
    }

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)

        ) {
            val centerX = size.width / 2f
            val centerY = size.height / 2f
            val radius = size.width / 3f

            val step = (2 * PI / values.size).toFloat()

            val path = Path()

            values.forEachIndexed { index, value ->

                val value2 = if (value < 10f) {
                    value + 10f
                } else {
                    value
                }

                val angle = (index * step).toFloat() - (PI / 2).toFloat()
                val x = centerX + radius * cos(angle) * (value2 / maxValue.value)
                val y = centerY + radius * sin(angle) * (value2 / maxValue.value)

                if (index == 0) {
                    path.moveTo(x, y)
                } else {
                    path.lineTo(x, y)
                }

                drawCircle(
                    color = UsOrange,
                    center = Offset(x, y),
                    radius = 7f
                )
            }

            path.close()

            drawPath(path, color = fillColor)
            drawPath(path, color = lineColor, style = Stroke(1.5.dp.toPx()))
        }
    }
}

//면접자 그래프 출력 오렌지
@Composable
fun IntervieweeTriangleGraphDemo(values: List<Float>) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .scale(1.5f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IntervieweeTriangleGraph(
            values = values,
            modifier = Modifier.size(200.dp),
            lineColor = UsOrange
        )
    }
}

@Preview()
@Composable
fun GraphViewPreview() {
    TriangleGraphView(
        intervieweeValues = listOf(30f, 99f, 56f)
    )
}