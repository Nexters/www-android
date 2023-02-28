package com.promiseeight.www.ui.meeting

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.promiseeight.www.R
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

        binding.btnShare.setOnClickListener {
            copy(navArgs<MeetingShareFragmentArgs>().value.argInvitationLink)
        }

        binding.btnNext.setOnClickListener {
//            findNavController().navigate(
//                Uri.parse("https://www/meeting/detail/${navArgs<MeetingShareFragmentArgs>().value.argInvitationCode}"),
//                NavOptions.Builder().apply {
//                setPopUpTo(R.id.fragment_home,false)
//            }.build())
        }
    }

    private fun copy(text : String){
        try {
            (requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).run {
                setPrimaryClip(ClipData.newPlainText("link",text))
                showToast(getString(R.string.copy_link_success))
            }
        } catch (e : Exception){
            showToast(getString(R.string.copy_link_fail))
        }

    }
}