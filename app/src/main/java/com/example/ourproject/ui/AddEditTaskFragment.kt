package com.example.ourproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ourproject.viewmodel.TaskViewModel
import com.example.ourproject.databinding.FragmentTaskAddEditBinding

class AddEditTaskFragment : Fragment() {

    private var _binding: FragmentTaskAddEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TaskViewModel
    private var taskId: String? = null

    companion object {
        private const val ARG_ID = "arg_id"
        fun newInstance(id: String?) = AddEditTaskFragment().apply {
            arguments = Bundle().apply { putString(ARG_ID, id) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskId = arguments?.getString(ARG_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskAddEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]

        taskId?.let { id ->
            viewModel.getTaskById(id)?.let { task ->
                binding.editName.setText(task.title)
                binding.editDate.setText(task.description)
            }
        }

        binding.buttonSubmit.setOnClickListener {
            val title = binding.editName.text.toString().trim()
            val desc = binding.editDate.text.toString().trim()

            if (title.isNotEmpty()) {
                viewModel.saveTask(taskId, title, desc)
                parentFragmentManager.popBackStack()
            } else {
                binding.editName.error = "Введите заголовок"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}