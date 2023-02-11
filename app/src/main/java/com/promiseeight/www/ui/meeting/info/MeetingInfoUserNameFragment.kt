package com.promiseeight.www.ui.meeting.info

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentMeetingInfoUserNameBinding
import com.promiseeight.www.ui.common.AddNavHostFragment
import com.promiseeight.www.ui.common.BaseFragment
import com.promiseeight.www.ui.common.JoinNavHostFragment
import com.promiseeight.www.ui.meeting.AddMeetingFragment

class MeetingInfoUserNameFragment : BaseFragment<FragmentMeetingInfoUserNameBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoUserNameBinding {
        return FragmentMeetingInfoUserNameBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            when (parentFragment) {
                is JoinNavHostFragment -> {
                    findNavController().navigate(
                        R.id.action_fragment_join_meeting_info_user_name_to_fragment_join_meeting_info_date
                    )
                }
                is AddNavHostFragment -> {
                    findNavController().navigate(
                        R.id.action_fragment_add_meeting_info_user_name_to_fragment_add_meeting_info_capacity
                    )
                }

            }

        }
    }
}