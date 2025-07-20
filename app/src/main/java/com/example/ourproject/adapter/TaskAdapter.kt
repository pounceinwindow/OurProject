package com.example.ourproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ourproject.databinding.ItemTaskBinding //itemtusk сделаете заработает
import com.example.ourproject.model.TaskModel
//import com.example.ourproject.util.CatGenerator  ???? obsudit вроде мы не генерим

class TaskAdapter(
    private val tasks: List<TaskModel>,
    private val onItemClicked: (TaskModel) -> Unit,
    private val onImageClicked: (TaskModel) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TuskViewHolder>() {

    inner class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: TaskModel) {
            binding.titleText.text = task.title
            binding.descText.text = task.description
            binding.root.setOnClickListener { onItemClicked(task) }
            binding.taskCompleted.setOnClickListener {
                task.is_Completed = true // кружок закрашивается зеленым цветом
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTuskBinding.inflate(inflater, parent, false)
        return TuskViewHolder(binding)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }
}