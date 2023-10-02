package com.capstone.vieweeapp.presentation.view.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.data.source.local.entity.InterviewResult
import com.capstone.vieweeapp.presentation.event.FeedbackForHomeUiEvent
import com.capstone.vieweeapp.presentation.view.feedback.graph.CircularGraphView
import com.capstone.vieweeapp.presentation.view.feedback.graph.TriangleGraphView
import com.capstone.vieweeapp.presentation.view.interview.input_profile.CustomTitleText
import com.capstone.vieweeapp.ui.theme.VieweeColorMain

@Preview(showBackground = true)
@Composable
fun TwoGraphPreview() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TriangleGraphView()
        CircularGraphView()
    }
}

@Composable
fun FeedbackScreenForHome(
    modifier: Modifier = Modifier,
    interviewResult: InterviewResult,
    onNavigateHome: () -> Unit,
    uiEvent: (FeedbackForHomeUiEvent) -> Unit,
    name: String,
) {

    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .background(Color.White)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = modifier.background(VieweeColorMain.copy(.2f)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        modifier = Modifier.align(Alignment.CenterStart),
                        onClick = { onNavigateHome() }
                    ) {
                        Icon(
                            Icons.Filled.Home,
                            "home", modifier = Modifier
                                .alpha(.7f)
                                .padding(vertical = 20.dp, horizontal = 20.dp)
                        )
                    }
                    IconButton(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        onClick = {
                            uiEvent(FeedbackForHomeUiEvent.DeleteFeedback(interviewResult))
                            onNavigateHome()
                        }
                    ) {
                        Icon(
                            modifier = Modifier
                                .alpha(.7f)
                                .padding(vertical = 20.dp, horizontal = 20.dp),
                            imageVector = Icons.Filled.DeleteForever,
                            contentDescription = "delete",
                            tint = Color.Red.copy(alpha = 0.75f)
                        )
                    }
                }
                Text(
                    text = "면접 결과",
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 30.dp),
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Normal,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W800,
                    color = VieweeColorMain
                )

                Divider(color = Color.Gray, thickness = 1.dp)
            }

            Column(
                modifier = Modifier
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TriangleGraphView()
                    CircularGraphView()
                }
                CustomTitleText(
                    Modifier.padding(top = 50.dp),
                    "✓ ${name} 님 면접의 총평 피드백"
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 30.dp, horizontal = 30.dp)
                ) {
                    Text(
                        text = interviewResult.feedbackTotal,
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = VieweeColorMain.copy(alpha = .5f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(30.dp)
                            .align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            FeedbackDetailCardGridForHome(
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .background(Color.White),
                interviewResult = interviewResult,
                name = name
            )
        }
    }

}


@Composable
fun FeedbackDetailCardGridForHome(
    modifier: Modifier = Modifier,
    interviewResult: InterviewResult,
    name: String
) {
    CustomTitleText(
        Modifier.padding(top = 20.dp, bottom = 30.dp),
        "✓ ${name} 님 면접의 질문 피드백"
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        interviewResult.answers
            .forEachIndexed { index, answer ->
                FeedbackDetailCardView(
                    modifier = Modifier.padding(bottom = 10.dp),
                    detailTitle = interviewResult.questions[index],
                    detailContent = answer,
                    feedbackContent = interviewResult.feedbacks.feedbacks[index]
                )
            }
    }
}


//@Composable
//fun FeedbackDetailCardViewForHome(
//    modifier: Modifier = Modifier,
//    DetailTitle: String,
//    DetailContent: String
//) {
//    var isExpanded by remember { mutableStateOf(false) }
//    val interactionSource = remember { MutableInteractionSource() }
//
//
//    val extraPadding by animateDpAsState(
//        if (isExpanded) 48.dp else 0.dp,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        )
//    )
//
//    CompositionLocalProvider(LocalRippleTheme provides CustomRippleEffect.NoRippleTheme) {
//        Box(
//            modifier = modifier
//                .border(1.dp, (VieweeColorMain.copy(alpha = 0.5f)), RoundedCornerShape(10.dp))
//                .clickableWithoutRipple(
//                    interactionSource = interactionSource,
//                    onClick = { isExpanded = !isExpanded }
//                ),
//
//            ) {
//            Column(
//                modifier = Modifier
//                    .padding(5.dp)
//                    .padding(bottom = extraPadding.coerceAtLeast(0.dp)),
//                verticalArrangement = Arrangement.Top,
//                horizontalAlignment = Alignment.CenterHorizontally,
//
//                ) {
//
//                Text(
//                    text = DetailTitle,
//                    modifier = Modifier
//                        .padding(top = 10.dp),
//                    textAlign = TextAlign.Center,
//                    fontWeight = FontWeight.ExtraBold,
//                    fontSize = 14.sp,
//                    color = Color.DarkGray.copy(.8f)
//                )
//
//                IconButton(
//                    onClick = { isExpanded = !isExpanded },
//
//                    ) {
//                    Icon(
//                        Icons.Filled.ArrowDropDown,
//                        "Show", modifier = Modifier
//                            .alpha(.7f)
//                            .fillMaxWidth()
//
//                    )
//                }
//                if (isExpanded) Text(
//                    text = DetailContent,
//                    modifier = Modifier
//                        .padding(10.dp),
//                    textAlign = TextAlign.Left,
//                    fontSize = 13.sp,
//                    color = Color.Gray,
//                ) else " "
//            }
//        }
//    }
//}
//
//private val VerticalScrollConsumer = object : NestedScrollConnection {
//    override fun onPreScroll(available: Offset, source: NestedScrollSource) = available.copy(x = 0f)
//    override suspend fun onPreFling(available: Velocity) = available.copy(x = 0f)
//}
//
//private val HorizontalScrollConsumer = object : NestedScrollConnection {
//    override fun onPreScroll(available: Offset, source: NestedScrollSource) = available.copy(y = 0f)
//    override suspend fun onPreFling(available: Velocity) = available.copy(y = 0f)
//}