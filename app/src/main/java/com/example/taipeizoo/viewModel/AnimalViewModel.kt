package com.example.taipeizoo.viewModel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.taipeizoo.base.BaseViewModel
import com.example.taipeizoo.fragment.adapter.AnimalPagingSource
import com.example.taipeizoo.network.TaipeiZooRepository
import com.example.taipeizoo.network.model.Animal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AnimalViewModel @Inject constructor(
    private val repository: TaipeiZooRepository
): BaseViewModel() {
    val animalsPagingFlow: Flow<PagingData<Animal>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { AnimalPagingSource(repository, 20) }
    ).flow.cachedIn(viewModelScope)

    val allAnimalsPagingFlow: Flow<PagingData<Animal>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { AnimalPagingSource(repository, 500) }
    ).flow.cachedIn(viewModelScope)
}