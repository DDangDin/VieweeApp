package com.capstone.vieweeapp.presentation.view.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.ui.theme.VieweeColorText
import com.capstone.vieweeapp.ui.theme.noToSansKr
import com.capstone.vieweeapp.utils.CustomTouchEvent.addFocusCleaner

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    name: String,
    onNameChanged: (String) -> Unit,
    saveName: (String) -> Unit
) {

    val context = LocalContext.current

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp, top = 45.dp, bottom = 10.dp)
            .addFocusCleaner(focusManager),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(35.dp, alignment = Alignment.Top)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.profile_title),
            fontFamily = noToSansKr,
            fontSize = 35.sp,
            fontWeight = FontWeight.Normal,
            color = VieweeColorText,
            textAlign = TextAlign.Start
        )
        ProfileSection(
            modifier = Modifier.padding(top = 40.dp),
            name = name,
            onNameChanged = { onNameChanged(it) },
            info = "개발자‧신입‧4년제 졸업",
            saveName = { saveName(it) },
            keyboardController = keyboardController,
            focusManager = focusManager
        )
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = VieweeColorText.copy(0.3f)
        )
        ResumeSection()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ProfileSection(
    modifier: Modifier = Modifier,
    name: String,
    onNameChanged: (String) -> Unit,
    info: String,
    saveName: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager
) {

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(5.dp, alignment = Alignment.CenterVertically),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileOutlinedTextField(
                modifier = Modifier.width(100.dp),
                name = name,
                onNameChanged = { onNameChanged(it) },
                keyboardController = keyboardController,
                focusManager = focusManager
            )
            IconButton(onClick = {
                keyboardController?.hide()
                focusManager.clearFocus()
                saveName(name)
            }) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Default.Settings,
                    contentDescription = "settings",
                    tint = Color(0xFF73737B).copy(alpha = 0.3f)
                )
            }
        }
        Text(
            text = info,
            fontFamily = noToSansKr,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = VieweeColorText.copy(alpha = 0.8f)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun ProfileOutlinedTextField(
    modifier: Modifier = Modifier,
    name: String,
    onNameChanged: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager
) {
    BasicTextField(
        modifier = modifier,
        value = name,
        onValueChange = {
            if (it.length <= 5) {
                onNameChanged(it)
            }
        },
        singleLine = true,
        cursorBrush = SolidColor(VieweeColorText.copy(alpha = 0.7f)),
        textStyle = TextStyle(
            fontFamily = noToSansKr,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            color = VieweeColorMain.copy(alpha = 0.8f),
            platformStyle = PlatformTextStyle(
                includeFontPadding = false
            )
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),
    )
}

@Composable
private fun ResumeSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterVertically)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.profile_title_resume),
            fontFamily = noToSansKr,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = VieweeColorText.copy(alpha = 0.7f),
            textAlign = TextAlign.Start,
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )
        // LazyColumn
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun ProfileSectionPreview() {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ProfileSection(
        name = "곽상진",
        info = "개발자‧신입‧4년제 졸업",
        saveName = {},
        keyboardController = keyboardController,
        focusManager = focusManager,
        onNameChanged = {}
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        modifier = Modifier.fillMaxWidth(),
        name = "",
        onNameChanged = {},
        saveName = {}
    )
}