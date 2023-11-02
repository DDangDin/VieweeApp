package com.capstone.vieweeapp.presentation.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.data.source.local.entity.InterviewResult
import com.capstone.vieweeapp.ui.theme.VieweeColorText
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.ui.theme.noToSansKr
import com.capstone.vieweeapp.utils.Constants

@Composable
fun InterviewResultCardView(
    modifier: Modifier = Modifier,
    index: Int,
    listSize: Int,
    interviewResult: InterviewResult,
) {

    Box(
        modifier = modifier
            .wrapContentSize()
            .background(VieweeColorText.copy(alpha = 0.05f))
    ) {
        Row(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 1.dp, bottom = 6.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(18.dp, alignment = Alignment.Start)
        ) {
            Text(
                text = "${index + 1}",
                fontSize = 80.sp,
                fontFamily = noToSansKr,
                fontWeight = FontWeight.Normal,
                color = VieweeColorMain.copy(alpha = 0.5f),
                style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    ),
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Top,
                        trim = LineHeightStyle.Trim.FirstLineTop
                    )
                )
            )
            Text(
                modifier = Modifier.padding(top = 30.dp),
                text = buildAnnotatedString {
                    append(text = interviewResult.date)
                    append(" ")
                    append(text = stringResource(id = R.string.home_interview_result_card_1))
                },
                fontSize = 14.sp,
                fontFamily = noToSansKr,
                fontWeight = FontWeight.Light,
                color = VieweeColorText.copy(alpha = 0.5f),
                style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                )
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .wrapContentSize()
                .padding(top = 6.dp, end = 6.dp, bottom = 6.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFDDDFE3).copy(alpha = 0.4f)),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 8.dp, vertical = 1.dp),
                text = "${index + 1}/${listSize}",
                fontSize = 13.sp,
                fontFamily = noToSansKr,
                fontWeight = FontWeight.Thin,
                color = Color(0xFF3E3A3A).copy(alpha = 0.6f),
                style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InterviewResultCardViewPreview() {
    InterviewResultCardView(
        index = 0,
        listSize = 6,
        interviewResult = Constants.INTERVIEW_RESULT_EMPTY_DATA
    )
}