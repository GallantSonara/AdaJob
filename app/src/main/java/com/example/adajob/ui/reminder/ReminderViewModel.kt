package com.example.adajob.ui.reminder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.adajob.api.response.ListJobResponse
import com.example.adajob.dao.JobDatabase
import com.example.adajob.dao.ReminderDao

class ReminderViewModel(application: Application) : AndroidViewModel(application) {

    private var dao: ReminderDao?
    private var dBase: JobDatabase?

    init {
        dBase = JobDatabase.getDatabase(application)
        dao = dBase?.reminderDao()
    }

    fun getReminderJob() : LiveData<List<ListJobResponse>>? {
        return dao?.getReminderJobs()
    }
}