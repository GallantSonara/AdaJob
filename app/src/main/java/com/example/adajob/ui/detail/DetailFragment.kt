@file:Suppress("DEPRECATION")

package com.example.adajob.ui.detail

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.adajob.R
import com.example.adajob.api.response.ListJobResponse
import com.example.adajob.databinding.FragmentDetailBinding
import com.example.adajob.ViewModelFactory
import kotlinx.coroutines.*
import java.util.*

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory(requireContext(), Application())
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val job = arguments?.getParcelable<ListJobResponse>("job") ?: return

        binding.apply {
            tvTitle.text = job.task_title
            tvDescription.text = job.description
            tvTaskType.text = getString(R.string.task_type, job.task_type)
            tvDeadline.text = getString(R.string.task_deadline, job.deadline)
            tvReward.text = getString(R.string.task_reward, job.reward)
            tvRewardType.text = getString(R.string.task_rewardType, job.reward_type)
        }

        val taskId = job.task_id
        val taskDesc = job.description

        setRegex(taskDesc)
        getAndSetReminder(taskId)
        setupTopBar()
    }

    private fun getAndSetReminder(taskId: Int){
        var isChecked = false
        binding.btnReminder.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.addToReminder(taskId)
                    withContext(Dispatchers.Main) {
                        binding.btnReminder.isChecked = isChecked
                    }
                }
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.removeReminder(taskId)
                    withContext(Dispatchers.Main) {
                        binding.btnReminder.isChecked = isChecked
                    }
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkReminderJob(taskId)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.btnReminder.isChecked = true
                        isChecked = true
                    } else {
                        binding.btnReminder.isChecked = false
                        isChecked = false
                    }
                }
            }
        }
    }

    private fun setRegex(taskDesc: String){
        val spannableString = SpannableString(taskDesc)

        val pattern = "(http|https)://[\\w-]+(\\.[\\w-]+)+(/[\\w- ./?%&=]*)?".toRegex()
        val matches = pattern.findAll(taskDesc)

        for (match in matches) {
            val link = match.value
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                    startActivity(intent)
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = true
                    ds.color = ContextCompat.getColor(requireContext(), R.color.rich_electric_blue)
                }
            }

            val start = match.range.first
            val end = match.range.last + 1
            spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        binding.tvDescription.movementMethod = LinkMovementMethod.getInstance()
        binding.tvDescription.setText(spannableString, TextView.BufferType.SPANNABLE)
    }

    private fun setupTopBar(){
        val toolbarTitle = binding.toolbarTitle
        toolbarTitle.text = getString(R.string.detail)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}