package com.promiseeight.www.ui.meeting.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentMeetingInfoDateBinding
import com.promiseeight.www.databinding.FragmentMeetingInfoPeriodBinding
import com.promiseeight.www.ui.common.BaseFragment
import com.promiseeight.www.ui.common.InfoFragment
import com.promiseeight.www.ui.meeting.InfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeetingInfoPeriodFragment : InfoFragment<FragmentMeetingInfoPeriodBinding>() {

    private val viewModel : InfoViewModel by viewModels ({ getHostFragment() })

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoPeriodBinding {
        return FragmentMeetingInfoPeriodBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setPage(4)
    }

    override fun initView() {
        super.initView()
        binding.run {
            btnNext.setOnClickListener {
                findNavController().navigate(ACTION_ADD_PERIOD_TO_DATE)
            }
        }
    }
}