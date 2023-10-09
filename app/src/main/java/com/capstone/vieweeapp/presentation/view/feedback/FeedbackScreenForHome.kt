package com.capstone.vieweeapp.presentation.view.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.data.source.local.entity.Feedbacks
import com.capstone.vieweeapp.data.source.local.entity.InterviewResult
import com.capstone.vieweeapp.presentation.event.FeedbackForHomeUiEvent
import com.capstone.vieweeapp.presentation.view.feedback.graph.CircularGraphView
import com.capstone.vieweeapp.presentation.view.feedback.graph.TriangleGraphView
import com.capstone.vieweeapp.presentation.view.interview.input_profile.CustomTitleText
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.ui.theme.VieweeColorText
import com.capstone.vieweeapp.utils.CalculateDate

@Preview(showBackground = true)
@Composable
fun FeedbackScreenForHomePreview() {
    FeedbackScreenForHome(
        interviewResult = InterviewResult(
            id = 0,
            questions = emptyList(),
            answers = emptyList(),
            date = "2023.10.01 월",
            feedbacks = Feedbacks(emptyList()),
            feedbackTotal = "총평피드백 부분",
            emotions = emptyList(),
            textSentiment = emptyList(),
            etc = ""
        ),
        onNavigateHome = { /*TODO*/ },
        uiEvent = { /*TODO*/ },
        name = "곽상진"
    )
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
                            imageVector = ImageVector.vectorResource(R.drawable.ic_btn_home),
                            contentDescription = "home",
                            tint = VieweeColorMain,
                            modifier = Modifier
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
                            imageVector = ImageVector.vectorResource(R.drawable.ic_btn_trash),
                            contentDescription = "delete",
                            tint = VieweeColorMain,
                            modifier = Modifier
                                .alpha(.7f)
                                .padding(vertical = 20.dp, horizontal = 20.dp),
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 30.dp, horizontal = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(30.dp, alignment = Alignment.CenterVertically)
                ) {
                    CustomTitleText(text = "✓ ${CalculateDate.dateFormatForFeedback(interviewResult.date)} 면접의 감정 분석")
                    TriangleGraphView()
                    CustomTitleText(text = "✓ ${CalculateDate.dateFormatForFeedback(interviewResult.date)} 면접의 표정 분석")
                    CircularGraphView()
                }
                CustomTitleText(text = "✓ ${CalculateDate.dateFormatForFeedback(interviewResult.date)} 면접의 총평 피드백")
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
            )
        }
    }

}


@Composable
fun FeedbackDetailCardGridForHome(
    modifier: Modifier = Modifier,
    interviewResult: InterviewResult,
) {
    CustomTitleText(
        Modifier.padding(top = 20.dp, bottom = 30.dp),
        "✓ ${CalculateDate.dateFormatForFeedback(interviewResult.date)} 면접의 질문 피드백"
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