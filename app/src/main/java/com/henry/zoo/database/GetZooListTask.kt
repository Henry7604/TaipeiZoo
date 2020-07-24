package com.henry.zoo.database

import android.os.AsyncTask
import com.henry.zoo.database.callback.DatabaseTaskPostExecute
import com.henry.zoo.database.enity.Zoo

/**
 *
 * @author Henry
 */
class GetZooListTask constructor(val callback: DatabaseTaskPostExecute<List<Zoo>>): AsyncTask<Void, Void, List<Zoo>>() {
    override fun doInBackground(vararg params: Void?): List<Zoo> {
        return DBHelper.instance?.getZooDao()?.getAll() ?: listOf()
    }

    override fun onPostExecute(result: List<Zoo>) {
        super.onPostExecute(result)
        callback.onCallback(result)
    }
}