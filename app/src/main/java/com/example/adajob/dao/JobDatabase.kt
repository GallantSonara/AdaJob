package com.example.adajob.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.adajob.api.response.ListJobResponse

@Database(entities = [ListJobResponse::class, RemoteKeys::class, Reminder::class], version = 1, exportSchema = false)
abstract class JobDatabase : RoomDatabase(){

    abstract fun jobDao(): JobDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun reminderDao(): ReminderDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: JobDatabase

        fun getDatabase(context: Context): JobDatabase {
            synchronized(JobDatabase::class) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        JobDatabase::class.java, "reminder_db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}