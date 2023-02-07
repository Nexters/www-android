package com.promiseeight.www.ui.meeting.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentMeetingInfoPlaceBinding
import com.promiseeight.www.ui.common.AddNavHostFragment
import com.promiseeight.www.ui.common.BaseFragment
import com.promiseeight.www.ui.common.JoinNavHostFragment
import com.promiseeight.www.ui.meeting.AddMeetingFragmentDirections

class MeetingInfoPlaceFragment : BaseFragment<FragmentMeetingInfoPlaceBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoPlaceBinding {
        return FragmentMeetingInfoPlaceBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            when (parentFragment) {
                is JoinNavHostFragment -> {

                }
                is AddNavHostFragment -> {
                    Navigation.findNavController(requireActivity(), R.id.fcv_main) // 인터페이스 만들어보기
                        .navigate(
                            AddMeetingFragmentDirections.actionFragmentAddMeetingToFragmentMeetingShare()
                        )
                }

            }

        }
    }
}