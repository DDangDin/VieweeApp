package com.capstone.vieweeapp.presentation.view.interview.real_interview

import android.view.MotionEvent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.ui.theme.VieweeColorOrange
import com.capstone.vieweeapp.ui.theme.noToSansKr

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RealInterviewScreenBottomBar(
    modifier: Modifier = Modifier,
    time: String,
    onStop: () -> Unit,
    onNextTurn: () -> Unit,
    interviewFinishState: Boolean,
    startFeedback: () -> Unit
) {

    var isStopButtonTouched by remember { mutableStateOf(false) }
    var isNextButtonTouched by remember { mutableStateOf(false) }

    val stopIcon = if (isStopButtonTouched) {
        R.drawable.ic_interview_stop_clicked
    } else {
        R.drawable.ic_interview_stop
    }
    val nextIcon = if (isNextButtonTouched) {
        R.drawable.ic_interview_btn_next_clicked
    } else {
        R.drawable.ic_interview_btn_next
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier
                .size(50.dp)
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            isStopButtonTouched = true
                            onStop()
                        }

                        MotionEvent.ACTION_UP -> {
                            isStopButtonTouched = false
                        }

                        MotionEvent.ACTION_HOVER_EXIT -> {
                            isStopButtonTouched = true
                            onStop()
                        }

                        MotionEvent.ACTION_HOVER_ENTER -> {
                            isStopButtonTouched = false
                        }

                        else -> false
                    }
                    true
                },
            imageVector = ImageVector.vectorResource(stopIcon),
            contentDescription = "stop",
            tint = VieweeColorMain
        )
        Text(
            text = "소요시간: $time",
            fontFamily = noToSansKr,
            fontWeight = FontWeight.Light,
            fontSize = 20.sp,
            color = VieweeColorMain,
            textAlign = TextAlign.Center
        )
        Icon(
            modifier = Modifier
                .size(50.dp)
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            isNextButtonTouched = true
                            if (interviewFinishState) {
                                startFeedback()
                            } else {
                                onNextTurn()
                            }
                        }

                        MotionEvent.ACTION_UP -> {
                            isNextButtonTouched = false
                        }

                        MotionEvent.ACTION_HOVER_EXIT -> {
                            isNextButtonTouched = true
                            if (interviewFinishState) {
                                startFeedback()
                            } else {
                                onNextTurn()
                            }
                        }

                        MotionEvent.ACTION_HOVER_ENTER -> {
                            isNextButtonTouched = false
                        }

                        else -> false
                    }
                    true
                },
            imageVector = if (interviewFinishState) {
                ImageVector.vectorResource(R.drawable.ic_btn_interview_finish)
            } else {
                ImageVector.vectorResource(nextIcon)
            },
            contentDescription = "next",
            tint = if (interviewFinishState) {
                VieweeColorOrange
            } else {
                VieweeColorMain
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RealInterviewBottomBarPreview() {
    RealInterviewScreenBottomBar(
        time = "00:00",
        onStop = {},
        onNextTurn = {},
        interviewFinishState = false,
        startFeedback = {}
    )
}