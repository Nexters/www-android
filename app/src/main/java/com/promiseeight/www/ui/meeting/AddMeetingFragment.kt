package com.promiseeight.www.ui.meeting

import android.view.LayoutInflater
import android.view.ViewGroup
import com.promiseeight.www.databinding.FragmentAddMeetingBinding
import com.promiseeight.www.ui.common.BaseFragment

class AddMeetingFragment : BaseFragment<FragmentAddMeetingBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddMeetingBinding {
        return FragmentAddMeetingBinding.inflate(inflater,container,false)
    }

}