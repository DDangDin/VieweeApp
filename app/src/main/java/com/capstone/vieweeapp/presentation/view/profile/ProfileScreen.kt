package com.capstone.vieweeapp.presentation.view.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.presentation.view.home.CustomTextField
import com.capstone.vieweeapp.ui.theme.VieweeColorHomeSearchBarLeadingIcon
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.ui.theme.VieweeColorText

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    saveName: () -> Unit
) {

    var isSearchBarFocused by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(100.dp)
                .padding(horizontal = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
        ) {
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
                fontSize = 17.sp,
                placeholderText = AnnotatedString(""),
                leadingIcon = {
                    Icon(
                        modifier = Modifier.padding(start = 10.dp),
                        imageVector = Icons.Default.Settings,
                        contentDescription = "leadingIcon",
                        tint = VieweeColorHomeSearchBarLeadingIcon
                    )
                }
            )

            OutlinedButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VieweeColorMain,
                    contentColor = Color.White
                ),
                onClick = { saveName() }
            ) {
                Text(text = "임시 이름 변경")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        modifier = Modifier.fillMaxWidth(),
        text = "",
        onTextChanged = {},
        saveName = {}
    )
}