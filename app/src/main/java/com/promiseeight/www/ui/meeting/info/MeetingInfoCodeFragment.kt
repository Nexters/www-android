package com.promiseeight.www.ui.meeting.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentMeetingInfoCodeBinding
import com.promiseeight.www.databinding.FragmentMeetingInfoNameBinding
import com.promiseeight.www.ui.common.BaseFragment

class MeetingInfoCodeFragment : BaseFragment<FragmentMeetingInfoCodeBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoCodeBinding {
        return FragmentMeetingInfoCodeBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            findNavController().navigate(
                R.id.action_fragment_join_meeting_info_code_to_fragment_join_meeting_info_user_name
            )
        }
    }
}