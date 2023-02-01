package com.promiseeight.www.ui.meeting.info

import android.view.LayoutInflater
import android.view.ViewGroup
import com.promiseeight.www.databinding.FragmentMeetingInfoNameBinding
import com.promiseeight.www.ui.common.BaseFragment

class MeetingInfoNameFragment : BaseFragment<FragmentMeetingInfoNameBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoNameBinding {
        return FragmentMeetingInfoNameBinding.inflate(inflater,container,false)
    }
}