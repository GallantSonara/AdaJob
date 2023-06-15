package com.example.adajob.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.adajob.api.response.ListJobResponse

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToReminder(reminder: Reminder)

    @Query("SELECT * FROM job_table INNER JOIN reminder_table ON job_table.task_id = reminder_table.task_id")
    fun getReminderJobs(): LiveData<List<ListJobResponse>>

    @Query("SELECT count(*) FROM reminder_table WHERE reminder_table.task_id = :id")
    suspend fun checkReminderJobs(id: Int): Int

    @Query("DELETE FROM reminder_table WHERE reminder_table.task_id = :id")
    suspend fun deleteReminderJobs(id: Int): Int
}