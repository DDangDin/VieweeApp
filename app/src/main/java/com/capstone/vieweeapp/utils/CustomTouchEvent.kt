package com.capstone.vieweeapp.utils

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput

object CustomTouchEvent {

    // 전체화면의 터치 이벤트 감지
    // Ex) TextField 를 감싸고 있는 상위(부모) 컴포저블에 적용
    fun Modifier.addFocusCleaner(focusManager: FocusManager, doOnClear: () -> Unit = {}): Modifier {
        return this.pointerInput(Unit) {
            detectTapGestures(onTap = {
                doOnClear()
                focusManager.clearFocus()
            })
        }
    }
}