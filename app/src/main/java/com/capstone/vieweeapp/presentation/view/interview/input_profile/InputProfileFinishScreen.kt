package com.capstone.vieweeapp.presentation.view.interview.input_profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.ui.theme.noToSansKr

@Composable
fun InputProfileFinishScreen(
    modifier: Modifier = Modifier,
    onFinish: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = stringResource(id = R.string.input_profile_finish_text),
            fontFamily = noToSansKr,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            color = VieweeColorMain,
            textAlign = TextAlign.Center
        )

        Icon(
            modifier = Modifier.size(134.dp),
            imageVector = ImageVector.vectorResource(R.drawable.ic_input_finish),
            contentDescription = "input finish",
            tint = Color(0xFF21C161)
        )

        Button(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .height(54.dp)
                .clip(RoundedCornerShape(10.dp)),
            onClick = onFinish,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = VieweeColorMain
            )
        ) {
            Text(
                text = stringResource(id = R.string.input_profile_finish_btn),
                fontFamily = noToSansKr,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun InputProfileFinishScreenPreview() {
    InputProfileFinishScreen {

    }
}