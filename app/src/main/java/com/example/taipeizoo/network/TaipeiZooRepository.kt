package com.example.taipeizoo.network

import com.example.taipeizoo.network.model.Animal
import com.example.taipeizoo.network.model.BaseModel
import com.example.taipeizoo.network.model.ParkIntro
import com.example.taipeizoo.network.model.Plant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaipeiZooRepository @Inject constructor(
    private val apiInterface: APIInterface
) {
    suspend fun getParkIntroList(limit: Int, offset: Int): NetworkResponse<BaseModel<List<ParkIntro>>> {
        return apiInterface.getParkIntroList(limit, offset)
    }

    suspend fun getAnimalList(limit: Int, offset: Int): NetworkResponse<BaseModel<List<Animal>>> {
        return apiInterface.getAnimalList(limit, offset)
    }

    suspend fun getPlantList(limit: Int, offset: Int): NetworkResponse<BaseModel<List<Plant>>> {
        return apiInterface.getPlantList(limit, offset)
    }
}