package com.promiseeight.www.ui.meeting.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentMeetingInfoCapacityBinding
import com.promiseeight.www.ui.common.BaseFragment
import com.promiseeight.www.ui.common.InfoFragment
import com.promiseeight.www.ui.meeting.InfoViewModel

class MeetingInfoCapacityFragment : InfoFragment<FragmentMeetingInfoCapacityBinding>() {

    private val viewModel: InfoViewModel by viewModels({ getHostFragment() })

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoCapacityBinding {
        return FragmentMeetingInfoCapacityBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        viewModel.setPage(3)
    }

    override fun initView() {
        super.initView()
        binding.run {
            btnNext.setOnClickListener {
                findNavController().navigate(ACTION_ADD_CAPACITY_TO_PERIOD)
            }

            ivMinus.setOnClickListener {
                viewModel?.minusMeetingCapacity()
            }

            ivPlus.setOnClickListener {
                viewModel?.plusMeetingCapacity()
            }
        }
    }
}