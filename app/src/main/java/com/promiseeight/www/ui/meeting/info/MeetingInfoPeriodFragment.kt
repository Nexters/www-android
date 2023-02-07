package com.promiseeight.www.ui.meeting.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentMeetingInfoDateBinding
import com.promiseeight.www.databinding.FragmentMeetingInfoPeriodBinding
import com.promiseeight.www.ui.common.BaseFragment

class MeetingInfoPeriodFragment : BaseFragment<FragmentMeetingInfoPeriodBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoPeriodBinding {
        return FragmentMeetingInfoPeriodBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            findNavController().navigate(
                R.id.action_fragment_add_meeting_info_period_to_fragment_add_meeting_info_date
            )
        }
    }
}