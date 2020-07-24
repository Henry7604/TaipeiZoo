package com.henry.zoo.database.callback

/**
 * task call back
 *
 * @author Henry
 */
interface DatabaseTaskPostExecute<T> {
    fun onCallback(result: T?)
}