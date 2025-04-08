package com.example.taipeizoo.viewModel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.taipeizoo.base.BaseViewModel
import com.example.taipeizoo.fragment.adapter.PlantPagingSource
import com.example.taipeizoo.network.TaipeiZooRepository
import com.example.taipeizoo.network.model.Plant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PlantViewModel @Inject constructor(
    private val repository: TaipeiZooRepository
): BaseViewModel() {
    val plantsPagingFlow: Flow<PagingData<Plant>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { PlantPagingSource(repository, 20) }
    ).flow.cachedIn(viewModelScope)

    val allPlantsPagingFlow: Flow<PagingData<Plant>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { PlantPagingSource(repository, 500) }
    ).flow.cachedIn(viewModelScope)
}