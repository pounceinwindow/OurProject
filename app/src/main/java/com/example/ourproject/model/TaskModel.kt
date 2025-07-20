package com.example.ourproject.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskModel(
    val id: Int,
    val title: String,
    val description: String,
    var is_Completed : Boolean
) : Parcelable