package com.henry.zoo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.henry.zoo.database.enity.Plant
import com.henry.zoo.database.enity.Zoo

/**
 *
 * @author Henry
 */
@Dao
interface PlantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlantList(products: List<Plant>)

    @Query("select * from plant where F_Location like :eName")
    fun getPlantListFromLocation(eName: String): List<Plant>

    @Query("select * from plant where F_Name_Ch = :chName")
    fun getPlant(chName: String): Plant
}