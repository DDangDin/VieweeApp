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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.utils.CustomRippleEffect
import com.capstone.vieweeapp.utils.CustomRippleEffect.clickableWithoutRipple

@Composable
fun FeedbackDetailCardView(
    modifier: Modifier = Modifier,
    detailTitle: String,
    detailContent: String,
    feedbackContent: String,
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
                .border(1.dp, (VieweeColorMain.copy(alpha = 0.5f)), RoundedCornerShape(10.dp))
                .clickableWithoutRipple(
                    interactionSource = interactionSource,
                    onClick = { isExpanded = !isExpanded }
                ),
            ) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp)),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    text = detailTitle,
                    modifier = Modifier
                        .padding(top = 10.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    color = Color.DarkGray.copy(.8f)
                )

                IconButton(
                    onClick = { isExpanded = !isExpanded },

                    ) {
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        "Show", modifier = Modifier
                            .alpha(.7f)
                            .fillMaxWidth()

                    )
                }
                if (isExpanded) {
                    Text(
                        text = detailContent,
                        modifier = Modifier
                            .padding(10.dp),
                        textAlign = TextAlign.Left,
                        fontSize = 13.sp,
                        color = Color.Gray,
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        text = "-> $feedbackContent",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Start
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
        detailContent = "DetailContent",
        detailTitle = "DetailTitle",
        feedbackContent = "피드백 들어갈 부분"
    )
}