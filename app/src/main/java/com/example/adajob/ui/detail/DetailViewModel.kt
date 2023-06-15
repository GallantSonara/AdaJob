package com.example.adajob.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.adajob.dao.JobDatabase
import com.example.adajob.dao.Reminder
import com.example.adajob.dao.ReminderDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private var dao: ReminderDao?
    private var database: JobDatabase?

    init {
        database = JobDatabase.getDatabase(application)
        dao = database?.reminderDao()
    }

    fun addToReminder(task_id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val user = Reminder(task_id)
            dao?.addToReminder(user)
        }
    }

    suspend fun checkReminderJob(id: Int) = dao?.checkReminderJobs(id)

    fun removeReminder(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            dao?.deleteReminderJobs(id)
        }
    }
}