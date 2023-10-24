package com.capstone.vieweeapp.presentation.view.interview.input_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.presentation.viewmodel.InputProfileViewModel
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.utils.CustomRippleEffect.clickableWithoutRipple


// 정보 입력 화면 2
@Composable
fun InputProfileScreen2(
    modifier: Modifier = Modifier,
    inputProfileViewModel: InputProfileViewModel,
    onNavigateNext: () -> Unit,
    onExit: () -> Unit
) {

    var isEmptyValue_1 by remember { mutableStateOf(false) }
    var isEmptyValue_2 by remember { mutableStateOf(false) }
    var isEmptyValue_3 by remember { mutableStateOf(false) }

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
                    dataName = stringResource(id = R.string.input_profile_screen_2_support_job),
                    dataValue = inputProfileViewModel.inputSupportJobs.value,
                    onTextChanged = inputProfileViewModel::inputSupportJobsChanged,
                    keyboardType = KeyboardType.Text,
                    isEmptyValue = isEmptyValue_1,
                )
                DataInputCard(
                    dataName = stringResource(id = R.string.input_profile_screen_2_certification),
                    dataValue = inputProfileViewModel.inputCertifications.value,
                    onTextChanged = inputProfileViewModel::inputCertificationsChanged,
                    keyboardType = KeyboardType.Text,
                    isEmptyValue = isEmptyValue_2,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.input_profile_screen_2_certification_placeholder),
                            color = Color.Gray.copy(.5f)
                        )
                    }
                )
                DataInputCard(
                    dataName = stringResource(id = R.string.input_profile_screen_2_skills),
                    dataValue = inputProfileViewModel.inputSkills.value,
                    onTextChanged = inputProfileViewModel::inputSkillsChanged,
                    keyboardType = KeyboardType.Text,
                    isEmptyValue = isEmptyValue_3,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.input_profile_screen_2_skills_placeholder),
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
                isEmptyValue_1 = inputProfileViewModel.inputSupportJobs.value.isEmpty()
                isEmptyValue_2 = inputProfileViewModel.inputCertifications.value.isEmpty()
                isEmptyValue_3 = inputProfileViewModel.inputSkills.value.isEmpty()

                if (
                    inputProfileViewModel.inputSupportJobs.value.isNotEmpty() &&
                    inputProfileViewModel.inputCertifications.value.isNotEmpty() &&
                    inputProfileViewModel.inputSkills.value.isNotEmpty()
                ) {
                    onNavigateNext()
                }
            }
        )
    }
//        QuitButton(onClick = {})
}
