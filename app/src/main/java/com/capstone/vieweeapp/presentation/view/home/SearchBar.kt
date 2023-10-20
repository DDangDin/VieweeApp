package com.capstone.vieweeapp.presentation.view.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.ui.theme.VieweeColorHomeSearchBarLeadingIcon
import com.capstone.vieweeapp.ui.theme.VieweeColorText
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    focusManager: FocusManager,
) {

    var isSearchBarFocused by rememberSaveable {
        mutableStateOf(false)
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    CustomTextField(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .height(40.dp)
            .border(
                1.dp,
                if (isSearchBarFocused) {
                    VieweeColorText.copy(alpha = 0.5f)
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
        textColor = VieweeColorText.copy(alpha = 0.7f),
        fontSize = 15.sp,
        placeholderText = buildAnnotatedString {
            append(
                AnnotatedString(
                    text = stringResource(id = R.string.home_text_searchbar_placeholder),
                    spanStyle = SpanStyle(
                        fontSize = 12.sp,
                        color = VieweeColorText.copy(alpha = 0.5f)
                    ),
                )
            )
        },
        leadingIcon = {
            Icon(
                modifier = Modifier.padding(start = 10.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                contentDescription = "leadingIcon",
                tint = VieweeColorHomeSearchBarLeadingIcon
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBar(
        text = "",
        onTextChanged = {},
        focusManager = LocalFocusManager.current
    )
}