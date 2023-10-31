package com.capstone.vieweeapp.presentation.view.feedback.graph

import android.graphics.Typeface
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.data.source.local.entity.toFloatList
import com.capstone.vieweeapp.presentation.view.feedback.graph.TriangleGraphColor.BlueGraph
import com.capstone.vieweeapp.presentation.view.feedback.graph.TriangleGraphColor.UsOrange
import com.capstone.vieweeapp.ui.theme.VieweeColorText
import com.capstone.vieweeapp.ui.theme.noToSansKr
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

object TriangleGraphColor {
    val BlueGraph = Color(0xFF1F77B4)
    val UsOrange = Color(0xFFFF7F0E)
}


data class TriangleGraphData(
    val values: List<Float>,
    val lineColor: Color = UsOrange,
    val fillColor: Color = UsOrange.copy(alpha = 0.1f),
    val maxValue: Float = 100f
)

//@Preview(showBackground = true)
//@Composable
//fun OneTriangle() {
//    val orangeData =
//        TriangleGraphData(listOf(40f, 60f, 100f), UsOrange, UsOrange.copy(alpha = 0.1f))
//    val blueData = TriangleGraphData(listOf(50f, 50f, 50f), BlueGraph, BlueGraph.copy(alpha = 0.1f))
//    val maxValue = remember { Animatable(2000f) }
//
//    LaunchedEffect(maxValue) {
//        maxValue.animateTo(
//            targetValue = 100f,
//            animationSpec = tween(durationMillis = 3000, easing = LinearOutSlowInEasing)
//        )
//    }
//
//    Box(
//        modifier = Modifier
//            .size(200.dp)
//            .padding(horizontal = 10.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        TriangleGraphDemo(orangeData, modifier = Modifier, showText = false)
//        TriangleGraphDemo(blueData, modifier = Modifier.align(Alignment.Center))
//    }
//}



//@Preview(showBackground = true)
@Composable
fun TwoTriangles(
    modifier: Modifier = Modifier,
    values1: List<Float>,
    values2: List<Float>,
) {
    val orangeData1 = TriangleGraphData(values1, UsOrange, UsOrange.copy(alpha = 0.1f))
    val orangeData2 = TriangleGraphData(values2, UsOrange, UsOrange.copy(alpha = 0.1f))
    val blueData = TriangleGraphData(listOf(50f, 50f, 50f), BlueGraph, BlueGraph.copy(alpha = 0.1f))

    val maxValue = remember { Animatable(2000f) }

    LaunchedEffect(maxValue) {
        maxValue.animateTo(
            targetValue = 100f,
            animationSpec = tween(durationMillis = 3000, easing = LinearOutSlowInEasing)
        )
    }

    Column(
        modifier = modifier.fillMaxWidth().height(200.dp),//.scale(.8f)
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Box(
            modifier = Modifier
                .size(200.dp)
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            TriangleGraphDemo(
                data = orangeData1,
                modifier = Modifier,
                showText = false,
                maxValue = maxValue.value
            )
            TriangleGraphDemo(
                data = blueData,
                modifier = Modifier.align(Alignment.Center),
                maxValue = blueData.maxValue
            )

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_replay_trianglegraph_triangle),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier
                    .size(30.dp)
                    .offset(x = 80.dp)
            )
        }

        Box(
            modifier = Modifier
                .size(200.dp)
                .padding(horizontal = 10.dp)
                .offset(x = 150.dp, y = -115.dp),
            contentAlignment = Alignment.Center
        ) {
            TriangleGraphDemo(
                data = orangeData2,
                modifier = Modifier,
                showText = false,
                maxValue = maxValue.value
            )
            TriangleGraphDemo(
                data = blueData,
                modifier = Modifier.align(Alignment.Center),
                maxValue = blueData.maxValue
            )
        }
    }
}



@Composable
fun TriangleGraphDemo(
    data: TriangleGraphData,
    modifier: Modifier = Modifier,
    showText: Boolean = true,
    maxValue: Float
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(300.dp)
                .padding(16.dp)
        ) {
            val centerX = size.width / 2f
            val centerY = size.height / 2f
            val radius = size.width / 3f
            val step = (2 * PI / data.values.size).toFloat()
            val path = Path()

            data.values.forEachIndexed { index, value ->
                val value2 = if (value < 10f) {
                    value + 10f
                } else {
                    value
                }

                val angle = (index * step).toFloat() - (PI / 2).toFloat()
                val x = centerX + radius * cos(angle) * (value2 / maxValue)
                val y = centerY + radius * sin(angle) * (value2 / maxValue)

                if (index == 0) {
                    path.moveTo(x, y)
                } else {
                    path.lineTo(x, y)
                }

                drawCircle(
                    color = data.lineColor,
                    center = Offset(x, y),
                    radius = 7f
                )

                if (showText) {
                    val label = when (index) {
                        0 -> "긍정"
                        1 -> "보통"
                        2 -> "부정"
                        else -> ""
                    }

                    val textOffsetX = if (x > centerX) 35.dp.toPx() else -20.dp.toPx()
                    val textOffsetY = if (y > centerY) 45.dp.toPx() else -35.dp.toPx()

                    drawIntoCanvas { canvas ->
                        val paint = android.graphics.Paint().apply {
                            color = Color.DarkGray.toArgb()
                            textSize = 9.sp.toPx()
                            textAlign = android.graphics.Paint.Align.CENTER
                            isAntiAlias = true
                        }

                        canvas.nativeCanvas.drawText(
                            label,
                            x + textOffsetX,
                            y + textOffsetY,
                            paint
                        )
                    }
                }
            }

            // Draw the three lines
            val lineAngle1 = -PI / 6 + PI / 3
            val lineAngle2 = PI / 2 + PI / 3
            val lineAngle3 = 7 * PI / 6 + PI / 3
            val lineColor = Color.LightGray
            val lineWidth = 2f

            drawLine(
                lineColor, Offset(centerX, centerY), Offset(
                    (centerX + radius * cos(lineAngle1)).toFloat(),
                    (centerY + radius * sin(lineAngle1)).toFloat()
                ), lineWidth
            )
            drawLine(
                lineColor, Offset(centerX, centerY), Offset(
                    (centerX + radius * cos(lineAngle2)).toFloat(),
                    (centerY + radius * sin(lineAngle2)).toFloat()
                ), lineWidth
            )
            drawLine(
                lineColor, Offset(centerX, centerY), Offset(
                    (centerX + radius * cos(lineAngle3)).toFloat(),
                    (centerY + radius * sin(lineAngle3)).toFloat()
                ), lineWidth
            )

            path.close()

            drawPath(path, color = data.fillColor)
            drawPath(path, color = data.lineColor, style = Stroke(1.5.dp.toPx()))

            val circleCount = 5
            val maxCircleRadius = radius
            val circleSpacing = maxCircleRadius / circleCount
            val circleWidth = 0.5.dp.toPx()

            repeat(circleCount) { i ->
                drawCircle(
                    color = Color.Gray.copy(alpha = 0.5f),
                    center = Offset(centerX, centerY),
                    radius = maxCircleRadius - (i * circleSpacing),
                    style = Stroke(width = circleWidth)
                )
            }
        }
    }
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
    val context = LocalContext.current

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
                            typeface = Typeface.createFromAsset(
                                context.assets,
                                "noto_sans_kr_semi_bold.ttf"
                            )
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

//@Preview(showBackground = true)
//@Composable
//fun GraphViewPreview() {
//    val intervieweeValues = listOf(30f, 99f, 56f)
//    Row(
//        modifier = Modifier.fillMaxSize(),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.spacedBy(
//            25.dp,
//            alignment = Alignment.CenterHorizontally
//        )
//    ) {
//        OneTriangle()
//        Icon(
//            modifier = Modifier
//                .size(33.dp, 16.dp),
//            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_right),
//            contentDescription = "arrowRight",
//            tint = Color(0x1A92979F),
//        )
//        OneTriangle()
//    }
//}