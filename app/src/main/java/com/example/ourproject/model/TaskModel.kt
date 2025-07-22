package com.example.ourproject.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskModel(
    var date : String,
    var title: String,
    var description: String,
    var isDone: Boolean = false
) : Parcelable