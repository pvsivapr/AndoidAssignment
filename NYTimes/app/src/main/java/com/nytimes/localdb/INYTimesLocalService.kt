package com.nytimes.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface INYTimesLocalService {
    @Query("SELECT * FROM nylocaldbmodel")
    fun getLocalNYItemsList() : List<NYLocalDBModel>

    @Insert
    fun insertNYItem(nyLocalDBModel: NYLocalDBModel)
}