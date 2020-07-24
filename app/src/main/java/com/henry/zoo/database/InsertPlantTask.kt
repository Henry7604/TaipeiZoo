package com.henry.zoo.database

import android.os.AsyncTask
import com.henry.zoo.database.callback.DatabaseTaskPostExecute
import com.henry.zoo.database.enity.Plant

/**
 *
 * @author Henry
 */
class InsertPlantTask constructor(): AsyncTask<List<Plant>, Void, Void>() {
    override fun doInBackground(vararg params: List<Plant>?): Void? {
        if (params[0] != null) {
            val list = params[0] as List<Plant>
            DBHelper.instance?.getPlantDao()?.insertPlantList(list)
        }
        return null
    }
}