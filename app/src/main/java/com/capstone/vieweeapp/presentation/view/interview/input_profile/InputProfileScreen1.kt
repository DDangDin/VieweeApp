package com.capstone.vieweeapp.presentation.view.interview.input_profile

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.presentation.viewmodel.InputProfileViewModel
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.utils.CustomRippleEffect.clickableWithoutRipple


// 정보 입력 화면 1
@Composable
fun InputProfileScreen1(
    modifier: Modifier = Modifier,
    inputProfileViewModel: InputProfileViewModel,
    onNavigateNext: () -> Unit,
    onExit: () -> Unit
) {

    var isEmptyValue_1 by remember { mutableStateOf(false) }
    var isEmptyValue_2 by remember { mutableStateOf(false) }
    var isEmptyValue_3 by remember { mutableStateOf(false) }
    var isEmptyValue_4 by remember { mutableStateOf(false) }

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
        CustomTitleText(
            modifier = Modifier.padding(top = 130.dp),
            text = stringResource(id = R.string.input_profile_title),
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(vertical = 7.dp, horizontal = 30.dp),
                verticalArrangement = Arrangement.spacedBy(
                    10.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                DataInputCard(
                    dataName = stringResource(id = R.string.input_profile_screen_1_name),
                    dataValue = inputProfileViewModel.inputName.value,
                    onTextChanged = inputProfileViewModel::inputNameChanged,
                    keyboardType = KeyboardType.Text,
                    isEmptyValue = isEmptyValue_1,
                )
                DataInputCard(
                    dataName = stringResource(id = R.string.input_profile_screen_1_birth),
                    dataValue = inputProfileViewModel.inputBirthdate.value,
                    onTextChanged = inputProfileViewModel::inputBirthdateChanged,
                    keyboardType = KeyboardType.Number,
                    isEmptyValue = isEmptyValue_2,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.input_profile_screen_1_birth_placeholder),
                            color = Color.Gray.copy(.5f)
                        )
                    }
                )
                DataInputCard(
                    dataName = stringResource(id = R.string.input_profile_screen_1_education),
                    dataValue = inputProfileViewModel.inputEducation.value,
                    onTextChanged = inputProfileViewModel::inputEducationChanged,
                    keyboardType = KeyboardType.Text,
                    isEmptyValue = isEmptyValue_3,
                )
                DataInputCard(
                    dataName = stringResource(id = R.string.input_profile_screen_1_career),
                    dataValue = inputProfileViewModel.inputCareer.value,
                    onTextChanged = inputProfileViewModel::inputCareerChanged,
                    keyboardType = KeyboardType.Text,
                    isEmptyValue = isEmptyValue_4,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.input_profile_screen_1_career_placeholder),
                            color = Color.Gray.copy(.5f)
                        )
                    }
                )
            }
        }
        NextButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 30.dp, end = 30.dp, bottom = 30.dp),
            onClick = {
                isEmptyValue_1 = inputProfileViewModel.inputName.value.isEmpty()
                isEmptyValue_2 = inputProfileViewModel.inputBirthdate.value.isEmpty()
                isEmptyValue_3 = inputProfileViewModel.inputEducation.value.isEmpty()
                isEmptyValue_4 = inputProfileViewModel.inputCareer.value.isEmpty()

                if (
                    inputProfileViewModel.inputName.value.isNotEmpty() &&
                    inputProfileViewModel.inputBirthdate.value.isNotEmpty() &&
                    inputProfileViewModel.inputEducation.value.isNotEmpty() &&
                    inputProfileViewModel.inputCareer.value.isNotEmpty()
                ) {
                    onNavigateNext()
                }
            }
        )
    }
//        QuitButton(onClick = {})
}