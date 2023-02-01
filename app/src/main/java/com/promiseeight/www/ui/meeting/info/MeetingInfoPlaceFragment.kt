package com.promiseeight.www.ui.meeting.info

import android.view.LayoutInflater
import android.view.ViewGroup
import com.promiseeight.www.databinding.FragmentMeetingInfoPlaceBinding
import com.promiseeight.www.ui.common.BaseFragment

class MeetingInfoPlaceFragment : BaseFragment<FragmentMeetingInfoPlaceBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoPlaceBinding {
        return FragmentMeetingInfoPlaceBinding.inflate(inflater, container, false)
    }
}