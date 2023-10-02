package com.capstone.vieweeapp.presentation.view.interview.real_interview

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.presentation.event.RealInterviewUiEvent
import com.capstone.vieweeapp.presentation.state.InterviewTurnState
import com.capstone.vieweeapp.presentation.state.QuestionsState
import com.capstone.vieweeapp.presentation.viewmodel.TextVoiceSpeechViewModel
import com.capstone.vieweeapp.ui.theme.VieweeColorMain
import com.capstone.vieweeapp.utils.Constants
import com.capstone.vieweeapp.utils.opencv.makeGrayMat
import com.capstone.vieweeapp.utils.opencv.makeRgbaMat
import kotlinx.coroutines.delay
import org.opencv.core.Mat

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@Composable
fun RealInterviewScreen(
    modifier: Modifier = Modifier,
    interviewerTurnState: InterviewTurnState,
    questionsState: QuestionsState,
    interviewFinishState: Boolean,
    time: String,
    onStop: () -> Unit,
    uiEvent: (RealInterviewUiEvent) -> Unit,
    recognizeImage: (Mat, Mat, Int) -> Unit,
    textVoiceSpeechViewModel: TextVoiceSpeechViewModel,
    onNavigateFeedbackScreen: () -> Unit
) {
    // 답변 차례일 때 다음 질문으로 넘어가기 위한 조건
    // 1. tts 상태가 아니여야 함 즉, 말하고 있는 상태가 아니여야 함
    // (ttsState에서 isSpeak 값 체크)
    // 2. interviewerTurn이 아니여야 함
    // (interviewerTurnState에서 isInterviewTurn 값 체크)

    val context = LocalContext.current

    val ttsState = textVoiceSpeechViewModel.ttsState.value
    val voiceToTextState = textVoiceSpeechViewModel.voiceToTextState.collectAsState()
    val finishState = textVoiceSpeechViewModel.finishState.value

    val isInterviewerTurn = interviewerTurnState.isInterviewerTurn

    LaunchedEffect(interviewerTurnState) {
        delay(1000L)
        if (isInterviewerTurn) {
            if (interviewerTurnState.turnIndex >= questionsState.questions.size) {
                textVoiceSpeechViewModel.speak(Constants.INTERVIEW_FINISH_MESSAGE)
            } else {
                textVoiceSpeechViewModel.speak(questionsState.questions[interviewerTurnState.turnIndex])
            }
            // 면접자로 전환
//            uiEvent(RealInterviewUiEvent.ChangeInterviewerTurn)
        }
    }

    // 나중에 바꿀 코드
    LaunchedEffect(ttsState.isSpeak) {
        if (!ttsState.isSpeak && interviewerTurnState.isInterviewerTurn) {
            // 면접자로 전환
            uiEvent(RealInterviewUiEvent.ChangeInterviewerTurn)
        }
        if (interviewerTurnState.turnIndex >= questionsState.questions.size && !ttsState.isSpeak) {
            uiEvent(RealInterviewUiEvent.FinishInterview)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VieweeColorMain.copy(alpha = 0.1f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        RealInterviewScreenTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.07f),
            interviewerTurn = isInterviewerTurn,
            index = interviewerTurnState.turnIndex,
            isFinish = questionsState.questions.size == interviewerTurnState.turnIndex,
        )
        LaunchedEffect(interviewerTurnState) {
            Log.d("InterviewScreenTopBar", "questionSize: ${questionsState.questions.size}" +
                    "turnIndex: ${interviewerTurnState.turnIndex - 1}")
        }
        Image(
            modifier = Modifier
                .weight(1f)
                .border(
                    color = if (isInterviewerTurn) VieweeColorMain else Color.Transparent,
                    width = 4.5.dp,
                    shape = RoundedCornerShape(15.dp)
                ),
            painter = painterResource(id = R.drawable.img_interviewer),
            contentDescription = "interviewer"
        )
        Spacer(modifier = Modifier.height(10.dp))
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .weight(1f)
//                .border(
//                    color = if (!isInterviewerTurn) VieweeColorMain else Color.Transparent,
//                    width = 4.5.dp,
//                    shape = RoundedCornerShape(15.dp)
//                ),
//        ) {
//            Icon(
//                modifier = Modifier
//                    .align(Alignment.Center)
//                    .clip(CircleShape)
//                    .size(50.dp)
//                    .background(VieweeColorMain)
//                    .clickable {
//                        if (interviewerTurnState.turnIndex < questionsState.questions.size) {
//                            textVoiceSpeechViewModel.startListening(Constants.VOICE_TO_TEXT_LANGUAGE)
//                        }
//                    },
//                imageVector = Icons.Filled.Mic,
//                contentDescription = "말하기",
//                tint = Color.White
//            )
//        }
        RealInterviewApplicantView(
            modifier = Modifier
                .weight(1f)
                .border(
                    color = if (!isInterviewerTurn) VieweeColorMain else Color.Transparent,
                    width = 4.5.dp,
                    shape = RoundedCornerShape(15.dp)
                )
                .clip(RoundedCornerShape(15.dp)),
            onImageAnalysis = { imageProxy ->
                val mRgba = makeRgbaMat(imageProxy.image!!)
                val mGray = makeGrayMat(imageProxy.image!!)
                val rotationDegrees = imageProxy.imageInfo.rotationDegrees

                recognizeImage(mRgba!!, mGray!!, rotationDegrees)
            },
            startListening = {
                if (interviewerTurnState.turnIndex < questionsState.questions.size &&
                    !ttsState.isSpeak &&
                    !interviewerTurnState.isInterviewerTurn
                ) {
                    textVoiceSpeechViewModel.startListening(Constants.VOICE_TO_TEXT_LANGUAGE)
                }
            }
        )
        RealInterviewScreenBottomBar(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f),
            time = time,
            onStop = onStop,
            onNextTurn = {
                if (!ttsState.isSpeak && !interviewerTurnState.isInterviewerTurn) {
                    uiEvent(
                        RealInterviewUiEvent.NextQuestion(
                            answer = voiceToTextState.value.spokenText,
                        )
                    )
//                    if (voiceToTextState.value.spokenText.isEmpty()) {
//                        Toast.makeText(context, Constants.SPOKEN_ANSWER_TEXT_EMPTY, Toast.LENGTH_SHORT).show()
//                    } else {
//                        uiEvent(
//                            RealInterviewUiEvent.NextQuestion(
//                                answer = voiceToTextState.value.spokenText,
//                            )
//                        )
//                    }
                }
            },
            interviewFinishState = interviewFinishState,
            startFeedback = {
                uiEvent(RealInterviewUiEvent.StartFeedback)
                onNavigateFeedbackScreen()
            }
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun RealInterviewScreenPreview() {
//    RealInterviewScreen(
//        interviewerTurn = false,
//        time = "00:00",
//        onStop = { /*TODO*/ },
//        uiEvent = {},
//        recognizeImage = { a, b, c -> }
//    )
//}

