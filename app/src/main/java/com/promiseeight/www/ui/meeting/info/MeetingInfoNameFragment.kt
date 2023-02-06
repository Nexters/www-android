package com.promiseeight.www.ui.meeting.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.promiseeight.www.databinding.FragmentMeetingInfoNameBinding
import com.promiseeight.www.ui.common.BaseFragment

class MeetingInfoNameFragment : BaseFragment<FragmentMeetingInfoNameBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoNameBinding {
        return FragmentMeetingInfoNameBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            findNavController().navigate(
                MeetingInfoNameFragmentDirections.actionFragmentMeetingInfoNameToFragmentMeetingInfoUserName()
            )
        }
    }
}