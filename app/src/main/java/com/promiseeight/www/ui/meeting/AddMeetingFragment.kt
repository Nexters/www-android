package com.promiseeight.www.ui.meeting

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.promiseeight.www.databinding.FragmentAddMeetingBinding
import com.promiseeight.www.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddMeetingFragment : BaseFragment<FragmentAddMeetingBinding>() {

    private val viewModel: InfoViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddMeetingBinding {
        return FragmentAddMeetingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        viewModel.totalPage = 6
        initObserver()
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.page.collectLatest { page ->
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            binding.pbAdd.setProgress(100 * page / viewModel.totalPage, true)
                        } else {
                            binding.pbAdd.progress = 100 * page / viewModel.totalPage
                        }
                    }
                }

                launch {
                    viewModel.meetingName.collectLatest { name ->
                        if(name.isNotBlank() && viewModel.page.value > 1){
                            binding.tvTitle.text = name
                        } else {
                            binding.tvTitle.text = ""
                        }
                    }
                }
            }
        }
    }
}