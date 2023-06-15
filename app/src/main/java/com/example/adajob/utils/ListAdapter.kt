package com.example.adajob.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adajob.R
import com.example.adajob.api.response.ListJobResponse
import com.example.adajob.databinding.ItemTaskBinding

class ListAdapter(var job: List<ListJobResponse>, private val onClick: (ListJobResponse) -> Unit) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val job = job[position]
        holder.bind(job)
        holder.itemView.setOnClickListener {
            onClick(job)
        }
    }

    override fun getItemCount(): Int {
        return job.size
    }

    inner class ViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(job: ListJobResponse) {
            binding.apply {
                taskTitle.text = job.task_title
                taskDeadline.text = root.context.getString(R.string.task_deadline, job.deadline)
                taskReward.text = root.context.getString(R.string.task_reward, job.reward)
                taskType.text = root.context.getString(R.string.task_type, job.task_type)
            }
        }
    }
}
