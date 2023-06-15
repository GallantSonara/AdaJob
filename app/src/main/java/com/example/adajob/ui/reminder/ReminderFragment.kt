package com.example.adajob.ui.reminder

import android.annotation.SuppressLint
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
import com.example.adajob.databinding.FragmentReminderBinding
import com.example.adajob.utils.ListAdapter
import com.example.adajob.utils.ViewModelFactory

class ReminderFragment : Fragment() {

    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ReminderViewModel
    private lateinit var adapter: ListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelFactory = ViewModelFactory(requireContext(), Application())
        viewModel = ViewModelProvider(this, viewModelFactory)[ReminderViewModel::class.java]

        navigateToDetail()

        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = adapter

        getData()
    }

    private fun navigateToDetail(){
        adapter = ListAdapter(emptyList()) { job ->
            val bundle = Bundle().apply {
                putParcelable("job", job)
            }
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        viewModel.getReminderJob()?.observe(viewLifecycleOwner) { jobs ->
            if (jobs.isEmpty()){

            }
            adapter.job = jobs
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




