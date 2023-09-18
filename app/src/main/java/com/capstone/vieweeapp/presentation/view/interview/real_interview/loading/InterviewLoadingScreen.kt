package com.capstone.vieweeapp.presentation.view.interview.real_interview.loading

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.presentation.state.QuestionsState
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.ui.theme.VieweeColorOrange

@Composable
fun InterviewLoadingScreen(
    modifier: Modifier = Modifier,
    backToMain: () -> Unit,
    startInterview: () -> Unit,
    onPopBackStack: () -> Unit,
    questionsState: QuestionsState
) {

    // Success
    LaunchedEffect(key1 = questionsState){
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
                    40.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(90.dp),
                    strokeWidth = 2.dp,
                    color = VieweeColorMain
                )

                Text(
                    text = stringResource(id = R.string.interview_loading_main_text),
                    fontSize = 25.sp,
                    color = VieweeColorMain,
                    fontWeight = FontWeight.Bold
                )

                // 종료하기 기능이 애매함
                Text(
                    modifier = Modifier.clickable {
                        backToMain()
                    },
                    text = stringResource(id = R.string.interview_loading_exit_text),
                    fontSize = 12.sp,
                    color = VieweeColorOrange,
                    fontWeight = FontWeight.Light,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}