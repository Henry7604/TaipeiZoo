package com.henry.zoo.database

import android.content.Context
import androidx.room.Room
import com.henry.zoo.database.dao.PlantDao
import com.henry.zoo.database.dao.ZooDao

/**
 * db helper
 *
 * @author Henry
 */
class DBHelper constructor(var context: Context) {
    val DB_NAME = "Taipei_Zoo.db"
    var appDB: TaipeiZooDatabase? = null

    init {
        appDB = Room.databaseBuilder(context, TaipeiZooDatabase::class.java, DB_NAME).fallbackToDestructiveMigration().build()
    }

    companion object {

        var instance: DBHelper? = null

        fun init(context: Context): DBHelper {
            if (instance == null) {
                synchronized(DBHelper::class) {
                    if (instance == null) {
                        instance = DBHelper(context)
                    }
                }
            }
            return instance!!
        }

    }

    fun onDestory() {
        if (appDB != null) {
            appDB!!.close()
        }
    }

    fun getZooDao(): ZooDao {
        return appDB!!.zooDao()
    }

    fun getPlantDao(): PlantDao {
        return appDB!!.plantDao()
    }
}