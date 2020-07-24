package com.henry.zoo.database

import android.os.AsyncTask
import com.henry.zoo.database.callback.DatabaseTaskPostExecute
import com.henry.zoo.database.enity.Zoo

/**
 *
 * @author Henry
 */
class InsertZooTask constructor(val callback: DatabaseTaskPostExecute<Boolean>): AsyncTask<List<Zoo>, Void, Void>() {
    override fun doInBackground(vararg params: List<Zoo>?): Void? {
        if (params[0] != null) {
            val list = params[0] as List<Zoo>
            DBHelper.instance?.getZooDao()?.insertZooList(list)
        }
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        callback.onCallback(true)
    }
}