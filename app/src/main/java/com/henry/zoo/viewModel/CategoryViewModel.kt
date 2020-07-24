package com.henry.zoo.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.henry.zoo.database.GetZooListTask
import com.henry.zoo.database.InsertPlantTask
import com.henry.zoo.database.InsertZooTask
import com.henry.zoo.database.callback.DatabaseTaskPostExecute
import com.henry.zoo.database.enity.Plant
import com.henry.zoo.database.enity.Zoo
import com.henry.zoo.utility.KUtils
import com.henry.zoo.utility.ResourceProvider

/**
 * 館區簡介
 *
 * @author Henry
 */
class CategoryViewModel: ViewModel() {
    val list = MutableLiveData<List<Zoo>>()

    init {
        list.value = ArrayList()
    }

    fun getZoo() {
        GetZooListTask(object : DatabaseTaskPostExecute<List<Zoo>> {
            override fun onCallback(result: List<Zoo>?) {
                result?.run {
                    if (this.isNotEmpty()) {
                        list.postValue(this)
                    } else {
                        val zooType = KUtils.getTypeToken<List<Zoo>>()
                        val zooGson = Gson()
                        val zooList = zooGson.fromJson<List<Zoo>>(ResourceProvider.getAssets("zoo.txt"), zooType)

                        val plantType = KUtils.getTypeToken<List<Plant>>()
                        val plantGson = Gson()
                        val plantList = plantGson.fromJson<List<Plant>>(ResourceProvider.getAssets("plant.txt"), plantType)

                        InsertZooTask(object : DatabaseTaskPostExecute<Boolean> {
                            override fun onCallback(result: Boolean?) {
                                list.postValue(zooList)
                            }
                        }).execute(zooList)
                        InsertPlantTask().execute(plantList)
                    }
                }

            }
        }).execute()
    }
}