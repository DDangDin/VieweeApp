package com.capstone.vieweeapp.presentation.view.interview.real_interview

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
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
import com.capstone.vieweeapp.utils.Constants

@Composable
fun RealInterviewScreenTopBar(
    modifier: Modifier = Modifier,
    interviewerTurn: Boolean,
    index: Int,
    isFinish: Boolean,
) {

    val icon = if (interviewerTurn) R.drawable.ic_speacker else R.drawable.ic_microphone

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            8.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!isFinish) {
            Icon(
                modifier = Modifier.size(23.dp),
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = "icon",
                tint = VieweeColorMain
            )
            Text(
                text = if (interviewerTurn) {
                    "${index + 1}번째 질문"
                } else {
                    "${index + 1}번째 답변"
                },
                fontFamily = noToSansKr,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = VieweeColorMain.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(
                        if (interviewerTurn) VieweeColorOrange else Color.Transparent,
                        CircleShape
                    )
            )
        } else {
            Text(
                text = stringResource(id = R.string.interview_question_finish),
                fontFamily = noToSansKr,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = VieweeColorMain.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RealInterviewTopBarPreview() {
    RealInterviewScreenTopBar(
        interviewerTurn = true,
        index = 0,
        isFinish = true
    )
}