package com.capstone.vieweeapp.presentation.view.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.ui.theme.VieweeColorText
import com.capstone.vieweeapp.ui.theme.noToSansKr

@Composable
fun HomeInterviewResultText(
    modifier: Modifier = Modifier,
    username: String,
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = buildAnnotatedString {
                if (username.isNotEmpty()) {
                    append(
                        AnnotatedString(
                            text = "${username}님의 ",
                            spanStyle = SpanStyle(
                                fontFamily = noToSansKr,
                                fontWeight = FontWeight.SemiBold,
                                color = VieweeColorText.copy(0.8f)
                            )
                        )
                    )
                }
                append(
                    AnnotatedString(
                        text = stringResource(id = R.string.home_interview_result_1),
                        spanStyle = SpanStyle(
                            fontFamily = noToSansKr,
                            fontWeight = FontWeight.Normal,
                            color = VieweeColorText.copy(0.8f)
                        )
                    )
                )
            },
            fontSize = 20.sp,
            textAlign = TextAlign.Start
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeInterviewResultPreview() {
    HomeInterviewResultText(username = "김길동")
}