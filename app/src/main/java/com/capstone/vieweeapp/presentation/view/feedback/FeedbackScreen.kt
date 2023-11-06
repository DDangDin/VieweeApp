package com.capstone.vieweeapp.presentation.view.feedback

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.data.source.local.entity.Emotion
import com.capstone.vieweeapp.data.source.local.entity.Feedbacks
import com.capstone.vieweeapp.data.source.local.entity.TextSentiment
import com.capstone.vieweeapp.data.source.local.entity.average
import com.capstone.vieweeapp.data.source.local.entity.toPercentages
import com.capstone.vieweeapp.data.source.remote.clova.dto.Confidence
import com.capstone.vieweeapp.presentation.state.FeedbackState
import com.capstone.vieweeapp.presentation.state.QuestionsState
import com.capstone.vieweeapp.presentation.view.feedback.graph.CircularGraphView
import com.capstone.vieweeapp.presentation.view.feedback.graph.ExplainTriangleGraph
import com.capstone.vieweeapp.presentation.view.feedback.graph.TriangleGraphView
import com.capstone.vieweeapp.presentation.view.interview.input_profile.CustomTitleText
import com.capstone.vieweeapp.presentation.view.interview.real_interview.loading.LoadingAnimation
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.ui.theme.VieweeColorOrange
import com.capstone.vieweeapp.ui.theme.VieweeColorShadow
import com.capstone.vieweeapp.ui.theme.VieweeColorText
import com.capstone.vieweeapp.ui.theme.noToSansKr
import com.capstone.vieweeapp.utils.CalculateDate
import com.capstone.vieweeapp.utils.Constants
import com.capstone.vieweeapp.utils.CustomRippleEffect
import timber.log.Timber

@Preview
@Composable
fun FeedbackScreenPreview() {
    FeedbackScreen(
        questionState = QuestionsState(
            questions = listOf("1", "2"),
            loading = false
        ),
        answerList = listOf("1", "2"),
        feedbackState = FeedbackState(
            feedbacks = Feedbacks(listOf("1", "2")),
            loading = false,
            resumeForFeedback = Constants.RESUME_DUMMY_DATA
        ),
        onNavigateHome = {},
        saveInterviewResult = {},
        emotionList = listOf(
            Emotion(1, 1, 1, 1, 1, 1, 1),
            Emotion(1, 1, 1, 1, 1, 1, 1),
            Emotion(1, 1, 1, 1, 1, 1, 1),
        ),
        textSentimentList = emptyList(),
    )
}

@Composable
fun FeedbackScreen(
    modifier: Modifier = Modifier,
    questionState: QuestionsState,
    answerList: List<String>,
    feedbackState: FeedbackState,
    emotionList: List<Emotion>,
    textSentimentList: List<TextSentiment>,
    onNavigateHome: () -> Unit,
    saveInterviewResult: () -> Unit,
    isReInterview: Boolean = false,
) {

    val scrollState = rememberScrollState()
    var isSaveButtonClick by remember { mutableStateOf(false) }
    var todayDate by remember { mutableStateOf(CalculateDate.dateFormatForFeedback(CalculateDate.today())) }


    Box(modifier = Modifier.fillMaxSize()) {
        if (!feedbackState.loading) {
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
                    }
                    // FeedbackTopBar
//                     modifier = modifier
//                        .fillMaxWidth()
//                        .padding(top = 40.dp, bottom = 60.dp),
                    Text(
                        text = "면접이 종료되었습니다!\n" +
                                "\n" +
                                "수고하셨습니다",
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(bottom = 60.dp),
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
                        CustomTitleText(text = "$todayDate 면접의 감정 분석")
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ExplainTriangleGraph(
                                Modifier
                                    .align(Alignment.End)
                                    .padding(end = 28.dp)
                            )
                            if (textSentimentList.isNotEmpty()) {
//                                val textSentimentListTotal = if (isReInterview) {
//                                    textSentimentList + (feedbackState.previousInterviewResult?.textSentiments
//                                        ?: emptyList())
//                                } else {
//                                    textSentimentList
//                                }
                                TriangleGraphView(
                                    intervieweeValues = textSentimentList.average(
                                        isReInterview
                                    )
                                )
                                LaunchedEffect(key1 = Unit) {
                                    Timber.tag("feedback_screen_log").d("textSentimentListTotalSize: ${textSentimentList.size}")
                                }
                            }
                        }
                        CustomTitleText(text = "$todayDate 면접의 표정 분석")
                        if (emotionList.isNotEmpty()) {
//                            val emotionListTotal = if (isReInterview) {
//                                emotionList + (feedbackState.previousInterviewResult?.emotions
//                                    ?: emptyList())
//                            } else {
//                                emotionList
//                            }
                            CircularGraphView(
                                emotions = emotionList.toPercentages().map { it.second }
                            )

                            LaunchedEffect(key1 = Unit) {
                                Timber.tag("feedback_screen_log").d("emotionListTotalSize: ${emotionList.size}")
                            }
                        }
                    }
                    CustomTitleText(
                        Modifier.padding(top = 50.dp),
                        "$todayDate 면접의 총평 피드백"
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 27.dp, horizontal = 24.dp)
                            .background(
                                VieweeColorMain.copy(alpha = 0.08f),
                                RoundedCornerShape(10.dp)
                            )
                    ) {
                        Text(
                            text = feedbackState.feedbacks.feedbacks.last(),
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
                FeedbackDetailCardGrid(
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                        .background(Color.White),
                    feedbackState = feedbackState,
                    questionState = questionState,
                    answerList = answerList,
                    todayDate = todayDate,
                    isReInterview = isReInterview,
                    emotionList = emotionList,
                    textSentimentList = textSentimentList
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
                        containerColor = if (isSaveButtonClick) Color.LightGray else VieweeColorMain.copy(
                            alpha = 1f
                        ),
                        contentColor = Color.White,
                    ),
                    onClick = {
                        if (!isSaveButtonClick) {
                            saveInterviewResult()
                            isSaveButtonClick = true
                        }
                    }
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
                            text = if (isSaveButtonClick) {
                                "저장 완료"
                            } else {
                                "모의면접 결과 저장"
                            },
                            fontFamily = noToSansKr,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        if (!isSaveButtonClick) {
                            Icon(
                                modifier = Modifier.size(17.dp),
                                imageVector = ImageVector.vectorResource(R.drawable.ic_download),
                                contentDescription = "download",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(
                    30.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoadingAnimation(Modifier.fillMaxWidth())

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    androidx.compose.material3.Text(
                        text = stringResource(id = R.string.feedback_loading_main),
                        fontFamily = noToSansKr,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = VieweeColorMain.copy(0.8f),
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                    androidx.compose.material3.Text(
                        modifier = Modifier.padding(top = 7.dp),
                        text = stringResource(id = R.string.feedback_loading_sub),
                        fontFamily = noToSansKr,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = VieweeColorMain.copy(0.5f),
                    )
                }

                androidx.compose.material3.Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.feedback_loading_sub_2),
                    fontFamily = noToSansKr,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = VieweeColorText.copy(0.5f),
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Composable
private fun FeedbackDetailCardGrid(
    modifier: Modifier = Modifier,
    feedbackState: FeedbackState,
    questionState: QuestionsState,
    answerList: List<String>,
    todayDate: String,
    isReInterview: Boolean,
    emotionList: List<Emotion>,
    textSentimentList: List<TextSentiment>
) {
    CustomTitleText(
        modifier.padding(top = 20.dp, bottom = 30.dp),
        "$todayDate 면접의 질문 피드백"
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        feedbackState.feedbacks.feedbacks.subList(0, feedbackState.feedbacks.feedbacks.size - 1)
//            .forEachIndexed { index, feedback ->
//                FeedbackDetailCardView(
//                    modifier = Modifier.padding(bottom = 10.dp),
//                    detailTitle = questionState.questions[index],
//                    detailContent = if (isReInterview) {
//                        feedbackState.previousInterviewResult?.answers?.get(index) ?: ""
//                    } else answerList[index],
//                    feedbackContent = feedback,
//                    isReInterview = isReInterview,
//                    detailContent2 = if (isReInterview) {
//                        answerList[index]
//                    } else "",
//                    emotion = emotionList[index],
//                    textSentiment = textSentimentList[index],
//                    textSentiment2 = feedbackState.previousInterviewResult?.textSentiments?.get(index)
//                        ?: TextSentiment("", Confidence(0.0,0.0,0.0))
//                )
//            }
        questionState.questions.forEachIndexed { index, question ->

            var feedbackContent by remember { mutableStateOf("") }

            LaunchedEffect(index) {
                feedbackContent = try {
                    feedbackState.feedbacks.feedbacks.subList(
                        0,
                        feedbackState.feedbacks.feedbacks.size - 1
                    )[index]
                } catch (e: IndexOutOfBoundsException) {
                    "답변 분석에 어려움이 있어 피드백을 생성하지 못했습니다."
                }
            }

            FeedbackDetailCardView(
                modifier = Modifier.padding(bottom = 10.dp),
                detailTitle = question,
                detailContent = if (isReInterview) {
                    feedbackState.previousInterviewResult?.answers?.get(index) ?: ""
                } else answerList[index],
                feedbackContent = feedbackContent,
                isReInterview = isReInterview,
                detailContent2 = if (isReInterview) {
                    answerList[index]
                } else "",
                emotion = emotionList[index],
                textSentiment = if (isReInterview) {
                    feedbackState.previousInterviewResult?.textSentiments?.get(index)
                        ?: TextSentiment("", Confidence(0.0, 0.0, 0.0))
                } else {
                    textSentimentList[index]
                },
                textSentiment2 = textSentimentList[index],
                index = index
            )
        }
    }
}


private val VerticalScrollConsumer = object : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource) = available.copy(x = 0f)
    override suspend fun onPreFling(available: Velocity) = available.copy(x = 0f)
}

private val HorizontalScrollConsumer = object : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource) = available.copy(y = 0f)
    override suspend fun onPreFling(available: Velocity) = available.copy(y = 0f)
}

fun Modifier.disabledVerticalPointerInputScroll(disabled: Boolean = true) =
    if (disabled) this.nestedScroll(VerticalScrollConsumer) else this

fun Modifier.disabledHorizontalPointerInputScroll(disabled: Boolean = true) =
    if (disabled) this.nestedScroll(HorizontalScrollConsumer) else this