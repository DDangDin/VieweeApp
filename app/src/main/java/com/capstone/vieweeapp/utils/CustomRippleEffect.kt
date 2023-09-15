package com.capstone.vieweeapp.utils

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

object CustomRippleEffect {

    object NoRippleTheme : RippleTheme {
        @Composable
        override fun defaultColor() = Color.Unspecified

        @Composable
        override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
    }

    @OptIn(ExperimentalFoundationApi::class)
    fun Modifier.clickableWithoutRipple(
        interactionSource: MutableInteractionSource,
        onClick: () -> Unit,
        onLongClick: () -> Unit = {}
    ) = composed(
        factory = {
            this.then(
                Modifier.combinedClickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { onClick() },
                    onLongClick = { onLongClick() }
                )
            )
        }
    )


    fun Modifier.shimmerEffect(isLoading: Boolean): Modifier = composed {
        if (isLoading) {
            var size by remember {
                mutableStateOf(IntSize.Zero)
            }
            val transition = rememberInfiniteTransition()
            val startOffsetX by transition.animateFloat(
                initialValue = -2 * size.width.toFloat(),
                targetValue = 2 * size.width.toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(1000)
                )
            )

            background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFCFC8C8),
                        Color(0xFFB9B3B3),
                        Color(0xFFCFC8C8),
//                        Color(0xFFB8B5B5),
//                        Color(0xFF8F8B8B),
//                        Color(0xFFB8B5B5),
                    ),
                    start = Offset(startOffsetX, 0f),
                    end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
                )
            )
                .onGloballyPositioned {
                    size = it.size
                }
        } else {
            Modifier
        }
    }
}
