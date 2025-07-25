package com.example.ourproject.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ourproject.model.TaskModel

class TaskViewModel : ViewModel() {

    private val tasks = mutableMapOf<String, TaskModel>()
    private var counter = 1

    fun saveTask(id: String?, title: String, description: String, date: String) {
        val realId = id ?: counter++.toString()
        tasks[realId] = TaskModel(id = realId, date = date, title = title, description = description)
    }

    fun getTaskById(id: String): TaskModel? = tasks[id]

    fun getAllTasks(): List<TaskModel> = tasks.values.toList()
}
