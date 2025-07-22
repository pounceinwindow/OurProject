package itis.summer.myapplication.model

import java.util.Date

data class Task(
    val id: Int,
    var title: String,
    var isCompleted: Boolean = false,
    var completionDate: Date? = null
)