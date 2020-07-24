package com.henry.zoo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.henry.zoo.R
import com.henry.zoo.database.enity.Zoo
import com.henry.zoo.utility.ResourceProvider

/**
 *
 * @author Henry
 */
class MainViewModel: ViewModel() {
    val isShowBack = MutableLiveData<Boolean>()
    val title = MutableLiveData<String>()

    init {
        isShowBack.value = false
        title.value = ResourceProvider.getString(R.string.home_title)
    }
}