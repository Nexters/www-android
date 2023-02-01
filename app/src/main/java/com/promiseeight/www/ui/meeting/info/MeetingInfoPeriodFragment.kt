package com.promiseeight.www.ui.meeting.info

import android.view.LayoutInflater
import android.view.ViewGroup
import com.promiseeight.www.databinding.FragmentMeetingInfoDateBinding
import com.promiseeight.www.ui.common.BaseFragment

class MeetingInfoPeriodFragment : BaseFragment<FragmentMeetingInfoDateBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoDateBinding {
        return FragmentMeetingInfoDateBinding.inflate(inflater,container,false)
    }
}