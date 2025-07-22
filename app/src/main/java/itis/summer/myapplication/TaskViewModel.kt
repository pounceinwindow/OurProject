package itis.summer.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import itis.summer.myapplication.model.Task
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

class TaskViewModel : ViewModel() {
    private val _tasks = MutableLiveData<MutableList<Task>>(mutableListOf())
    val tasks: LiveData<MutableList<Task>> get() = _tasks

    private val deletionJobs = mutableMapOf<Int, Job>()

    fun addTask(title: String) {
        val current = _tasks.value ?: mutableListOf()
        val newId = if (current.isNotEmpty()) current.maxOf { it.id } + 1 else 1
        current.add(Task(newId, title))
        _tasks.value = current
    }

    fun updateTask(task: Task) {
        val current = _tasks.value ?: return
        val index = current.indexOfFirst { it.id == task.id }
        if (index != -1) {
            current[index] = task
            _tasks.value = current
        }
    }

    fun markTaskCompleted(taskId: Int) {
        val current = _tasks.value ?: return
        val task = current.find { it.id == taskId } ?: return

        if (!task.isCompleted) {
            task.isCompleted = true

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            task.completionDate = calendar.time

            updateTask(task)

            deletionJobs[taskId] = viewModelScope.launch {
                delay(3600000)
                deleteTask(taskId)
            }
        }
    }

    private fun deleteTask(taskId: Int) {
        val current = _tasks.value ?: return
        current.removeAll { it.id == taskId }
        _tasks.value = current
        deletionJobs.remove(taskId)
    }

    fun cancelDeletion(taskId: Int) {
        deletionJobs[taskId]?.cancel()
        deletionJobs.remove(taskId)
    }

    fun getTaskById(taskId: Int): Task? {
        return _tasks.value?.find { it.id == taskId }
    }
}