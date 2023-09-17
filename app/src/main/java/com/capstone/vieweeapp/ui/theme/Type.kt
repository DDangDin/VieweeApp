package com.capstone.vieweeapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val noToSansKr = FontFamily(
    Font(R.font.noto_sans_kr_black, FontWeight.Black, FontStyle.Normal),
    Font(R.font.noto_sans_kr_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.noto_sans_kr_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.noto_sans_kr_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.noto_sans_kr_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.noto_sans_kr_semi_bold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.noto_sans_kr_thin, FontWeight.Thin, FontStyle.Normal),
)