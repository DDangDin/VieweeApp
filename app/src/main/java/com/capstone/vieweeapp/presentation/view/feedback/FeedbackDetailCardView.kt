package com.capstone.vieweeapp.presentation.view.feedback

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.data.source.local.entity.Emotion
import com.capstone.vieweeapp.data.source.local.entity.TextSentiment
import com.capstone.vieweeapp.data.source.local.entity.toFloatList
import com.capstone.vieweeapp.data.source.local.entity.toPairList
import com.capstone.vieweeapp.data.source.remote.clova.dto.Confidence
import com.capstone.vieweeapp.presentation.view.feedback.graph.CircularGraphColor
import com.capstone.vieweeapp.presentation.view.feedback.graph.ExplainTriangleGraph
import com.capstone.vieweeapp.presentation.view.feedback.graph.TriangleGraphView
import com.capstone.vieweeapp.presentation.view.feedback.graph.TwoTriangles
import com.capstone.vieweeapp.presentation.view.interview.input_profile.CustomTitleText
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.ui.theme.VieweeColorText
import com.capstone.vieweeapp.ui.theme.noToSansKr
import com.capstone.vieweeapp.utils.CustomRippleEffect
import com.capstone.vieweeapp.utils.CustomRippleEffect.clickableWithoutRipple
import com.capstone.vieweeapp.utils.FacialEmotionNames
import timber.log.Timber

@Composable
fun FeedbackDetailCardView(
    modifier: Modifier = Modifier,
    detailTitle: String,
    detailContent: String,
    feedbackContent: String,
    isReInterview: Boolean = false,
    detailContent2: String = "",
    emotion: Emotion,
    emotion2: Emotion,
    textSentiment: TextSentiment,
    textSentiment2: TextSentiment,
    index: Int
) {
    var isExpanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

//    val extraPadding by animateDpAsState(
//        if (isExpanded) 48.dp else 0.dp,
//        animationSpec = tween(),
//        label = "extraPadding"
//    )

    // 긍정 부정 하이라이팅 팝업?을 위한 변수들
//    var tapPosition by remember { mutableStateOf(Offset.Zero) }
//    val density = LocalDensity.current
//    val (xDp, yDp) = with(density) {
//        (tapPosition.x.toDp()) to (tapPosition.y.toDp())
//    }
//    var tapDialogVisible by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {
        Timber.tag("FeedbackDetailCardView_Log").d("" +
                "emotion: ${emotion}\n" +
                "")
    }

    CompositionLocalProvider(LocalRippleTheme provides CustomRippleEffect.NoRippleTheme) {
        Box(
            modifier = modifier
                .fillMaxWidth()
//                .border(1.3.dp, (VieweeColorMain.copy(alpha = 0.5f)), RoundedCornerShape(10.dp))
                .border(
                    2.dp,
                    if (isExpanded) Color.Transparent else VieweeColorMain.copy(0.5f),
                    RoundedCornerShape(10.dp)
                )
                .background(VieweeColorText.copy(alpha = 0.05f), RoundedCornerShape(10.dp))
                .clickableWithoutRipple(
                    interactionSource = interactionSource,
                    onClick = { isExpanded = !isExpanded }
                )
                .animateContentSize(tween()),
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 11.dp)
//                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 11.dp),
                    text = "질문${index+1}: $detailTitle",
                    textAlign = TextAlign.Center,
                    fontFamily = noToSansKr,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.DarkGray.copy(.8f),
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
                if (!isExpanded) {
                    Icon(
                        modifier = Modifier
                            .size(16.dp)
                            .clickableWithoutRipple(
                                interactionSource = MutableInteractionSource(),
                                onClick = { isExpanded = !isExpanded }
                            ),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_drop_down),
                        contentDescription = "Show",
                        tint = Color(0xCC92979F),
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 14.dp),
                        text = buildAnnotatedString {
                            append(
                                AnnotatedString(
                                    text = if (isReInterview) "1회차 답변: " else "답변: ",
                                    spanStyle = SpanStyle(
                                        color = VieweeColorText.copy(alpha = 0.5f),
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            )
                            append("\n")
                            append("\n")
                            append(
                                AnnotatedString(
                                    text = detailContent,
                                    spanStyle = SpanStyle(
                                        color = VieweeColorText.copy(alpha = 0.5f)
                                    ),
                                )
                            )
                        },
                        textAlign = TextAlign.Start,
                        fontFamily = noToSansKr,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = VieweeColorText,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                    if (isReInterview) {
                        Icon(
                            modifier = Modifier
                                .size(66.dp, 20.dp),
//                                .padding(vertical = 15.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_drop_down),
                            contentDescription = "arrowDropDown",
                            tint = Color(0x1A92979F),
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            text = buildAnnotatedString {
                                append(
                                    AnnotatedString(
                                        text = "2회차 답변: ",
                                        spanStyle = SpanStyle(
                                            color = VieweeColorText.copy(alpha = 0.8f),
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                )
                                append("\n")
                                append("\n")
                                append(
                                    AnnotatedString(
                                        text = detailContent2,
                                        spanStyle = SpanStyle(
                                            color = VieweeColorText.copy(alpha = 0.8f)
                                        ),
                                    )
                                )
                            },
                            textAlign = TextAlign.Start,
                            fontFamily = noToSansKr,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = VieweeColorText,
                            style = TextStyle(
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            )
                        )
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            thickness = 1.dp,
                            color = VieweeColorText.copy(alpha = 0.2f)
                        )
                    }
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 25.dp),
                        text = "피드백: \n\n$feedbackContent",
                        textAlign = TextAlign.Start,
                        fontFamily = noToSansKr,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = VieweeColorMain.copy(alpha = 0.8f),
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            25.dp,
                            alignment = Alignment.CenterVertically
                        )
                    ) {
                        CustomTitleText(
                            text = stringResource(id = R.string.feedback_detail_card_facial_graph),
                            fontSize = 17.sp
                        )
                        if (isReInterview) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(19.dp, alignment = Alignment.CenterVertically)
                            ) {
                                FacialAnalyzedGraph(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    emotionTopThree = emotion
                                        .toPairList()
                                        .sortedBy { it.second }
                                        .reversed()
                                        .subList(0, 3)
                                        .map { it.first }
                                )
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_replay_trianglegraph_triangle),
                                    contentDescription = "arrowRight",
                                    tint = Color.Gray,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .rotate(90f)
                                )
                                FacialAnalyzedGraph(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    emotionTopThree = emotion2
                                        .toPairList()
                                        .sortedBy { it.second }
                                        .reversed()
                                        .subList(0, 3)
                                        .map { it.first }
                                )
                            }
                        } else {
                            FacialAnalyzedGraph(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                emotionTopThree = emotion
                                    .toPairList()
                                    .sortedBy { it.second }
                                    .reversed()
                                    .subList(0, 3)
                                    .map { it.first }
                            )
                        }
                        CustomTitleText(
                            modifier = Modifier.padding(top = 15.dp),
                            text = stringResource(id = R.string.feedback_detail_card_triangle_graph),
                            fontSize = 17.sp
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            ExplainTriangleGraph(
                                Modifier
                                    .align(Alignment.End)
                                    .padding(end = 28.dp)
                            )
//                            TriangleGraphView(
//                                modifier = Modifier,
//                                intervieweeValues = textSentiment.toFloatList()
//                            )

                            if (isReInterview) {
                                TwoTriangles(
                                    modifier = Modifier.fillMaxWidth(),
                                    values1 = textSentiment.toFloatList(),
                                    values2 = textSentiment2.toFloatList()
                                )
                            } else {
                                TriangleGraphView(
                                    modifier = Modifier,
                                    intervieweeValues = textSentiment.toFloatList()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FacialAnalyzedGraph(
    modifier: Modifier = Modifier,
    emotionTopThree: List<String>, // 상위 3개만
) {

    // Top3 만
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(
            10.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        emotionTopThree.forEachIndexed { index, emo ->

            val rankText = when (index) {
                0 -> stringResource(id = R.string.feedback_detailcard_facialgraph_1st)
                1 -> stringResource(id = R.string.feedback_detailcard_facialgraph_2nd)
                2 -> stringResource(id = R.string.feedback_detailcard_facialgraph_3rd)
                else -> {
                    ""
                }
            }

            FacialCircleShape(
                rankText = rankText,
                emotion = emo
            )
        }
    }
}

@Composable
private fun FacialCircleShape(
    modifier: Modifier = Modifier,
    rankText: String,
    emotion: String
) {

    // 순위 별 글씨 크기 / 도형 크기
    // 1st -> 10.sp / 70.dp
    // 2nd -> 7.sp / 50.dp
    // 3rd -> 7.sp / 35.dp
    val rank1 = stringResource(id = R.string.feedback_detailcard_facialgraph_1st)
    val rank2 = stringResource(id = R.string.feedback_detailcard_facialgraph_2nd)
    val rank3 = stringResource(id = R.string.feedback_detailcard_facialgraph_3rd)

    val fontSize = when (rankText) {
        rank1 -> 14.sp
        rank2 -> 11.sp
        rank3 -> 11.sp
        else -> 11.sp
    }

    var shapeSize by remember { mutableStateOf(0.dp) }
    LaunchedEffect(key1 = Unit) {
        shapeSize = when (rankText) {
            rank1 -> 80.dp
            rank2 -> 60.dp
            rank3 -> 45.dp
            else -> 50.dp
        }
    }

    val paddingValue = when (rankText) {
        rank1 -> PaddingValues(
            bottom = 17.dp,
            end = 12.dp
        )

        rank2 -> PaddingValues(
            bottom = 13.dp,
            end = 10.dp
        )

        rank3 -> PaddingValues(
            bottom = 10.dp,
            end = 7.dp
        )

        else -> PaddingValues(bottom = 15.dp, end = 10.dp)
    }


    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically)
    ) {
        Box(
            modifier = Modifier.wrapContentSize()
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(CircleShape)
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 3000,
                            easing = LinearOutSlowInEasing
                        )
                    )
                    .size(shapeSize),
                imageVector = Icons.Default.Circle,
                contentDescription = "circle",
                tint = CircularGraphColor.colorsMap[emotion] ?: Color.LightGray,
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(paddingValue),
                text = rankText,
                fontFamily = noToSansKr,
                fontWeight = FontWeight.Medium,
                fontSize = fontSize,
                color = VieweeColorText.copy(alpha = 0.8f),
            )
        }
        Text(
            modifier = Modifier,
            text = FacialEmotionNames.translationToKorean(emotion),
            fontFamily = noToSansKr,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            color = VieweeColorText.copy(alpha = 0.8f),
        )
    }
}


@Preview(showBackground = true)
@Composable
fun FacialAnalyzeGraphPreview() {

    val emotion = Emotion(1, 5, 2, 10, 1, 1, 1)
        .toPairList()
        .sortedBy { it.second }
        .reversed()
        .subList(0, 3)
        .map { it.first }
    FacialAnalyzedGraph(
        emotionTopThree = emotion
    )
}

@Preview(showBackground = true)
@Composable
fun FacialCircleGraphPreview() {
    FacialCircleShape(
        rankText = "1st",
        emotion = "Neutral"
    )
}

@Preview(showBackground = true)
@Composable
fun FeedFeedbackDetailCardViewPreview() {

    val emotion = Emotion(1, 5, 2, 10, 1, 1, 1)

    FeedbackDetailCardView(
        detailTitle = "팀에 갈등이 생길경우 어떻게 해결하나요?",
        detailContent = "DetailContentDetailContentDetailContentDetailContentDetailContentDetailContentDetailContentDetailContentDetailContentDetailContentDetailContent",
        feedbackContent = "피드백 들어갈 부분피드백 들어갈 부분피드백 들어갈 부분피드백 들어갈 부분피드백 들어갈 부분피드백 들어갈 부분피드백 들어갈 부분피드백 들어갈 부분피드백 들어갈 부분피드백 들어갈 부분",
        isReInterview = true,
        detailContent2 = "답변(2회차)",
        emotion = emotion,
        emotion2 = emotion,
        textSentiment = TextSentiment(
            "",
            Confidence(0.0, 0.0, 0.0)
        ),
        textSentiment2 = TextSentiment(
            "",
            Confidence(0.0, 0.0, 0.0)
        ),
        index = 0
    )
}