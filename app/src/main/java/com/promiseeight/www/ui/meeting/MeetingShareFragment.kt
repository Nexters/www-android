package com.promiseeight.www.ui.meeting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.promiseeight.www.databinding.FragmentMeetingShareBinding
import com.promiseeight.www.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeetingShareFragment : BaseFragment<FragmentMeetingShareBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingShareBinding {
        return FragmentMeetingShareBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //확인을 위한 Toast, 삭제 예정
        navArgs<MeetingShareFragmentArgs>().value.run {
            Toast.makeText(requireContext(),"초대 코드 : $argInvitationCode 링크 : $argInvitationLink",Toast.LENGTH_SHORT).show()
        }
    }
}