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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentMeetingShareBinding
import com.promiseeight.www.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MeetingShareFragment : BaseFragment<FragmentMeetingShareBinding>() {

    private val viewModel : MeetingShareViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingShareBinding {
        return FragmentMeetingShareBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnShare.setOnClickListener {
            copy(navArgs<MeetingShareFragmentArgs>().value.argInvitationCode)
        }

        binding.btnNext.setOnClickListener {
            viewModel.getMeetingByCode(navArgs<MeetingShareFragmentArgs>().value.argInvitationCode)
        }

        initObserver()
    }

    private fun initObserver(){
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.meetingId.collectLatest {
                        if(it>=0){
                            findNavController().navigate(
                                MeetingShareFragmentDirections.actionFragmentMeetingShareToFragmentMeetingDetail(it.toString())
                            )
                        }
                    }
                }
            }
        }
    }

    private fun copy(text : String){
        try {
            (requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).run {
                setPrimaryClip(ClipData.newPlainText("link",text))
                showToast(getString(R.string.copy_code_success))
            }
        } catch (e : Exception){
            showToast(getString(R.string.copy_code_fail))
        }

    }
}