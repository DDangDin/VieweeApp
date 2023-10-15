package com.capstone.vieweeapp.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.viewee.data.source.network.clova_api.dto.Confidence
import com.capstone.viewee.data.source.network.clova_api.dto.toTextSentiment
import com.capstone.vieweeapp.data.source.local.entity.Emotion
import com.capstone.vieweeapp.data.source.local.entity.Feedbacks
import com.capstone.vieweeapp.data.source.local.entity.InterviewResult
import com.capstone.vieweeapp.data.source.local.entity.TextSentiment
import com.capstone.vieweeapp.data.source.local.entity.toCreateQuestionReqDto
import com.capstone.vieweeapp.data.source.remote.clova.dto.TextSentimentReqDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.request.FeedbackReqDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.request.ReFeedbackReqDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.response.makeQuestionList
import com.capstone.vieweeapp.data.source.remote.viewee.dto.response.toFeedbacks
import com.capstone.vieweeapp.domain.repository.ClovaSentimentRepository
import com.capstone.vieweeapp.domain.repository.InterviewResultRepository
import com.capstone.vieweeapp.domain.repository.ResumeRepository
import com.capstone.vieweeapp.domain.repository.VieweeRepository
import com.capstone.vieweeapp.presentation.event.RealInterviewUiEvent
import com.capstone.vieweeapp.presentation.event.SelectResumeUiEvent
import com.capstone.vieweeapp.presentation.state.FeedbackState
import com.capstone.vieweeapp.presentation.state.InterviewTurnState
import com.capstone.vieweeapp.presentation.state.QuestionsState
import com.capstone.vieweeapp.presentation.state.ReInterviewState
import com.capstone.vieweeapp.presentation.state.ResumeState
import com.capstone.vieweeapp.utils.CalculateDate
import com.capstone.vieweeapp.utils.Constants
import com.capstone.vieweeapp.utils.FacialEmotionList
import com.capstone.vieweeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterviewViewModel @Inject constructor(
    private val resumeRepository: ResumeRepository,
    private val interviewResultRepository: InterviewResultRepository,
    private val vieweeRepository: VieweeRepository,
    private val clovaSentimentRepository: ClovaSentimentRepository
) : ViewModel() {
    private val TAG = "InterviewViewModel_Log"


    // resume 선택 시 아래 변수에 할당 됨
    private var selectedResume = Constants.RESUME_DUMMY_DATA

    private val _resumeState = MutableStateFlow(ResumeState())
    val resumeState = _resumeState.asStateFlow()

    private val _questionsState = MutableStateFlow(QuestionsState())
    val questionsState = _questionsState.asStateFlow()

    private val _interviewerTurnState = MutableStateFlow(InterviewTurnState())
    val interviewerTurnState = _interviewerTurnState.asStateFlow()

    private val _interviewTime = MutableStateFlow(0)
    val interviewTime = _interviewTime.asStateFlow()

    var interviewTimeJob: Job? = null

    private val _finishState = MutableStateFlow(false)
    val finishState = _finishState.asStateFlow()

    // Data
    private val _emotionListUpdatePossible = MutableStateFlow(false)
    private val emotionListUpdatePossible = _emotionListUpdatePossible.asStateFlow()
    private var emotionHashMap = hashMapOf<String, Int>()

    var answerList = arrayListOf<String>()
        private set
    var emotionList = arrayListOf<Emotion>()
        private set
    var textSentimentList = arrayListOf<TextSentiment>()
        private set

    // Permissions
    private val _shouldShowCamera = mutableStateOf(false)
    val shouldShowCamera: State<Boolean> = _shouldShowCamera

    private val _shouldRecordAudio = mutableStateOf(false)
    val shouldRecordAudio: State<Boolean> = _shouldRecordAudio

    private val _hasPermissionsForInterview = mutableStateOf(false)
    val hasPermissionsForInterview: State<Boolean> = _hasPermissionsForInterview

    // Feedback
    private val _feedbackState = MutableStateFlow(FeedbackState())
    val feedbackState = _feedbackState.asStateFlow()


    init {
        emotionListInitialize()
    }

    fun getResumes() {
        viewModelScope.launch {
            _resumeState.update { it.copy(loading = true) }
            val resumes = resumeRepository.getResumes()
            _resumeState.update {
                it.copy(
                    resumes = resumes,
                    loading = false
                )
            }
        }
    }

    fun selectResumeUiEvent(uiEvent: SelectResumeUiEvent) {
        when (uiEvent) {
            is SelectResumeUiEvent.SelectedResume -> {
                selectedResume = uiEvent.resume
            }

            is SelectResumeUiEvent.DeleteResume -> {
                viewModelScope.launch {
                    resumeRepository.deleteResume(uiEvent.resume)
                    getResumes()
                }
            }
        }
    }

    // Permissions Logic
    fun updatePermissions(permissionName: String, valid: Boolean) {
        when (permissionName) {
            android.Manifest.permission.CAMERA -> _shouldShowCamera.value = valid
            android.Manifest.permission.RECORD_AUDIO -> _shouldRecordAudio.value = valid
        }

        if (_shouldShowCamera.value && _shouldRecordAudio.value)
            _hasPermissionsForInterview.value = true
    }

    private fun emotionListInitialize() {
        viewModelScope.launch {
            _emotionListUpdatePossible.emit(false)
            FacialEmotionList.emotions.forEach { emotion ->
                emotionHashMap[emotion] = 0
            }
            _emotionListUpdatePossible.emit(true)
        }
    }

    fun updateEmotionList(emotion: String, emotionValue: Float) {
        // facialExpressionRecognition
        // emotion -> emotion_s
        // emotion_value -> emotion_v
//        Log.d(TAG, emotion)
        viewModelScope.launch {
            if (emotionListUpdatePossible.value) {
                // _facialExpressionHashMap 초기화가 된 상태에서 진행되어야 함
                // 초기화 할 때 까지 기다림
                val count = emotionHashMap[emotion]
                emotionHashMap[emotion] = count!! + 1
            }
        }
    }

    fun createQuestions() {
        viewModelScope.launch {
            vieweeRepository.getQuestions(selectedResume.toCreateQuestionReqDto())
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _questionsState.update {
                                it.copy(
                                    questions = result.data?.makeQuestionList() ?: emptyList(),
                                    loading = false
                                )
                            }
                        }

                        is Resource.Loading -> {
                            _questionsState.update { it.copy(loading = true) }
                        }

                        is Resource.Error -> {
                            _questionsState.update {
                                it.copy(
                                    error = result.message ?: Constants.COMMON_ERROR_MESSAGE,
                                    loading = false
                                )
                            }
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    private fun saveEachInterviewTurn(
        answer: String,
    ) {
        // 텍스트 감정 분석 저장
        // 임시 (API 요청 필요)
//        textSentimentList.add(
//            TextSentiment(
//                sentiment = "Neutral",
//                confidence = Confidence(0.0, 0.0, 0.0)
//            )
//        )
        val emptyOrErrorTextSentiment = TextSentiment(
            sentiment = "Neutral",
            confidence = Confidence(0.0, 0.0, 0.0)
        )
        viewModelScope.launch {
            clovaSentimentRepository.getClovaSentimentResult(TextSentimentReqDto(answer))
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            textSentimentList.add(
                                result.data?.toTextSentiment() ?: emptyOrErrorTextSentiment
                            )
                        }

                        is Resource.Loading -> {}
                        is Resource.Error -> {
                            textSentimentList.add(emptyOrErrorTextSentiment)
                        }
                    }
                }.launchIn(viewModelScope)
        }

        // 답변 저장
        answerList.add(answer)

        // 얼굴 표정 분석 저장
        val emotion = Emotion(
            surprise = emotionHashMap[FacialEmotionList.emotions[0]]!!,
            fear = emotionHashMap[FacialEmotionList.emotions[1]]!!,
            angry = emotionHashMap[FacialEmotionList.emotions[2]]!!,
            neutral = emotionHashMap[FacialEmotionList.emotions[3]]!!,
            sad = emotionHashMap[FacialEmotionList.emotions[4]]!!,
            disgust = emotionHashMap[FacialEmotionList.emotions[5]]!!,
            happy = emotionHashMap[FacialEmotionList.emotions[6]]!!
        )
        emotionList.add(emotion)

        Log.d("InterviewViewModel_Log", "$emotionList")
    }

    fun realInterviewUiEvent(uiEvent: RealInterviewUiEvent) {
        when (uiEvent) {
            RealInterviewUiEvent.ChangeInterviewerTurn -> {
                _interviewerTurnState.update {
                    it.copy(
                        isInterviewerTurn = !interviewerTurnState.value.isInterviewerTurn
                    )
                }
            }

            RealInterviewUiEvent.StartInterview -> {
                _interviewTime.update { 0 }
                _interviewerTurnState.update {
                    it.copy(
                        isInterviewerTurn = true
                    )
                }
                startCount(true)
            }

            RealInterviewUiEvent.FinishInterview -> {
                // 카운트 종료
                startCount(false)
                // 모든 답변 분석 (서버요청)
                interviewAnalyze()
                _finishState.update { true }
            }

            is RealInterviewUiEvent.NextQuestion -> {
                Log.d("InterviewViewModel_Log", "$answerList")
                saveEachInterviewTurn(answer = uiEvent.answer)
                emotionListInitialize()
                _interviewerTurnState.update {
                    it.copy(
                        isInterviewerTurn = !interviewerTurnState.value.isInterviewerTurn,
                        turnIndex = interviewerTurnState.value.turnIndex + 1
                    )
                }
            }

            RealInterviewUiEvent.StartFeedback -> {
                viewModelScope.launch {
                    var answerRequest = ""

                    for (idx in answerList.indices) {
                        answerRequest += "${idx + 1}. ${answerList[idx]}"
//                        answerRequest += "${idx + 1}. ${answerList[idx]}, (" +
//                                "질문에 대한 답변 분석 데이터 결과: " +
//                                "표정 분석 결과(${feedbackState.value.emotionList[idx]})," +
//                                "답변 텍스트 감정 분석 결과(${feedbackState.value.textSentimentList.response[idx]?.document?.sentiment ?: ""})" +
//                                "\n"
                    }

                    vieweeRepository.getAnswerFeedback(
                        FeedbackReqDto(
                            facialExpressionAnalysisData = "",
                            textSentimentAnalysisData = "",
                            allAnswersFeedback = "",
                            answerFeedback = answerRequest
                        )
                    ).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                _feedbackState.update {
                                    it.copy(
                                        feedbacks = result.data?.toFeedbacks() ?: Feedbacks(
                                            emptyList()
                                        ),
                                        loading = false,
                                        resumeForFeedback = selectedResume
                                    )
                                }
                            }

                            is Resource.Loading -> {
                                _feedbackState.update { it.copy(loading = true) }
                            }

                            is Resource.Error -> {
                                _feedbackState.update { it.copy(loading = false) }
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    fun interviewAnalyze() {

    }

    fun saveInterviewResult() {
        // 의문점->
        // 뷰모델에서 관찰한 상태 값을 가지고 뷰에서 데이터를 가지고 놀다가
        // 해당 데이터를 저장하거나 가공하고 싶을 때는
        // 뷰에서 데이터를 전달해주나?
        // 아니면 어차피 뷰모델이 가지고 있는 데이터니까 밑에 처럼 하나?
        viewModelScope.launch {
            interviewResultRepository.insertInterviewResult(
                InterviewResult(
                    feedbacks = feedbackState.value.feedbacks,
                    textSentiment = textSentimentList,
                    emotions = emotionList,
                    questions = questionsState.value.questions,
                    answers = answerList,
                    feedbackTotal = feedbackState.value.feedbacks.feedbacks.last(),
                    etc = "",
                    date = CalculateDate.today()
                )
            )
        }
    }

    private fun startCount(toggle: Boolean) {
        if (toggle) {
            interviewTimeJob = viewModelScope.launch {
                while (toggle) {
                    delay(1000L)
                    _interviewTime.value += 1
                    Log.d("${TAG}_CountUp", "count!!!")
                }
            }
        } else {
            interviewTimeJob?.cancel()
        }
    }
}