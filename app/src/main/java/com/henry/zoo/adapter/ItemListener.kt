package com.henry.zoo.adapter

/**
 * recycler view item click listener
 *
 * @author Henry
 */
interface ItemListener<T> {

    fun onItemClick(data: T)
}