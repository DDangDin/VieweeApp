    fun updateFacialExpression(emotion: String, emotionValue: Float) {
        // facialExpressionRecognition
        // emotion -> emotion_s
        // emotion_value -> emotion_v
        Log.d(TAG, emotion)
        viewModelScope.launch {
            if (isFacialExpressionUpdatePossible.value) {
                // _facialExpressionHashMap 초기화가 된 상태에서 진행되어야 함
                // 초기화 할 때 까지 기다림
                val count = _facialExpressionHashMap[emotion]
                _facialExpressionHashMap[emotion] = count!! + 1
            }
        }
    }