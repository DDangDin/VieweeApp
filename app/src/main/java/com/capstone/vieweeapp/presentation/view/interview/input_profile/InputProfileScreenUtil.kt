package com.capstone.vieweeapp.presentation.view.interview.input_profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.Placeholder
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.ui.theme.VieweeColorBackgroundGrey
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.ui.theme.VieweeColorOrange

// 다음 버튼
@Composable
fun NextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        onClick = onClick,
        colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
            containerColor = VieweeColorMain
        ),
        border = BorderStroke(
            width = 0.dp,
            color = Color.Transparent,
        ),
        shape = RoundedCornerShape(10.dp)
//        colors = ButtonDefaults.buttonColors(
//            backgroundColor = VieweeColorMain
//        )
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_nextbtn_text),
            contentDescription = "next",
            tint = Color.White
        )
    }
}

// 종료 버튼
@Composable
fun QuitButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Text(
        modifier = modifier.clickable { onClick() },
        text = "종료하기",
        fontSize = 13.sp,
        color = VieweeColorOrange,
        textDecoration = TextDecoration.Underline
    )
}

// 정보 입력 카드
@Composable
fun DataInputCard(
    modifier: Modifier = Modifier,
    dataName: String,
    dataValue: String,
    onTextChanged: (String) -> Unit,
    keyboardType: KeyboardType,
    isEmptyValue: Boolean,
    placeholder: (@Composable () -> Unit)? = null,
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(7.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dataName,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left,
            fontStyle = FontStyle.Normal,
            fontSize = 13.sp,
            color = Color.Gray,
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .border(
                    1.dp,
                    if (isEmptyValue) Color.Red else Color.Transparent,
                    RoundedCornerShape(10.dp)
                ),
            value = dataValue,
            onValueChange = { onTextChanged(it) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = VieweeColorBackgroundGrey,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = VieweeColorMain
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            placeholder = {
                if (placeholder != null) placeholder()
            }
        )
    }
}

@Composable
fun CustomTitleText(
    modifier: Modifier = Modifier,
    text: String = "자신의 정보를 입력해주세요.",
    fontSize: TextUnit = 20.sp
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = text,
        textAlign = TextAlign.Center,
        fontStyle = FontStyle.Normal,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
        color = VieweeColorMain
    )
}

@Preview(showBackground = true)
@Composable
fun CustomTitleTextPreview() {
    CustomTitleText()
}

@Preview
@Composable
fun NextButtonPreview() {
    NextButton {

    }
}