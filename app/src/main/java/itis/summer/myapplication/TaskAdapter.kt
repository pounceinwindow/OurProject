package itis.summer.myapplication

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import itis.summer.myapplication.databinding.ItemTaskBinding
import itis.summer.myapplication.model.Task

class TaskAdapter(
    private val listener: OnTaskActionListener
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    interface OnTaskActionListener {
        fun onTaskChecked(taskId: Int, isChecked: Boolean)
        fun onTaskClicked(taskId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TaskViewHolder(
        private val binding: ItemTaskBinding,
        private val listener: OnTaskActionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.apply {
                taskTitle.text = task.title

                if (task.isCompleted && task.completionDate != null) {
                    taskDate.visibility = View.VISIBLE
                    val dateFormat = DateFormat.getDateFormat(root.context)
                    taskDate.text = dateFormat.format(task.completionDate)
                } else {
                    taskDate.visibility = View.GONE
                }

                taskTitle.paintFlags = if (task.isCompleted) {
                    taskTitle.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    taskTitle.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }

                checkboxCompleted.isChecked = task.isCompleted
                checkboxCompleted.setOnCheckedChangeListener { _, isChecked ->
                    listener.onTaskChecked(task.id, isChecked)
                }

                root.setOnClickListener {
                    listener.onTaskClicked(task.id)
                }
            }
        }
    }
}

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Task, newItem: Task) =
        oldItem == newItem && oldItem.completionDate == newItem.completionDate
}