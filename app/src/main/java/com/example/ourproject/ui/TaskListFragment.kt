package com.example.ourproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ourproject.R
import com.example.ourproject.adapter.TaskAdapter
import com.example.ourproject.databinding.FragmentTaskListBinding
import com.example.ourproject.viewmodel.TaskViewModel

class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]

        val adapter = TaskAdapter(
            tasks = viewModel.getAllTasks(),
            onItemClicked = { task ->
                val bundle = Bundle().apply {
                    putString("arg_id", task.id)
                }
                findNavController().navigate(R.id.action_taskListFragment_to_addEditTaskFragment, bundle)
            },
            onCheckboxClicked = { task ->
            }
        )

        binding.recyclerView.adapter = adapter

        binding.fabAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_taskListFragment_to_addEditTaskFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
