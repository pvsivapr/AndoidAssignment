package com.nytimes.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

object NYTimesLocalService
{
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "Kotlin_Training_DB"
        ).build()
}

@Database(entities = arrayOf(NYLocalDBModel::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun nyTimesLocalDBService(): INYTimesLocalService
}