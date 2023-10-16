package com.capstone.vieweeapp.presentation.viewmodel

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.capstone.vieweeapp.presentation.state.TtsState
import com.capstone.vieweeapp.presentation.state.VoiceToTextParserState
import com.capstone.vieweeapp.utils.Constants
import com.capstone.vieweeapp.utils.VieweeErrorCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class TextVoiceSpeechViewModel @Inject constructor(
    private val application: Application,
) : ViewModel(), TextToSpeech.OnInitListener, RecognitionListener {

    init {
        ttsInit()
    }

    private lateinit var tts: TextToSpeech

    private val _ttsState = mutableStateOf(TtsState())
    val ttsState: State<TtsState> = _ttsState

    private val _voiceToTextState = MutableStateFlow(VoiceToTextParserState())
    val voiceToTextState = _voiceToTextState.asStateFlow()

    private val _finishState = mutableStateOf(false)
    val finishState: State<Boolean> = _finishState

    val recognizer = SpeechRecognizer.createSpeechRecognizer(application)


    fun startListening(languageCode: String) {
        _voiceToTextState.update { VoiceToTextParserState() }

        if (!SpeechRecognizer.isRecognitionAvailable(application)) {
            _voiceToTextState.update {
                it.copy(
                    error = "Speech recognition is not available"
                )
            }
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM,
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageCode)
//            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 10000L)
//            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 10000L)
//            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 10000L)
        }

        recognizer.setRecognitionListener(this)
        recognizer.startListening(intent)

        _voiceToTextState.update {
            it.copy(
                isSpeaking = true
            )
        }
    }

    fun stopListening() {
        _voiceToTextState.update {
            it.copy(
                isSpeaking = false
            )
        }

        recognizer.stopListening()
    }

    // VoiceToText Settings (start)
    override fun onReadyForSpeech(p0: Bundle?) {
        _voiceToTextState.update {
            it.copy(
                error = null
            )
        }
    }

    override fun onBeginningOfSpeech() = Unit

    override fun onRmsChanged(p0: Float) = Unit

    override fun onBufferReceived(p0: ByteArray?) = Unit

    override fun onEndOfSpeech() {
        _voiceToTextState.update {
            it.copy(
                isSpeaking = false
            )
        }
    }

    override fun onError(error: Int) {
        if (error == SpeechRecognizer.ERROR_CLIENT) {
            return
        }
        _voiceToTextState.update {
            it.copy(
                error = "Error: $error"
            )
        }
    }

    override fun onResults(results: Bundle?) {
        results
            ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)
            ?.let { text ->
                _voiceToTextState.update {
                    it.copy(
                        spokenText = text
                    )
                }
            }
    }

    override fun onPartialResults(p0: Bundle?) = Unit

    override fun onEvent(p0: Int, p1: Bundle?) = Unit
    // VoiceToText Settings (end)



    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.KOREAN)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language not supported!")
            }
        }
    }

    fun speakInterviewFinish(questionIndexForFinish: Int, isInterviewerTurn: Boolean) {
        if (questionIndexForFinish == 5 && !isInterviewerTurn) {
            if (!tts.isSpeaking) {
                tts.speak(Constants.INTERVIEW_FINISH_MESSAGE, TextToSpeech.QUEUE_FLUSH, null, "")

                tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(p0: String?) {
                        _finishState.value = false
                    }

                    override fun onDone(p0: String?) {
                        _finishState.value = true
                    }

                    override fun onError(p0: String?) {
                        _ttsState.value = ttsState.value.copy(
                            error = VieweeErrorCode.TTS_ERR
                        )
                    }
                })
            }
        }
    }

    fun speak(question: String) {
        // 가상 면접관이 질문 읽어 주는 부분
        if (question.isNotEmpty()) {
            if (!tts.isSpeaking) {
                tts.speak(question, TextToSpeech.QUEUE_FLUSH, null, "")

                tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(p0: String?) {
                        _ttsState.value = ttsState.value.copy(
                            isSpeak = true
                        )
                        Log.d("TTS_State_Log", "TTS: onStart")
                    }

                    override fun onDone(p0: String?) {
                        _ttsState.value = ttsState.value.copy(
                            isSpeak = false
                        )
                        Log.d("TTS_State_Log", "TTS: onDone")
                    }

                    override fun onError(p0: String?) {
                        _ttsState.value = ttsState.value.copy(
                            error = VieweeErrorCode.TTS_ERR
                        )
                        Log.d("TTS_State_Log", "TTS: onError")
                    }
                })
            }
        }
    }

    fun ttsInit() {
//        val a: MutableSet<String> = HashSet()
//        a.add("male") //here you can give male if you want to select male voice.
//        val voice = Voice("ko-kr-x-sfg#male_2-local", Locale("ko", "KR"), 400, 200, true, a)

        // tts
        // com.google.android.tts -> 구글 TTS 엔진
        // com.samsung.SMT -> 삼성 TTS 엔진 (이게 기본 값)
        tts = TextToSpeech(application, this, )
        tts.setSpeechRate(1f)
//        val voices = tts.voices
//        val voiceList: List<Voice> = ArrayList(voices)
//        Log.d("voice_log", "voiceList: ${voiceList ?: "null"}")
        Log.d("voice_log", "tts_info: ${tts.voices}, tts_info: ${tts.engines}")
    }

    override fun onCleared() {
        // tts
        if (tts != null) {
            tts.stop()
            tts.shutdown()
        }
    }

    fun onDestroy() {
        // tts
        if (tts != null) {
            tts.stop()
            tts.shutdown()
        }
    }
}
