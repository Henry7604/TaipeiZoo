package com.henry.zoo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.henry.zoo.database.enity.Zoo

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
}