package com.promiseeight.www.ui.meeting.info

import android.view.LayoutInflater
import android.view.ViewGroup
import com.promiseeight.www.databinding.FragmentMeetingInfoCapacityBinding
import com.promiseeight.www.ui.common.BaseFragment

class MeetingInfoCapacityFragment : BaseFragment<FragmentMeetingInfoCapacityBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoCapacityBinding {
        return FragmentMeetingInfoCapacityBinding.inflate(inflater,container,false)
    }
}