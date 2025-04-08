package com.example.taipeizoo.network

import com.example.taipeizoo.network.model.Animal
import com.example.taipeizoo.network.model.BaseModel
import com.example.taipeizoo.network.model.ParkIntro
import com.example.taipeizoo.network.model.Plant
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    @GET("api/v1/dataset/9683ba26-109e-4cb8-8f3d-03d1b349db9f?scope=resourceAquire")
    suspend fun getParkIntroList(@Query("limit") limit: Int, @Query("offset") offset: Int): NetworkResponse<BaseModel<List<ParkIntro>>>

    @GET("api/v1/dataset/6afa114d-38a2-4e3c-9cfd-29d3bd26b65b?scope=resourceAquire")
    suspend fun getAnimalList(@Query("limit") limit: Int, @Query("offset") offset: Int): NetworkResponse<BaseModel<List<Animal>>>

    @GET("api/v1/dataset/e20706d8-bf89-4e6a-9768-db2a10bb2ba4?scope=resourceAquire")
    suspend fun getPlantList(@Query("limit") limit: Int, @Query("offset") offset: Int): NetworkResponse<BaseModel<List<Plant>>>
}