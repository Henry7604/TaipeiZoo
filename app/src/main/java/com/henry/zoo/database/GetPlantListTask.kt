package com.henry.zoo.database

import android.os.AsyncTask
import com.henry.zoo.database.callback.DatabaseTaskPostExecute
import com.henry.zoo.database.enity.Plant
import com.henry.zoo.database.enity.Zoo

/**
 *
 * @author Henry
 */
class GetPlantListTask constructor(val callback: DatabaseTaskPostExecute<List<Plant>>): AsyncTask<String, Void, List<Plant>>() {
    override fun doInBackground(vararg params: String?): List<Plant> {
        val eName = params[0] ?: ""
        return DBHelper.instance?.getPlantDao()?.getPlantListFromLocation(eName) ?: listOf()
    }

    override fun onPostExecute(result: List<Plant>) {
        super.onPostExecute(result)
        callback.onCallback(result)
    }
}