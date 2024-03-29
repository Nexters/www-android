package com.promiseeight.www.ui.meeting.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentMeetingInfoUserNameBinding
import com.promiseeight.www.ui.common.InfoFragment
import com.promiseeight.www.ui.common.util.SnackBarUtil
import com.promiseeight.www.ui.meeting.InfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeetingInfoUserNameFragment : InfoFragment<FragmentMeetingInfoUserNameBinding>() {

    private val viewModel: InfoViewModel by viewModels({ getHostFragment() })

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoUserNameBinding {
        return FragmentMeetingInfoUserNameBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        viewModel.setPage(2)
    }

    override fun initView() {
        super.initView()
        binding.run {
            btnNext.setOnClickListener {
                hideKeyboardWithLayout(etInfoUserName.windowToken)
                setParentFragmentBranch(
                    onJoin = {
                        if(viewModel?.meetingNicknameList?.value?.contains(viewModel?.meetingUserName?.value) == false)
                            findNavController().navigate(
                                ACTION_JOIN_USER_NAME_TO_DATE
                            )
                        else {
                            SnackBarUtil.showSnackBarSimple(
                                requireContext(),
                                binding.root,
                                "동일한 닉네임이 존재해요",
                                binding.btnNext
                            )
                        }
                    },
                    onAdd = {
                        findNavController().navigate(
                            ACTION_ADD_USER_NAME_TO_CAPACITY
                        )
                    }
                )
            }

            ivClose.setOnClickListener {
                viewModel?.setMeetingUserNameEmpty()
            }
            showKeyboardWithEditText(etInfoUserName)
            root.setOnClickListener {
                hideKeyboardWithLayout(etInfoUserName.windowToken)
            }
        }
    }


}