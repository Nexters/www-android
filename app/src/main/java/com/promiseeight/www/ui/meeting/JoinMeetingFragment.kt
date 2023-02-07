package com.promiseeight.www.ui.meeting

import android.view.LayoutInflater
import android.view.ViewGroup
import com.promiseeight.www.databinding.FragmentJoinMeetingBinding
import com.promiseeight.www.ui.common.BaseFragment

class JoinMeetingFragment : BaseFragment<FragmentJoinMeetingBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentJoinMeetingBinding {
        return FragmentJoinMeetingBinding.inflate(inflater,container,false)
    }

}