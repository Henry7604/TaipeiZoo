package com.henry.zoo.adapter

import androidx.databinding.ViewDataBinding

/**
 * Base Recycler View Interface
 *
 * @author Henry
 */
interface AdapterInterface<T> {

    // 資料綁定實作
    fun bindData(binding: ViewDataBinding, data: T)

    // item click實作
    fun onItemClick(data: T)

}