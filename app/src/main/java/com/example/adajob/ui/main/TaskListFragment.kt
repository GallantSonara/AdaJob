package com.example.adajob.ui.main

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adajob.R
import com.example.adajob.databinding.FragmentTaskListBinding
import com.example.adajob.utils.LoadingStateAdapter
import com.example.adajob.utils.ListPagingAdapter
import com.example.adajob.utils.ViewModelFactory

class TaskListFragment : Fragment() {
    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ListPagingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelFactory = ViewModelFactory(requireContext(), Application())
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        navigateToDetail()
        getData()
    }

    private fun getData() {
        binding.recyclerview.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        viewModel.job.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun navigateToDetail() {
        adapter = ListPagingAdapter { job ->
            val bundle = Bundle().apply {
                putParcelable("job", job)
            }
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}