package com.capstone.vieweeapp.presentation.view.interview.select_resume

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.ui.theme.VieweeColorShadow
import com.capstone.vieweeapp.ui.theme.VieweeColorText
import com.capstone.vieweeapp.ui.theme.noToSansKr
import com.capstone.vieweeapp.utils.InterviewerEngine

@Preview
@Composable
fun SelectBottomSheetPreview() {
    SelectBottomSheet(backgroundColor = Color(0xFFF5F6F8), prepareInterview = {})
}

@Preview
@Composable
fun EngineSectionPreview() {
    EngineSection(
        logo = R.drawable.img_vector_gpt,
        model = R.string.select_resume_screen_gpt_model,
        company = R.string.select_resume_screen_gpt_company,
        content = R.string.select_resume_screen_gpt_content,
        isVector = true,
        prepareInterview = {}
    )
}

@Composable
fun SelectBottomSheet(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    prepareInterview: (String) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(backgroundColor),
        verticalArrangement = Arrangement.spacedBy(40.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.select_resume_screen_bottom_sheet_title),
            fontFamily = noToSansKr,
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold,
            color = VieweeColorMain.copy(0.9f),
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                47.dp,
                alignment = Alignment.CenterHorizontally
            ),
        ) {
            EngineSection(
                logo = R.drawable.img_vector_gpt,
                model = R.string.select_resume_screen_gpt_model,
                company = R.string.select_resume_screen_gpt_company,
                content = R.string.select_resume_screen_gpt_content,
                isVector = true,
                prepareInterview = { prepareInterview(InterviewerEngine.GPT) }
            )
            Divider(
                color = VieweeColorText.copy(0.1f),
                modifier = Modifier
                    .height(200.dp)  //fill the max height
                    .width(2.dp)
            )
            EngineSection(
                logo = R.drawable.img_palm2,
                model = R.string.select_resume_screen_palm2_model,
                company = R.string.select_resume_screen_palm2_company,
                content = R.string.select_resume_screen_palm2_content,
                isVector = false,
                prepareInterview = { prepareInterview(InterviewerEngine.PALM2) }
            )
        }
    }
}

@Composable
private fun EngineSection(
    modifier: Modifier = Modifier,
    isVector: Boolean,
    @DrawableRes logo: Int,
    @StringRes model: Int,
    @StringRes company: Int,
    @StringRes content: Int,
    prepareInterview: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isVector) {
            Image(
                modifier = Modifier.size(72.dp),
                imageVector = ImageVector.vectorResource(logo),
                contentDescription = "modelLogo"
            )
        } else {
            Image(
                modifier = Modifier.size(72.dp),
                painter = painterResource(id = logo),
                contentDescription = "modelLogo"
            )
        }
        Text(
            modifier = Modifier.padding(top = 28.dp),
            text = buildAnnotatedString {
                append(
                    AnnotatedString(
                        text = stringResource(id = model)
                    )
                )
                append("\n")
                append(
                    AnnotatedString(
                        text = stringResource(id = company),
                        spanStyle = SpanStyle(
                            fontSize = 10.sp,
                            color = VieweeColorMain.copy(0.5f)
                        )
                    )
                )
            },
            fontFamily = noToSansKr,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = VieweeColorMain.copy(0.9f),
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            ),
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.padding(top = 20.dp, bottom = 32.dp),
            text = stringResource(id = content),
            fontFamily = noToSansKr,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0x66060F1F),
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            ),
            textAlign = TextAlign.Center
        )
        OutlinedButton(
            modifier = Modifier
                .width(80.dp)
                .height(40.dp),
            border = BorderStroke(0.dp, Color.Transparent),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = VieweeColorMain,
                contentColor = Color.Transparent,
            ),
            onClick = prepareInterview
        ) {
            Text(
                text = "선택",
                fontFamily = noToSansKr,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xE6E9F8FF)
            )
        }
    }
}
