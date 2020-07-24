package com.henry.zoo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.henry.zoo.database.dao.PlantDao
import com.henry.zoo.database.dao.ZooDao
import com.henry.zoo.database.enity.Plant
import com.henry.zoo.database.enity.Zoo

/**
 * database
 *
 * @author Henry
 */
@Database(entities = [Zoo::class, Plant::class], version = 1)
abstract class TaipeiZooDatabase: RoomDatabase() {
    abstract fun zooDao(): ZooDao
    abstract fun plantDao(): PlantDao
}