package com.promiseeight.www.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.promiseeight.www.databinding.FragmentMainOnGoingBinding
import com.promiseeight.www.ui.common.BaseFragment
import com.promiseeight.www.ui.meeting.info.Hilt_MeetingInfoCodeFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainOnGoingFragment : BaseFragment<FragmentMainOnGoingBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainOnGoingBinding {
        return FragmentMainOnGoingBinding.inflate(inflater,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewpager = binding.ongoingMeetPagerMain
    }

}