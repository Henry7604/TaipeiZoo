package com.henry.zoo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.henry.zoo.database.enity.Zoo

/**
 *
 * @author Henry
 */
@Dao
interface ZooDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertZooList(products: List<Zoo>)

    @Query("select * from zoo where E_no =:eNo")
    fun getZoo(eNo: Int): Zoo

    @Query("select * from zoo order by E_no")
    fun getAll() : List<Zoo>
}