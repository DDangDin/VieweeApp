package com.capstone.vieweeapp.presentation.view.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.ui.theme.VieweeColorHomeSearchBarLeadingIcon
import com.capstone.vieweeapp.ui.theme.VieweeColorHomeText

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit
) {

    var isSearchBarFocused by rememberSaveable {
        mutableStateOf(false)
    }

    CustomTextField(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .height(40.dp)
            .border(
                1.dp,
                if (isSearchBarFocused) {
                    VieweeColorHomeText.copy(alpha = 0.5f)
                } else {
                    Color.Transparent
                },
                RoundedCornerShape(20.dp)
            )
            .onFocusChanged {
                isSearchBarFocused = it.isFocused
            },
        text = text,
        onTextChanged = { onTextChanged(it) },
        textColor = VieweeColorHomeText.copy(alpha = 0.7f),
        fontSize = 17.sp,
        placeholderText = "",
        leadingIcon = {
            Icon(
                modifier = Modifier.padding(start = 10.dp),
                imageVector = Icons.Default.Search,
                contentDescription = "leadingIcon",
                tint = VieweeColorHomeSearchBarLeadingIcon
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBar(
        text = "",
        onTextChanged = {}
    )
}