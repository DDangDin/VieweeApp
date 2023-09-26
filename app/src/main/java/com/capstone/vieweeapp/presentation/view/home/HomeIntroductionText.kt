package com.capstone.vieweeapp.presentation.view.home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.capstone.vieweeapp.utils.CalculateDate

@Composable
fun HomeIntroductionText(
    modifier: Modifier = Modifier,
    username: String
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            append(
                AnnotatedString(
                    text = stringResource(id = R.string.home_text_title_1_1),
                )
            )
            if (username.isNotEmpty()) {
                append(
                    AnnotatedString(
                        text = " $username",
                    )
                )
                append(
                    AnnotatedString(
                        text = stringResource(id = R.string.home_text_title_1_2),
                    )
                )
            }
            append("\n")
            append(
                AnnotatedString(
                    text = stringResource(id = R.string.home_text_title_2),
                    spanStyle = SpanStyle(
                        fontFamily = noToSansKr,
                        fontWeight = FontWeight.SemiBold,
                    ),
                )
            )
            append("\n")
            append(
                AnnotatedString(
                    text = CalculateDate.today(),
                    spanStyle = SpanStyle(
                        fontSize = 12.sp,
                        fontFamily = noToSansKr,
                        fontWeight = FontWeight.Medium,
                        color = VieweeColorText.copy(alpha = 0.5f)
                    ),
                )
            )
        },
        color = VieweeColorText.copy(alpha = 0.7f),
        fontSize = 18.sp,
        fontFamily = noToSansKr,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Start,
        lineHeight = 27.sp
    )
}

@Preview
@Composable
fun HomeIntroductionTextPreview() {
    HomeIntroductionText(username = "김길동")
}