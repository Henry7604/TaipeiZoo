package com.example.taipeizoo.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel : ViewModel() {
    protected val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState
}
