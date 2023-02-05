package com.promiseeight.www.ui.meeting.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.promiseeight.www.databinding.FragmentMeetingInfoCapacityBinding
import com.promiseeight.www.ui.common.BaseFragment

class MeetingInfoCapacityFragment : BaseFragment<FragmentMeetingInfoCapacityBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoCapacityBinding {
        return FragmentMeetingInfoCapacityBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            findNavController().navigate(
                MeetingInfoCapacityFragmentDirections.actionFragmentMeetingInfoCapacityToFragmentMeetingInfoPeriod()
            )
        }
    }
}