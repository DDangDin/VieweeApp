package com.capstone.vieweeapp.presentation.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.capstone.vieweeapp.ui.theme.VieweeColorHomeSearchBarBackground
import com.capstone.vieweeapp.ui.theme.VieweeColorText

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    textColor: Color,
    fontSize: TextUnit,
    leadingIcon: (@Composable () -> Unit)? = null,
    placeholderText: AnnotatedString
) {
    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .background(VieweeColorHomeSearchBarBackground),
        value = text,
        onValueChange = { onTextChanged(it) },
        singleLine = true,
        maxLines = 1,
        cursorBrush = SolidColor(VieweeColorText.copy(alpha = 0.7f)),
        textStyle = LocalTextStyle.current.copy(
            color = textColor,
            fontSize = fontSize
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(11.dp, alignment = Alignment.CenterHorizontally)
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f)) {
                    if (text.isEmpty()) Text(
                        text = placeholderText,
                        style = LocalTextStyle.current.copy(
                            color = VieweeColorText.copy(alpha = 0.7f),
                            fontSize = fontSize
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    innerTextField()
                }
            }
        }
    )
}