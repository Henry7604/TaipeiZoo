package com.henry.zoo.database

import android.os.AsyncTask
import com.henry.zoo.database.callback.DatabaseTaskPostExecute
import com.henry.zoo.database.enity.Plant
import com.henry.zoo.database.enity.Zoo

/**
 *
 * @author Henry
 */
class GetPlantTask constructor(val callback: DatabaseTaskPostExecute<Plant>): AsyncTask<String, Void, Plant>() {
    override fun doInBackground(vararg params: String?): Plant? {
        var plant :Plant? = null
        if (params.isNotEmpty()) {
            val chName = params[0] ?: ""
            plant = DBHelper.instance?.getPlantDao()?.getPlant(chName)
        }
        return plant
    }

    override fun onPostExecute(result: Plant?) {
        super.onPostExecute(result)
        callback.onCallback(result)
    }
}