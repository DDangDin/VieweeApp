package com.capstone.vieweeapp.presentation.view.interview.real_interview.loading

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.presentation.state.QuestionsState
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.ui.theme.VieweeColorOrange
import com.capstone.vieweeapp.ui.theme.VieweeColorText
import com.capstone.vieweeapp.ui.theme.noToSansKr
import kotlinx.coroutines.delay
import timber.log.Timber

@Preview(showBackground = true)
@Composable
fun InterviewLoadingScreenPreview() {
    InterviewLoadingScreen(
        backToMain = { },
        startInterview = { },
        onPopBackStack = { },
        questionsState = QuestionsState()
    )
}

@Composable
fun InterviewLoadingScreen(
    modifier: Modifier = Modifier,
    backToMain: () -> Unit,
    startInterview: () -> Unit,
    onPopBackStack: () -> Unit,
    questionsState: QuestionsState
) {

    // Success
    LaunchedEffect(key1 = questionsState) {
        if (questionsState.questions.isNotEmpty()) {
            startInterview()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        // Error
        if (questionsState.error.isNotEmpty() && !questionsState.loading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.interview_loading_error),
                    fontSize = 25.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    text = questionsState.error,
                    fontSize = 12.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Light,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = { backToMain() }
                ) {
                    Text(text = "돌아 가기")
                }
            }
        } else {
            // Loading
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
                    Text(
                        text = stringResource(id = R.string.interview_loading_main),
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
                    Text(
                        modifier = Modifier.padding(top = 7.dp),
                        text = stringResource(id = R.string.interview_loading_sub),
                        fontFamily = noToSansKr,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = VieweeColorMain.copy(0.5f),
                    )
                }

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.interview_loading_sub_2),
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

                // 종료하기 기능이 애매함
                Text(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .clickable {
                            backToMain()
                        },
                    text = stringResource(id = R.string.interview_loading_exit),
                    fontSize = 12.sp,
                    color = VieweeColorOrange,
                    fontWeight = FontWeight.Light,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingAnimationPreview() {
    LoadingAnimation()
}

@Composable
fun LoadingAnimation(modifier: Modifier = Modifier) {

    val pacmanAnimatedFloat = remember { Animatable(0f) }

    LaunchedEffect(pacmanAnimatedFloat) {
        delay(500) // to avoid repeated delays
        pacmanAnimatedFloat.animateTo(
            targetValue = 130f, animationSpec = infiniteRepeatable(
                animation = tween(1500, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            17.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        Icon(
            modifier = Modifier
                .size(39.dp)
                .offset(x = pacmanAnimatedFloat.value.dp),
            imageVector = ImageVector.vectorResource(R.drawable.ic_pacman),
            contentDescription = "pacman",
            tint = VieweeColorMain,
        )
        Icon(
            modifier = Modifier
                .size(10.dp),
            imageVector = Icons.Default.Circle,
            contentDescription = "circle",
            tint = if (pacmanAnimatedFloat.value <= 35f) {
                Color(0xFFF2EA9E)
            } else {
                Color.Transparent
            }
        )
        Icon(
            modifier = Modifier
                .size(10.dp),
            imageVector = Icons.Default.Circle,
            contentDescription = "circle",
            tint = if (pacmanAnimatedFloat.value <= 62f) {
                Color(0xFF6B993D).copy(0.38f)
            } else {
                Color.Transparent
            }
        )
        Icon(
            modifier = Modifier
                .size(10.dp),
            imageVector = Icons.Default.Circle,
            contentDescription = "circle",
            tint = if (pacmanAnimatedFloat.value <= 89f) {
                Color(0xFFFF8057).copy(0.6f)
            } else {
                Color.Transparent
            }
        )
        Icon(
            modifier = Modifier
                .size(10.dp),
            imageVector = Icons.Default.Circle,
            contentDescription = "circle",
            tint =
            if (pacmanAnimatedFloat.value <= 116f) {
                Color(0xFFCE7DAE)
            } else {
                Color.Transparent
            }
        )
    }
}