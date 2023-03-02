package com.promiseeight.www.ui.meeting.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.promiseeight.www.databinding.FragmentMeetingInfoCodeBinding
import com.promiseeight.www.ui.common.InfoFragment
import com.promiseeight.www.ui.meeting.InfoViewModel
import com.promiseeight.www.ui.model.enums.CodeStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MeetingInfoCodeFragment : InfoFragment<FragmentMeetingInfoCodeBinding>() {

    private val viewModel : InfoViewModel by viewModels ({ getHostFragment() })

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoCodeBinding {
        return FragmentMeetingInfoCodeBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        viewModel.setPage(1)
    }

    override fun initView() {
        super.initView()
        initObserver()
        binding.run {
            btnNext.setOnClickListener {
                viewModel?.checkCodeValid()
            }

            ivClose.setOnClickListener {
                viewModel?.setMeetingCodeEmpty()
            }
            showKeyboardWithEditText(etInfoCode)
            root.setOnClickListener {
                hideKeyboardWithLayout(etInfoCode.windowToken)
            }
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.meetingCode.collectLatest { code ->
                        if(code.length < viewModel.codeMaxSize) {
                            viewModel.setCodeStatus(CodeStatus.READY)
                            viewModel.meetingName.value = ""
                        }
                        else if(code.length == viewModel.codeMaxSize) viewModel.setCodeStatus(CodeStatus.ACTIVE)
                    }
                }
                launch {
                    viewModel.meetingCodeStatus.collectLatest {
                        when(it){
                            CodeStatus.SUCCESS -> {
                                viewModel.setCodeStatus(CodeStatus.READY)
                                findNavController().navigate(ACTION_JOIN_CODE_TO_USER_NAME)
                            }
                            else -> {
                                //
                            }
                        }
                    }
                }
            }
        }
    }
}