package com.capstone.vieweeapp.presentation.view.feedback

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.viewee.data.source.network.clova_api.dto.Confidence
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.data.source.local.entity.Emotion
import com.capstone.vieweeapp.data.source.local.entity.Feedbacks
import com.capstone.vieweeapp.data.source.local.entity.InterviewResult
import com.capstone.vieweeapp.data.source.local.entity.TextSentiment
import com.capstone.vieweeapp.data.source.local.entity.average
import com.capstone.vieweeapp.data.source.local.entity.toPercentages
import com.capstone.vieweeapp.presentation.event.FeedbackForHomeUiEvent
import com.capstone.vieweeapp.presentation.state.ReInterviewState
import com.capstone.vieweeapp.presentation.view.feedback.graph.CircularGraphView
import com.capstone.vieweeapp.presentation.view.feedback.graph.ExplainTriangleGraph
import com.capstone.vieweeapp.presentation.view.feedback.graph.TriangleGraphView
import com.capstone.vieweeapp.presentation.view.interview.input_profile.CustomTitleText
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.ui.theme.VieweeColorShadow
import com.capstone.vieweeapp.ui.theme.VieweeColorText
import com.capstone.vieweeapp.ui.theme.noToSansKr
import com.capstone.vieweeapp.utils.CalculateDate
import com.capstone.vieweeapp.utils.Constants
import com.capstone.vieweeapp.utils.CustomRippleEffect
import com.capstone.vieweeapp.utils.CustomRippleEffect.clickableWithoutRipple

@Preview(showBackground = true)
@Composable
fun FeedbackScreenForHomePreview() {
    FeedbackScreenForHome(
        interviewResult = InterviewResult(
            id = 0,
            questions = listOf("1", "2"),
            answers = listOf("1", "2"),
            date = "2023.10.01 월",
            feedbacks = Feedbacks(listOf("1", "2", "총평피드백")),
            feedbackTotal = "총평피드백 부분",
            emotions = listOf(
                Emotion(1,1,1,1,1,1,1),
                Emotion(1,1,1,1,1,1,1),
                Emotion(1,1,1,1,1,1,1),
            ),
            textSentiments = listOf(
                TextSentiment("", Confidence(0.0,0.0,0.0)),
                TextSentiment("", Confidence(0.0,0.0,0.0)),
                TextSentiment("", Confidence(0.0,0.0,0.0)),
            ),
            etc = ""
        ),
        onNavigateHome = { /*TODO*/ },
        uiEvent = { /*TODO*/ },
        eachReInterviewState = ReInterviewState(),
        onStartReInterview = {}
    )
}

@Composable
fun FeedbackScreenForHome(
    modifier: Modifier = Modifier,
    interviewResult: InterviewResult,
    onNavigateHome: () -> Unit,
    uiEvent: (FeedbackForHomeUiEvent) -> Unit,
    eachReInterviewState: ReInterviewState,
    isReInterview: Boolean = false,
    onStartReInterview: (Int) -> Unit
) {

    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (
            (interviewResult.answers.size + 1) == interviewResult.feedbacks.feedbacks.size ||
            (interviewResult.answers.size / 2 + 1) == interviewResult.feedbacks.feedbacks.size
        ) {
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
                                tint = VieweeColorMain.copy(alpha = 0.8f),
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
                                tint = VieweeColorMain.copy(alpha = 0.8f),
                                modifier = Modifier
                                    .alpha(.7f)
                                    .padding(vertical = 20.dp, horizontal = 20.dp),
                            )
                        }
                    }
                    // FeedbackTopBar
                    Text(
                        text = AnnotatedString(
                            buildString {
                                append("${CalculateDate.dateFormatForFeedback(interviewResult.date)} 일자의")
                                append("\n")
                                append("${
                                    interviewResult.etc.ifEmpty { "1" }
                                }번째 면접 결과")
                            }
                        ),
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
                        verticalArrangement = Arrangement.spacedBy(
                            30.dp,
                            alignment = Alignment.CenterVertically
                        )
                    ) {
                        CustomTitleText(
                            text = "${
                                CalculateDate.dateFormatForFeedback(
                                    interviewResult.date
                                )
                            } 면접의 감정 분석"
                        )
                        if (interviewResult.textSentiments.isNotEmpty()) {
                            Column(
                                modifier = Modifier,
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                ExplainTriangleGraph(Modifier.align(Alignment.End).padding(end = 28.dp))
                                TriangleGraphView(intervieweeValues = interviewResult.textSentiments.average(isReInterview))
                            }
                        }
                        CustomTitleText(
                            text = "${
                                CalculateDate.dateFormatForFeedback(
                                    interviewResult.date
                                )
                            } 면접의 표정 분석"
                        )
                        if (interviewResult.emotions.isNotEmpty()) {
                            CircularGraphView(emotions = interviewResult.emotions.toPercentages().map { it.second })
                        }
                    }
                    CustomTitleText(
                        text = "${
                            CalculateDate.dateFormatForFeedback(
                                interviewResult.date
                            )
                        } 면접의 총평 피드백"
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 27.dp, horizontal = 20.dp)
                            .background(
                                VieweeColorMain.copy(alpha = 0.08f),
                                RoundedCornerShape(10.dp)
                            )
                    ) {
                        Text(
                            text = interviewResult.feedbackTotal,
                            modifier = Modifier
                                .padding(30.dp)
                                .align(Alignment.Center),
                            fontFamily = noToSansKr,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Start,
                            color = Color.Gray
                        )
                    }
                }
                FeedbackDetailCardGridForHome(
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                        .background(Color.White),
                    interviewResult = interviewResult,
                    onSubmit = { index, maxIndex, inputAnswer ->
                        uiEvent(
                            FeedbackForHomeUiEvent.EachReInterview(
                                index,
                                maxIndex,
                                inputAnswer
                            )
                        )
                    },
                    reInterviewState = eachReInterviewState,
                    isReInterview = isReInterview
                )

                Spacer(Modifier.height(100.dp))
            }
            CompositionLocalProvider(LocalRippleTheme provides CustomRippleEffect.NoRippleTheme) {
                OutlinedButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
                        .shadow(
                            elevation = 5.dp,
                            spotColor = VieweeColorShadow,
                            ambientColor = VieweeColorShadow,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    border = BorderStroke(0.dp, Color.Transparent),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VieweeColorMain.copy(alpha = 1f),
                        contentColor = Color.Transparent,
                    ),
                    onClick = { onStartReInterview(interviewResult.id ?: 0) }
                ) {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            12.dp,
                            alignment = Alignment.CenterHorizontally
                        )
                    ) {
                        Text(
                            text = "재면접 보러가기",
                            fontFamily = noToSansKr,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        Icon(
                            modifier = Modifier.size(25.dp),
                            imageVector = Icons.Default.ArrowRightAlt,
                            contentDescription = "download",
                            tint = Color.White
                        )
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    10.dp,
                    alignment = Alignment.CenterVertically
                ),
            ) {
                Text(
                    text = Constants.COMMON_ERROR_MESSAGE,
                    fontFamily = noToSansKr,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = Color.Red.copy(alpha = 0.5f),
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.clickable {
                        uiEvent(FeedbackForHomeUiEvent.DeleteFeedback(interviewResult))
                        onNavigateHome()
                    },
                    text = "면접 결과 지우기",
                    fontFamily = noToSansKr,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    color = VieweeColorText,
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline
                )
                Text(
                    modifier = Modifier.clickable { onNavigateHome() },
                    text = "돌아가기",
                    fontFamily = noToSansKr,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    color = VieweeColorText,
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }

}


@Composable
private fun FeedbackDetailCardGridForHome(
    modifier: Modifier = Modifier,
    interviewResult: InterviewResult,
    onSubmit: (Int, Int, String) -> Unit,
    reInterviewState: ReInterviewState,
    isReInterview: Boolean
) {

    CustomTitleText(
        Modifier.padding(top = 20.dp, bottom = 30.dp),
        "${CalculateDate.dateFormatForFeedback(interviewResult.date)} 면접의 질문 피드백"
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        interviewResult.questions.forEachIndexed { index, question ->

            var reInterviewClick by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier.padding(bottom = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    10.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                // 피드백 카드 뷰(질문, 답변, 피드백)
                FeedbackDetailCardView(
                    modifier = Modifier,
                    detailTitle = question,
                    detailContent = interviewResult.answers[index],
                    feedbackContent = interviewResult.feedbacks.feedbacks[index],
                    isReInterview = isReInterview,
                    detailContent2 = if (isReInterview) interviewResult.answers[index + (interviewResult.answers.size / 2)] else "",
                    emotion = interviewResult.emotions[index],
                    textSentiment = interviewResult.textSentiments[index],
                    textSentiment2 = if (isReInterview) interviewResult.textSentiments[index + (interviewResult.textSentiments.size / 2)]
                    else TextSentiment("", Confidence(0.0,0.0,0.0))
                )
                if (!isReInterview) {
                    EachReInterviewSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(
                                color = if (reInterviewClick) VieweeColorMain.copy(alpha = 0.07f) else VieweeColorMain.copy(
                                    alpha = 0.5f
                                ),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickableWithoutRipple(
                                interactionSource = MutableInteractionSource(),
                                onClick = { reInterviewClick = true }
                            ),
                        reInterviewClick = reInterviewClick,
                        reInterviewState = reInterviewState,
                        index = index,
                        onSubmit = { inputAnswer ->
                            onSubmit(
                                index,
                                interviewResult.answers.size,
                                inputAnswer
                            )
                        },
                        onClose = { reInterviewClick = false }
                    )
                }
            }
        }
    }
}