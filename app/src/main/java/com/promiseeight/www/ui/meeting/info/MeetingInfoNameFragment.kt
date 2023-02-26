package com.promiseeight.www.ui.meeting.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.promiseeight.www.databinding.FragmentMeetingInfoNameBinding
import com.promiseeight.www.ui.common.InfoFragment
import com.promiseeight.www.ui.meeting.InfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MeetingInfoNameFragment : InfoFragment<FragmentMeetingInfoNameBinding>() {

    private val viewModel : InfoViewModel by viewModels ({ getHostFragment() })

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoNameBinding {
        return FragmentMeetingInfoNameBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setPage(1)
        binding.viewModel = viewModel

        initObserver()
    }

    override fun initView() {
        super.initView()
        binding.run {
            btnNext.setOnClickListener {
                findNavController().navigate(ACTION_ADD_NAME_TO_USER_NAME)
            }

            ivClose.setOnClickListener {
                viewModel?.setMeetingNameEmpty()
            }
            showKeyboardWithEditText(etInfoName)
            root.setOnClickListener {
                hideKeyboardWithLayout(etInfoName.windowToken)
            }
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.meetingName.collectLatest {

                    }
                }
            }
        }
    }
}