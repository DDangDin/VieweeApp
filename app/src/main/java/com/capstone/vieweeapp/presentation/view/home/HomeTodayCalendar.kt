package com.capstone.vieweeapp.presentation.view.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.ui.theme.VieweeColorHomeText
import com.capstone.vieweeapp.ui.theme.noToSansKr

@Composable
fun HomeTodayCalendar(
    modifier: Modifier = Modifier,
    username: String
) {
    
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = buildAnnotatedString {
                append(
                    AnnotatedString(
                        text = username,
                        spanStyle = SpanStyle(
                            fontFamily = noToSansKr,
                            fontWeight = FontWeight.SemiBold,
                            color = VieweeColorHomeText.copy(0.8f)
                        )
                    )
                )
                append(
                    AnnotatedString(
                        text = stringResource(id = R.string.home_text_calendar_1),
                        spanStyle = SpanStyle(
                            fontFamily = noToSansKr,
                            fontWeight = FontWeight.Normal,
                            color = VieweeColorHomeText.copy(0.8f)
                        )
                    )
                )
            },
            fontSize = 20.sp,
            textAlign = TextAlign.Start
        )
        // 임시 (Calendar 데이터가 아직 없음)
        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = stringResource(id = R.string.home_text_calendar_2),
            fontFamily = noToSansKr,
            fontWeight = FontWeight.Normal,
            color = VieweeColorHomeText.copy(0.7f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeCalendarPreview() {
    HomeTodayCalendar(username = "김길동")
}