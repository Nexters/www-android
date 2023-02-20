package com.promiseeight.www.ui.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.promiseeight.www.databinding.FragmentHomeBinding
import com.promiseeight.www.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionFragmentHomeToFragmentAddMeeting()
            )
        }
        binding.btnJoin.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionFragmentHomeToFragmentJoinMeeting()
            )
        }
        binding.btnTest.setOnClickListener {
//            findNavController().navigate(
//                HomeFragmentDirections.actionFragmentHomeToFragmentMeetingDetail()
//            )
            findNavController().navigate(Uri.parse("https://www/meeting/detail"))
        }

        binding.ivSetting.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionFragmentHomeToFragmentSetting()
            )
        }

    }
}