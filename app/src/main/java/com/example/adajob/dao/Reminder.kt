package com.example.adajob.dao

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
@Entity(tableName = "reminder_table")
data class Reminder(
    @PrimaryKey(autoGenerate = false)
    val task_id: Int
) : Parcelable