package com.capstone.vieweeapp.presentation.view.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.utils.Constants
import com.capstone.vieweeapp.utils.CustomRippleEffect.clickableWithoutRipple

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    @DrawableRes logo: Int,
    onMenuClick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = (Constants.HOME_PADDING_VALUE-5).dp,
                end = Constants.HOME_PADDING_VALUE.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            modifier = Modifier
                .width(120.dp)
                .height(32.dp),
            painter = painterResource(id = logo),
            contentDescription = "logo"
        )
        Icon(
            modifier = Modifier
                .width(32.dp)
                .height(18.dp)
                .clickableWithoutRipple(
                    onClick = onMenuClick,
                    interactionSource = MutableInteractionSource()
                ),
            imageVector = ImageVector.vectorResource(R.drawable.ic_menu),
            contentDescription = "menu",
            tint = Color(0xFF4A5155)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeTopBarPreview() {
    HomeTopBar(
        modifier = Modifier.fillMaxWidth(),
        logo = R.drawable.img_logo,
        onMenuClick = {}
    )
}