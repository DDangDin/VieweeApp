package com.capstone.vieweeapp.presentation.view.feedback

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.vieweeapp.presentation.state.ReInterviewState
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.ui.theme.VieweeColorText
import com.capstone.vieweeapp.ui.theme.noToSansKr
import com.capstone.vieweeapp.utils.CustomRippleEffect.clickableWithoutRipple

@Preview(showBackground = true)
@Composable
fun EachReInterviewSectionPreview() {

    var reInterviewClick by remember { mutableStateOf(false) }

    EachReInterviewSection(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = VieweeColorMain.copy(alpha = 0.15f),
                shape = RoundedCornerShape(10.dp)
            )
            .clickableWithoutRipple(
                interactionSource = MutableInteractionSource(),
                onClick = { reInterviewClick = true }
            ),
        reInterviewClick = true,
        reInterviewState = ReInterviewState(),
        index = 0,
        onSubmit = {},
        onClose = {}
    )
}

@Composable
fun EachReInterviewSection(
    modifier: Modifier = Modifier,
    reInterviewClick: Boolean,
    reInterviewState: ReInterviewState,
    index: Int,
    onSubmit: (String) -> Unit,
    onClose: () -> Unit
) {
    // 임시
    val (text, onTextChanged) = remember { mutableStateOf("") }

    Box(modifier = modifier) {
        if (!reInterviewClick) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 12.dp),
                text = "질문${index + 1} 답변 수정 후 간단 피드백받기",
                fontFamily = noToSansKr,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 25.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    7.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier,
                        text = "수정한 답변:",
                        fontFamily = noToSansKr,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = VieweeColorText.copy(alpha = 0.7f),
                        textAlign = TextAlign.Start
                    )
                    Icon(
                        modifier = Modifier
                            .size(16.dp)
                            .clickableWithoutRipple(
                                interactionSource = MutableInteractionSource(),
                                onClick = onClose
                            ),
                        imageVector = Icons.Default.Close,
                        contentDescription = "close",
                        tint = VieweeColorText.copy(alpha = 0.7f),
                    )
                }
                OutlinedTextField(
                    value = text,
                    onValueChange = { onTextChanged(it) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = VieweeColorText.copy(alpha = 0.7f),
                        backgroundColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = VieweeColorText
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            color = Color.Transparent,
                            shape = RectangleShape,
                            width = 0.dp
                        )
                )
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    border = BorderStroke(0.dp, Color.Transparent),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VieweeColorMain.copy(alpha = 0.15f),
                        contentColor = Color.Transparent,
                    ),
                    onClick = {
                        // 연속 요청 할건지 고민
                        onSubmit(text)
                    }
                ) {
                    Text(
                        text = "답변 수정 후 간단 피드백 받기",
                        fontFamily = noToSansKr,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = VieweeColorMain.copy(alpha = 0.8f)
                    )
                }

                if (!reInterviewState.loadings && reInterviewState.reFeedbacks[index].feedbacks.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 18.dp),
//                        text = "수정답변에 대한 피드백: \n${reInterviewState.reFeedback[index].feedbacks}",
                        text = buildAnnotatedString {
                            append("수정답변에 대한 피드백:")
                            append("\n")
                            append("\n")
                            append(
                                AnnotatedString(
                                    text = reInterviewState.reFeedbacks[index].feedbacks,
                                    spanStyle = SpanStyle(
                                        fontWeight = FontWeight.Medium,
                                    ),
                                )
                            )
                        },
                        fontFamily = noToSansKr,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF5179BC),
                        textAlign = TextAlign.Start
                    )
                }

                if (reInterviewState.loadings) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(25.dp),
                        strokeWidth = 2.dp,
                        color = VieweeColorMain
                    )
                }
            }
        }
    }
}