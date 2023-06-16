package com.example.adajob.ui.main

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.adajob.R
import com.example.adajob.databinding.FragmentMainBinding
import com.example.adajob.ui.login.LoginActivity
import com.example.adajob.utils.PagerAdapter
import com.example.adajob.utils.UserPreferences
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var pref: UserPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = UserPreferences(requireContext())
        tabLayout = binding.tablayout
        viewPager = binding.viewpager

        setUpViewPager()
        showSearch()
        logout()

    }
    private fun setUpViewPager(){
        viewPager.adapter = PagerAdapter(requireActivity())
        TabLayoutMediator(tabLayout, viewPager) { tab, index ->
            tab.text = when (index) {
                0 -> {
                    getString(R.string.task_list)
                }
                1 -> {
                    getString(R.string.recommendation_task)
                }
                2 -> {
                    getString(R.string.reminder_task)
                }
                else -> {
                    throw Resources.NotFoundException("Position Not Found")
                }
            }
        }.attach()
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

    private fun showSearch() {
        val btnSearch = binding.btnSearch

        btnSearch.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}