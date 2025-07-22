package com.example.ourproject.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ourproject.data.Task

class TaskViewModel : ViewModel() {
    private val tasks = mutableMapOf<String, Task>()
    private var counter = 1

    fun saveTask(id: String?, title: String, desc: String) {
        val realId = id ?: counter++.toString()
        tasks[realId] = Task(realId, title, desc)
    }

    fun getTaskById(id: String): Task? = tasks[id]
}