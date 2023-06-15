package com.example.adajob.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.adajob.R
import com.example.adajob.api.response.ListJobResponse
import com.example.adajob.databinding.ItemTaskBinding

class ListPagingAdapter(private val onClick: (ListJobResponse) -> Unit) : PagingDataAdapter<ListJobResponse, ListPagingAdapter.ListViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListJobResponse>() {
            override fun areItemsTheSame(oldItem: ListJobResponse, newItem: ListJobResponse): Boolean {
                return oldItem.task_id == newItem.task_id
            }

            override fun areContentsTheSame(oldItem: ListJobResponse, newItem: ListJobResponse): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val job = getItem(position)
        holder.bind(job)
    }

    inner class ListViewHolder(
        private val binding: ItemTaskBinding,
        private val onClick: (ListJobResponse) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val job = getItem(position)
                    job?.let { onClick(it) }
                }
            }
        }

        fun bind(job: ListJobResponse?) {
            binding.apply {
                job?.let {
                    taskTitle.text = it.task_title
                    taskDeadline.text = root.context.getString(R.string.task_deadline, it.deadline)
                    taskReward.text = root.context.getString(R.string.task_reward, it.reward)
                    taskType.text = root.context.getString(R.string.task_type, it.task_type)
                }
            }
        }
    }
}
