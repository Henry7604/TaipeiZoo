package com.henry.zoo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.henry.zoo.database.GetPlantListTask
import com.henry.zoo.database.GetZooTask
import com.henry.zoo.database.callback.DatabaseTaskPostExecute
import com.henry.zoo.database.enity.Plant
import com.henry.zoo.database.enity.Zoo

/**
 * 館區詳細view model
 *
 * @author Henry
 */
class CategoryDetailViewModel(): ViewModel() {
    val photoUrl = MutableLiveData<String>()
    val info = MutableLiveData<String>()
    val memo = MutableLiveData<String>()
    val category = MutableLiveData<String>()
    val url = MutableLiveData<String>()
    val plantList = MutableLiveData<List<Plant>>()

    init {
        photoUrl.value = ""
        info.value = ""
        memo.value = ""
        category.value = ""
        url.value = ""
        plantList.value = ArrayList()
    }

    fun getZooAndPlantList(eNo: Int) {
        if (plantList.value?.isEmpty()!!) {
            GetZooTask(object : DatabaseTaskPostExecute<Zoo> {
                override fun onCallback(result: Zoo?) {
                    result?.let { it ->
                        val eName = it.E_Name
                        photoUrl.postValue(it.E_Pic_URL)
                        info.postValue(it.E_Info)
                        memo.postValue(it.E_Memo)
                        category.postValue(it.E_Category)
                        url.postValue(it.E_URL)
                        GetPlantListTask(object : DatabaseTaskPostExecute<List<Plant>> {
                            override fun onCallback(result: List<Plant>?) {
                                result?.run {
//                                    val header = Plant(photoUrl = it.E_Pic_URL, info = it.E_Info, memo = it.E_Memo, category = it.E_Category, url = it.E_URL)
//                                    this.add(0, header)
                                    plantList.postValue(this)
                                }
                            }
                        }).execute("%$eName%")
                    }
                }
            }).execute(eNo)
        }
    }
}