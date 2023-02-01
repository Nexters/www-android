package com.promiseeight.www.ui.meeting.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.promiseeight.www.databinding.FragmentMeetingInfoUserNameBinding
import com.promiseeight.www.ui.common.BaseFragment

class MeetingInfoUserNameFragment : BaseFragment<FragmentMeetingInfoUserNameBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoUserNameBinding {
        return FragmentMeetingInfoUserNameBinding.inflate(inflater,container,false)
    }
}