package com.promiseeight.www.ui.meeting.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.promiseeight.www.databinding.FragmentMeetingInfoDateBinding
import com.promiseeight.www.databinding.FragmentMeetingInfoPeriodBinding
import com.promiseeight.www.ui.common.BaseFragment

class MeetingInfoDateFragment : BaseFragment<FragmentMeetingInfoDateBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoDateBinding {
        return FragmentMeetingInfoDateBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            findNavController().navigate(
                MeetingInfoDateFragmentDirections.actionFragmentMeetingInfoDateToFragmentMeetingInfoPlace()
            )
        }
    }
}