package com.henry.zoo.database

import android.os.AsyncTask
import com.henry.zoo.database.callback.DatabaseTaskPostExecute
import com.henry.zoo.database.enity.Plant
import com.henry.zoo.database.enity.Zoo

/**
 *
 * @author Henry
 */
class GetPlantListTask constructor(val callback: DatabaseTaskPostExecute<ArrayList<Plant>>): AsyncTask<String, Void, ArrayList<Plant>>() {
    override fun doInBackground(vararg params: String?): ArrayList<Plant> {
        val eName = params[0] ?: ""
        val list = ArrayList<Plant>()
        DBHelper.instance?.getPlantDao()?.getPlantListFromLocation(eName)?.let { list.addAll(it) }
        return list
    }

    override fun onPostExecute(result: ArrayList<Plant>) {
        super.onPostExecute(result)
        callback.onCallback(result)
    }
}