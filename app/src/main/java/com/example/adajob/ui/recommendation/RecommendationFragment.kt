package com.example.adajob.ui.recommendation

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
import com.example.adajob.api.body.RecommendationRequest
import com.example.adajob.databinding.FragmentRecommendationBinding
import com.example.adajob.utils.BaseResponses
import com.example.adajob.utils.ListAdapter
import com.example.adajob.utils.ViewModelFactory

class RecommendationFragment : Fragment() {

    private var _binding: FragmentRecommendationBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RecommendationViewModel
    private lateinit var adapter: ListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRecommendationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory(requireContext(), Application())
        viewModel = ViewModelProvider(this, factory)[RecommendationViewModel::class.java]

        navigateToDetail()
        setupRecyclerview()
        getRecommendation(getRandomNumber())
    }

    private fun getRandomNumber(): Int {
        return (1..100).random()
    }

    private fun setupRecyclerview(){
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = adapter
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
    private fun getRecommendation(userID: Int) {
        viewModel.fetchRecommendationData(RecommendationRequest(userID))

        viewModel.jobsData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is BaseResponses.Success -> {
                    showLoading(false)
                    val jobs = response.data
                    if (jobs != null) {
                        adapter.job = jobs
                        adapter.notifyDataSetChanged()
                    }
                }
                is BaseResponses.Loading -> {
                    showLoading(true)
                }
                is BaseResponses.Error -> {
                    showLoading(false)
                    val errorMsg = response.msg
                    showError(errorMsg)
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) binding.progressBar.visibility = View.VISIBLE else binding.progressBar.visibility = View.GONE
    }

    private fun showError(msg: String?) {
        val errorMessage = getString(R.string.not_found) + " $msg"
        binding.apply {
            errorMsg.visibility = View.VISIBLE
            errorMsg.text = errorMessage
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}