package com.henry.zoo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.henry.zoo.R
import com.henry.zoo.database.GetPlantTask
import com.henry.zoo.database.callback.DatabaseTaskPostExecute
import com.henry.zoo.database.enity.Plant
import com.henry.zoo.utility.ResourceProvider
import java.lang.StringBuilder

/**
 * 植物介紹 view model
 *
 * @author Henry
 */
class PlantDetailViewModel: ViewModel() {
    val photoUrl = MutableLiveData<String>()
    val content = MutableLiveData<String>()

    init {
        photoUrl.value = ""
        content.value = ""
    }

    fun getPlant(name: String) {
        GetPlantTask(object : DatabaseTaskPostExecute<Plant> {
            override fun onCallback(result: Plant?) {
                result?.let {
                    photoUrl.postValue(it.F_Pic01_URL)

                    val stringBuilder = StringBuilder()
                    // 中文
                    stringBuilder.append(it.F_Name_Ch)
                    // 英文
                    stringBuilder.append("\n${it.F_Name_En}\n\n")

                    // 別名
                    stringBuilder.append(ResourceProvider.getString(R.string.also_known))
                    stringBuilder.append("\n${it.F_AlsoKnown}\n\n")

                    // 簡介
                    stringBuilder.append(ResourceProvider.getString(R.string.brief))
                    stringBuilder.append("\n${it.F_Brief}\n\n")

                    // 辨認方式
                    stringBuilder.append(ResourceProvider.getString(R.string.feature))
                    stringBuilder.append("\n${it.F_Feature}\n\n")

                    // 功能性
                    stringBuilder.append(ResourceProvider.getString(R.string.function))
                    stringBuilder.append("\n${it.F_Function_Application}\n\n")

                    // 最後更新
                    stringBuilder.append(String.format(ResourceProvider.getString(R.string.update), it.F_Update))

                    content.postValue(stringBuilder.toString())
                }
            }
        }).execute(name)
    }
}