package com.capstone.vieweeapp.presentation.view.feedback

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Refresh
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.data.source.local.entity.InterviewResult
import com.capstone.vieweeapp.presentation.state.FeedbackState
import com.capstone.vieweeapp.presentation.state.QuestionsState
import com.capstone.vieweeapp.presentation.view.interview.input_profile.CustomTitleText
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.utils.CustomRippleEffect
import com.capstone.vieweeapp.utils.CustomRippleEffect.clickableWithoutRipple
import org.opencv.android.Utils

@Composable
fun FeedbackScreenForHome(
    modifier: Modifier = Modifier,
    interviewResult: InterviewResult,
    onNavigateHome: () -> Unit,
    name: String,
    saveInterviewResult: () -> Unit,
) {

    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(bottom = 80.dp)
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
                }
                Text(
                    text = "면접이 종료되었습니다!\n" +
                            "\n" +
                            "수고하셨습니다",
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp, bottom = 60.dp),
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
                    DetailTitle = interviewResult.questions[index],
                    DetailContent = answer
                )
            }
    }
//    LazyColumn(
//        contentPadding = PaddingValues(horizontal = 30.dp),
//        verticalArrangement = Arrangement.spacedBy(10.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        // 그리드 각 항목들이 사이즈 비율이 안 맞았던 이유
//        // -> LazyHorizontalGrid 높이에 고정 값 주려면 패딩 값이나 space 값 도 계산 해서 해줘야…
//        modifier = modifier
//            .height(500.dp)
//            .disabledVerticalPointerInputScroll(),
//        content = {
//            items(answerFeedbackList.size) { index ->
//                FeedbackDetailCardView(
//                    modifier = Modifier,
//                    DetailTitle = feedbackState.questionList[index],
//                    DetailContent = answerFeedbackList[index]
//                )
//            }
//        })
}


@Composable
fun FeedbackDetailCardViewForHome(
    modifier: Modifier = Modifier,
    DetailTitle: String,
    DetailContent: String
) {
    var isExpanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }


    val extraPadding by animateDpAsState(
        if (isExpanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
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
                    text = DetailTitle,
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
                if (isExpanded) Text(
                    text = DetailContent,
                    modifier = Modifier
                        .padding(10.dp),
                    textAlign = TextAlign.Left,
                    fontSize = 13.sp,
                    color = Color.Gray,
                ) else " "
            }
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