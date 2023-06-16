package com.example.adajob.ui.search

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adajob.R
import com.example.adajob.databinding.FragmentSearchBinding
import com.example.adajob.ui.login.LoginActivity
import com.example.adajob.utils.BaseResponses
import com.example.adajob.utils.ListAdapter
import com.example.adajob.utils.UserPreferences
import com.example.adajob.ViewModelFactory

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var pref: UserPreferences
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = ViewModelFactory(requireContext(), Application())
        viewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]

        setupRecyclerView()

        binding.btnClose.setOnClickListener {
            findNavController().navigateUp()
        }

        getSearch()
        logout()
        getData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData(){
        viewModel.searchResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is BaseResponses.Success -> {
                    val jobs = response.data
                    if (jobs != null) {
                        if (jobs.isNotEmpty()) {
                            adapter.job = jobs
                            adapter.notifyDataSetChanged()
                            binding.errorMsg.visibility = View.GONE
                        } else {
                            adapter.job = emptyList()
                            adapter.notifyDataSetChanged()
                            binding.errorMsg.text = getString(R.string.not_found)
                            binding.errorMsg.visibility = View.VISIBLE
                        }
                    }
                }
                is BaseResponses.Error -> {
                    val errorMessage = response.msg
                    binding.errorMsg.text = errorMessage
                    binding.errorMsg.visibility = View.VISIBLE
                }
                else -> {}
            }
        }
    }

    private fun getSearch(){
        binding.searchField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val taskTitle = binding.searchField.text.toString()
                viewModel.searchJobsByTitle(taskTitle)
                hideKeyboard()
                true
            } else {
                false
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = ListAdapter(emptyList()) { job ->
            val bundle = Bundle().apply {
                putParcelable("job", job)
            }
            findNavController().navigate(R.id.action_searchFragment_to_detailFragment, bundle)
        }
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = adapter
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun logout() {
        val btnLogout = binding.btnLogout
        btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.logout_title))
                .setMessage(getString(R.string.logout_message))
                .setPositiveButton("Yes") { dialog, _ ->
                    pref.clearData()
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    requireActivity().finish()
                    startActivity(intent)
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}


