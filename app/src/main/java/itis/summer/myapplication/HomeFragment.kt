package itis.summer.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import itis.summer.myapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), TaskAdapter.OnTaskActionListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TaskViewModel by viewModels()

    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        adapter = TaskAdapter(this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HomeFragment.adapter
        }
    }

    private fun setupObservers() {
        viewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            adapter.submitList(tasks.toList())
        }
    }

    private fun setupClickListeners() {
        binding.fabAddTask.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToEditTaskFragment()
            )
        }
    }

    override fun onTaskChecked(taskId: Int, isChecked: Boolean) {
        if (isChecked) {
            viewModel.markTaskCompleted(taskId)
        } else {
            viewModel.cancelDeletion(taskId)
            val task = viewModel.getTaskById(taskId) ?: return
            task.isCompleted = false
            task.completionDate = null
            viewModel.updateTask(task)
        }
    }

    override fun onTaskClicked(taskId: Int) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToEditTaskFragment(taskId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}