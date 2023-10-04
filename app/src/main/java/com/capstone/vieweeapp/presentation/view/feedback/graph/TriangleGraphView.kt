package com.capstone.vieweeapp.presentation.view.feedback.graph

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.presentation.view.feedback.graph.TriangleGraphColor.BlueGraph
import com.capstone.vieweeapp.presentation.view.feedback.graph.TriangleGraphColor.UsOrange
import com.capstone.vieweeapp.ui.theme.VieweeColorOrange
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

object TriangleGraphColor {
    val BlueGraph = Color(0xFF1F77B4)
    val UsOrange = VieweeColorOrange
}

@Composable
fun TriangleGraphView(modifier: Modifier = Modifier) {
    // 범례
//    ExplainTriangleGraph()

    val values = remember { listOf(50f, 50f, 50f) }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        StandardTriangleGraphDemo(values = values)
        IntervieweeTriangleGraphDemo(values = listOf(60f, 100f, 30f) )
        StandardCircleTriangleGraph()
    }
}

//평균 그래프 파랑
@Composable
fun StandardTriangleGraph(
    modifier: Modifier = Modifier,
    values: List<Float> = listOf(25f, 75f, 50f),
    backgroundColor: Color = Color.White,
    lineColor: Color = BlueGraph,
    fillColor: Color = Color(0xFF1F77B4).copy(alpha = 0.3f)
) {
    var maxValue by remember { mutableStateOf(100f) }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
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
                    radius = 6f
                )

                val textOffsetX = if (x > centerX) 45.dp.toPx() else -30.dp.toPx()
                val textOffsetY = if (y > centerY) 55.dp.toPx() else -45.dp.toPx()

                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                        label,
                        x + textOffsetX,
                        y + textOffsetY,
                        android.graphics.Paint().apply {
                            color = Color.Gray.toArgb()
                            textSize = 14.sp.toPx()
                            textAlign = android.graphics.Paint.Align.CENTER
                            isAntiAlias = true
                            typeface = android.graphics.Typeface.DEFAULT_BOLD
                        }
                    )
                }
            }

            path.close()

            drawPath(path, color = fillColor)
            drawPath(path, color = lineColor, style = Stroke(2.dp.toPx()))

            for (index in 0 until values.size) {
                val angle = (index * step).toFloat() - (PI / 2).toFloat()
                val x = centerX + 150f * cos(angle)
                val y = centerY + radius * sin(angle)
                drawLine(
                    color = Color.Gray,
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
                color = Color.Gray,
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
    values: List<Float> =  listOf(50f, 100f, 30f),
    lineColor: Color = UsOrange,
    fillColor: Color = UsOrange.copy(alpha = 0.5f),
) {
    var maxValue by remember { mutableStateOf(100f) }

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
                val angle = (index * step).toFloat() - (PI / 2).toFloat()
                val x = centerX + radius * cos(angle) * (value / maxValue)
                val y = centerY + radius * sin(angle) * (value / maxValue)

                if (index == 0) {
                    path.moveTo(x, y)
                } else {
                    path.lineTo(x, y)
                }

                drawCircle(
                    color = UsOrange,
                    center = Offset(x, y),
                    radius = 6f
                )
            }

            path.close()

            drawPath(path, color = fillColor)
            drawPath(path, color = lineColor, style = Stroke(2.dp.toPx()))
        }
    }
}

//면접자 그래프 출력 오렌지
@Composable
fun IntervieweeTriangleGraphDemo(values: List<Float>) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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

//컬러표식
@Composable
fun ExplainTriangleGraph() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        drawCircle(
            color = BlueGraph,
            center = Offset(520f, 740f),
            radius = 7f
        )
        drawIntoCanvas {
            it.nativeCanvas.drawText(
                "평균",
                580f,
                750f,
                android.graphics.Paint().apply {
                    color = Color.Gray.toArgb()
                    textSize = 12.sp.toPx()
                    textAlign = android.graphics.Paint.Align.CENTER
                    isAntiAlias = true
                    typeface = android.graphics.Typeface.DEFAULT_BOLD
                }
            )
        }

        drawCircle(
            color = UsOrange,
            center = Offset(670f, 740f),
            radius = 7f
        )

        drawIntoCanvas {
            it.nativeCanvas.drawText(
                "본인",
                730f,
                750f,
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

@Preview()
@Composable
fun GraphViewPreview() {
    TriangleGraphView()
}