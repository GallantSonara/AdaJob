package com.example.adajob.utils

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.adajob.ui.main.TaskListFragment
import com.example.adajob.ui.recommendation.RecommendationFragment
import com.example.adajob.ui.reminder.ReminderFragment

class PagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> { TaskListFragment() }
            1 -> { RecommendationFragment() }
            2 -> { ReminderFragment() }
            else -> { throw Resources.NotFoundException("Position Not Found") }
        }
    }
}