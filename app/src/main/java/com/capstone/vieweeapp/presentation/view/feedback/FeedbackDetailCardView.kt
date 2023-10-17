package com.capstone.vieweeapp.presentation.view.feedback

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.ui.theme.VieweeColorText
import com.capstone.vieweeapp.ui.theme.noToSansKr
import com.capstone.vieweeapp.utils.CustomRippleEffect
import com.capstone.vieweeapp.utils.CustomRippleEffect.clickableWithoutRipple

@Composable
fun FeedbackDetailCardView(
    modifier: Modifier = Modifier,
    detailTitle: String,
    detailContent: String,
    feedbackContent: String,
    isReInterview: Boolean = false,
    detailContent2: String = "",
) {
    var isExpanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val scope = rememberCoroutineScope()

    val extraPadding by animateDpAsState(
        if (isExpanded) 48.dp else 0.dp,
        animationSpec = tween()
    )

    CompositionLocalProvider(LocalRippleTheme provides CustomRippleEffect.NoRippleTheme) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .border(1.3.dp, (VieweeColorMain.copy(alpha = 0.5f)), RoundedCornerShape(10.dp))
                .clickableWithoutRipple(
                    interactionSource = interactionSource,
                    onClick = { isExpanded = !isExpanded }
                ),
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(15.dp)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "질문: $detailTitle",
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
                            .padding(top = 11.dp)
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
                            .padding(vertical = 20.dp),
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
                            tint = Color(0xCC92979F),
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
                            .padding(top = 20.dp),
                        text = "피드백: $feedbackContent",
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
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeedFeedbackDetailCardViewPreview() {
    FeedbackDetailCardView(
        detailTitle = "팀에 갈등이 생길경우 어떻게 해결하나요?",
        detailContent = "DetailContentDetailContentDetailContentDetailContentDetailContentDetailContentDetailContentDetailContentDetailContentDetailContentDetailContent",
        feedbackContent = "피드백 들어갈 부분피드백 들어갈 부분피드백 들어갈 부분피드백 들어갈 부분피드백 들어갈 부분피드백 들어갈 부분피드백 들어갈 부분피드백 들어갈 부분피드백 들어갈 부분피드백 들어갈 부분",
        isReInterview = true,
        detailContent2 = "답변(2회차)"
    )
}