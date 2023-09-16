package com.capstone.vieweeapp.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel: ViewModel() {

    private val _searchText = mutableStateOf("")
    val searchText: State<String> = _searchText

    fun onSearchTextChanged(value: String) {
        _searchText.value = value
    }
}