package com.capstone.vieweeapp.presentation.view.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.ui.theme.noToSansKr

@Preview(showBackground = false)
@Composable
fun ReFeedbackBadgePreview() {
    ReFeedbackBadge(count = 1)
}

@Composable
fun ReFeedbackBadge(
    modifier: Modifier = Modifier,
    count: Int,
) {

    Box(
        modifier = modifier
            .background(VieweeColorMain, RoundedCornerShape(20.dp))
            .padding(10.dp)
    ) {
        Text(
            text = "재면접 ${count}회차",
            fontFamily = noToSansKr,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = Color.White
        )
    }
}