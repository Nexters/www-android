package com.promiseeight.www.ui.meeting

import android.view.LayoutInflater
import android.view.ViewGroup
import com.promiseeight.www.databinding.FragmentMeetingShareBinding
import com.promiseeight.www.ui.common.BaseFragment

class MeetingShareFragment : BaseFragment<FragmentMeetingShareBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingShareBinding {
        return FragmentMeetingShareBinding.inflate(inflater,container,false)
    }
}