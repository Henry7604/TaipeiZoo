package com.example.taipeizoo.fragment.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.taipeizoo.network.NetworkResponse
import com.example.taipeizoo.network.TaipeiZooRepository
import com.example.taipeizoo.network.model.Plant

class PlantPagingSource (
    private val repository: TaipeiZooRepository,
    private val limit: Int
) : PagingSource<Int, Plant>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Plant> {
        return try {
            val offset = params.key ?: 0
            when (val response = repository.getPlantList(limit, offset)) {
                is NetworkResponse.Success -> {
                    val animalList = response.body.result?.results ?: emptyList()
                    val count = response.body.result!!.count
                    LoadResult.Page(
                        data = animalList,
                        prevKey = null,
                        nextKey = if (offset + animalList.size >= count) {
                            null
                        } else {
                            offset + animalList.size
                        }
                    )
                }
                is NetworkResponse.ApiError -> {
                    LoadResult.Error(Throwable(response.errorMsg))
                }
                is NetworkResponse.NetworkError -> {
                    LoadResult.Error(response.error)
                }
                is NetworkResponse.UnknownError -> {
                    LoadResult.Error(response.error ?: Throwable("Unknown error"))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Plant>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition)
        return anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }
}
