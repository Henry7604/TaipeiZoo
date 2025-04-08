package com.example.taipeizoo.viewModel

import androidx.lifecycle.viewModelScope
import com.example.taipeizoo.base.BaseViewModel
import com.example.taipeizoo.network.NetworkResponse
import com.example.taipeizoo.network.TaipeiZooRepository
import com.example.taipeizoo.network.model.BaseModel
import com.example.taipeizoo.network.model.ParkIntro
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParkIntroViewModel @Inject constructor(
    private val repository: TaipeiZooRepository
): BaseViewModel() {
    private var _parkIntroListStateFlow = MutableStateFlow<NetworkResponse<BaseModel<List<ParkIntro>>>?>(null)
    var parkIntroListStateFlow: StateFlow<NetworkResponse<BaseModel<List<ParkIntro>>>?> = _parkIntroListStateFlow

    fun getParkIntroList() {
        _loadingState.value = true
        viewModelScope.launch {
            repository.getParkIntroList(20, 0).apply {
                _parkIntroListStateFlow.value = this
                _loadingState.value = false
            }
        }
    }
}