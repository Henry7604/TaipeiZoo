package com.henry.zoo.database.enity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * @author Henry
 */
@Entity(tableName = "zoo")
data class Zoo(
    @ColumnInfo(name = "E_Category")
    val E_Category: String,
    @ColumnInfo(name = "E_Geo")
    val E_Geo: String,
    @ColumnInfo(name = "E_Info")
    val E_Info: String,
    @ColumnInfo(name = "E_Memo")
    val E_Memo: String,
    @ColumnInfo(name = "E_Name")
    val E_Name: String,
    @ColumnInfo(name = "E_Pic_URL")
    val E_Pic_URL: String,
    @ColumnInfo(name = "E_URL")
    val E_URL: String,
    @PrimaryKey
    @ColumnInfo(name = "E_no")
    val E_no: Int
) {
}