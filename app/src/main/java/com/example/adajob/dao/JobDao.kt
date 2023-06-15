package com.example.adajob.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.adajob.api.response.ListJobResponse

@Dao
interface JobDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJobs(reminder: List<ListJobResponse>)

    @Query("SELECT * FROM job_table")
    fun getAllJobs(): PagingSource<Int, ListJobResponse>

    @Query("DELETE FROM job_table")
    suspend fun deleteAllJob()

    @Query("SELECT COUNT(*) FROM job_table")
    suspend fun getJobCount(): Int

    @Query("SELECT * FROM job_table WHERE task_title LIKE '%' || :searchQuery || '%' OR task_type LIKE '%' || :searchQuery || '%'")
    fun searchJobs(searchQuery: String): PagingSource<Int, ListJobResponse>

    @Query("SELECT * FROM job_table WHERE task_title IN (:task_title)")
    suspend fun getJobsByTaskTitles(task_title: List<String>): List<ListJobResponse>

    @Query("SELECT * FROM job_table WHERE task_title LIKE :task_title")
    suspend fun searchJob(task_title: String): List<ListJobResponse>
}