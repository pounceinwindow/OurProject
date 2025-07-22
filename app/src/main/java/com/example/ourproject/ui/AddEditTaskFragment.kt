package com.example.ourproject.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ourproject.databinding.FragmentTaskAddEditBinding
import com.example.ourproject.viewmodel.TaskViewModel

class AddEditTaskFragment : Fragment() {

    private var _binding: FragmentTaskAddEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TaskViewModel
    private var taskId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskId = arguments?.getString("arg_id")
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

        binding.buttonSubmit.isEnabled = false

        binding.editName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.buttonSubmit.isEnabled = !s.isNullOrEmpty()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

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
