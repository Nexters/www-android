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
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentJoinMeetingBinding
import com.promiseeight.www.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JoinMeetingFragment : BaseFragment<FragmentJoinMeetingBinding>() {

    private val viewModel: InfoViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentJoinMeetingBinding {
        return FragmentJoinMeetingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setStatusBarColor(R.color.www_white)
        binding.ivBack.setOnClickListener {
            onClickBackIcon()
        }
        viewModel.totalPage = 4
        parentFragmentManager.backStackEntryCount
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
            }
        }
    }
}