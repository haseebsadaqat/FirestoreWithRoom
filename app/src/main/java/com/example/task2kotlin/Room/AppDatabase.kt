package com.example.task2kotlin.Room

import android.content.Context
import android.provider.SyncStateContract.Constants
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.task2kotlin.CommonKeys

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): AppDatabase {
            if (instance == null){
                instance = Room.databaseBuilder(
                    ctx.applicationContext, AppDatabase::class.java,
                    CommonKeys.Db_Name)
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return instance!!

        }

    }

}