package com.capstone.vieweeapp.presentation.view.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.ui.theme.VieweeColorHomeText
import com.capstone.vieweeapp.ui.theme.noToSansKr
import com.capstone.vieweeapp.utils.CalculateDate

@Composable
fun HomeIntroductionText(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            append(
                AnnotatedString(stringResource(id = R.string.home_text_title_1))
            )
            append("\n")
            append(
                AnnotatedString(
                    stringResource(id = R.string.home_text_title_2),
                    spanStyle = SpanStyle(
                        fontFamily = noToSansKr,
                        fontWeight = FontWeight.SemiBold,
                    )
                )
            )
        },
        color = VieweeColorHomeText.copy(alpha = 0.7f),
        fontSize = 18.sp,
        fontFamily = noToSansKr,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Start,
    )
}

@Composable
fun HomeDateText(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = CalculateDate.today(),
        fontSize = 12.sp,
        fontFamily = noToSansKr,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Start,
        color = VieweeColorHomeText.copy(alpha = 0.5f)
    )
}