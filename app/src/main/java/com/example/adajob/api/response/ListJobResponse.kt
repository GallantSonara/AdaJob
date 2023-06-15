package com.example.adajob.api.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
@Entity(tableName = "job_table")
data class ListJobResponse(
    @PrimaryKey(autoGenerate = false)
    val task_id: Int,
    val task_title: String,
    val task_type: String,
    val reward_type: String,
    val reward: String,
    val deadline: String,
    val task_link: String,
    val description: String,
    val enrollment: Boolean
) : Parcelable