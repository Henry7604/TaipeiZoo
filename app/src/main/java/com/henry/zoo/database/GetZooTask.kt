package com.henry.zoo.database

import android.os.AsyncTask
import com.henry.zoo.database.callback.DatabaseTaskPostExecute
import com.henry.zoo.database.enity.Zoo

/**
 *
 * @author Henry
 */
class GetZooTask constructor(val callback: DatabaseTaskPostExecute<Zoo>): AsyncTask<Int, Void, Zoo>() {
    override fun doInBackground(vararg params: Int?): Zoo? {
        var zoo :Zoo? = null
        if (params.isNotEmpty()) {
            val eNo = params[0] ?: 0
            zoo = DBHelper.instance?.getZooDao()?.getZoo(eNo)
        }
        return zoo
    }

    override fun onPostExecute(result: Zoo?) {
        super.onPostExecute(result)
        callback.onCallback(result)
    }
}