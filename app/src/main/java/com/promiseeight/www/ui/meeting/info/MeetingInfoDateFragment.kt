package com.promiseeight.www.ui.meeting.info

import android.view.LayoutInflater
import android.view.ViewGroup
import com.promiseeight.www.databinding.FragmentMeetingInfoPeriodBinding
import com.promiseeight.www.ui.common.BaseFragment

class MeetingInfoDateFragment : BaseFragment<FragmentMeetingInfoPeriodBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoPeriodBinding {
        return FragmentMeetingInfoPeriodBinding.inflate(inflater,container,false)
    }
}