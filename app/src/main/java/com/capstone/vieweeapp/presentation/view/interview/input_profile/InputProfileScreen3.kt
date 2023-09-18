package com.capstone.vieweeapp.presentation.view.interview.input_profile

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.presentation.view.interview.input_profile.CustomTitleText
import com.capstone.vieweeapp.presentation.view.interview.input_profile.NextButton
import com.capstone.vieweeapp.presentation.viewmodel.InputProfileViewModel
import com.capstone.vieweeapp.ui.theme.VieweeColorBackgroundGrey
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.utils.Constants
import com.capstone.vieweeapp.utils.CustomRippleEffect.clickableWithoutRipple

// 정보 입력 화면 3
@Composable
fun InputProfileScreen3(
    modifier: Modifier = Modifier,
    inputProfileViewModel: InputProfileViewModel,
    onNavigateNext: () -> Unit,
    onExit: () -> Unit
) {

    var isEmptyValue_1 by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        Icon(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(40.dp)
                .padding(top = 20.dp, end = 20.dp)
                .clickableWithoutRipple(
                    onClick = onExit,
                    interactionSource = MutableInteractionSource()
                ),
            imageVector = ImageVector.vectorResource(R.drawable.ic_exit),
            contentDescription = "exit",
            tint = VieweeColorMain
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTitleText(
                modifier = Modifier.padding(top = 40.dp),
                text = stringResource(id = R.string.input_profile_title_resume_text),
            )
            Column(
                modifier = Modifier.padding(vertical = 7.dp, horizontal = 30.dp),
                verticalArrangement = Arrangement.spacedBy(
                    10.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    value = inputProfileViewModel.inputResumeText.value,
                    onValueChange = {
                        if (inputProfileViewModel.inputResumeText.value.length <= Constants.RESUME_TEXT_MAX_LENGTH) {
                            inputProfileViewModel.inputResumeChanged(it)
                        }
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.input_profile_resume_text_placeholder),
                            color = Color.Gray.copy(.5f)
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = VieweeColorBackgroundGrey,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = VieweeColorMain
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                )
            }
            NextButton(
                modifier = Modifier.padding(horizontal = 30.dp),
                onClick = {
                    isEmptyValue_1 = inputProfileViewModel.inputResumeText.value.isEmpty()

                    if (
                        inputProfileViewModel.inputResumeText.value.isNotEmpty()
                    ) {
                        // 저장
                        inputProfileViewModel.insertResume()
                        onNavigateNext()
                    }
                }
            )
        }
    }
//        QuitButton(onClick = {})
}