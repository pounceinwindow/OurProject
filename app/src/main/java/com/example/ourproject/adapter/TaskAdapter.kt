package com.example.todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ourproject.databinding.ItemTaskBinding
import com.example.ourproject.model.TaskModel
// мне нужно увидеть фрагмент добвления задачи
class TaskAdapter(
    private val tasks: List<TaskModel>,
    private val onItemClicked: (TaskModel) -> Unit,
    private val onCheckboxClicked: (TaskModel) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: TaskModel) {
            binding.titleText.text = task.title
            binding.descText.text = task.description
            binding.dateText.text = task.date
            binding.checkBox.isChecked = task.isDone

            binding.root.setOnClickListener {
                onItemClicked(task)
            }

            binding.checkBox.setOnClickListener {
                task.isDone = binding.checkBox.isChecked
                onCheckboxClicked(task)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size
}
