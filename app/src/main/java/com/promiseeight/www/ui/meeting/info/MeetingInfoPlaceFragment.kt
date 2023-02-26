package com.promiseeight.www.ui.meeting.info

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
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentMeetingInfoPlaceBinding
import com.promiseeight.www.ui.adapter.CandidateAdapter
import com.promiseeight.www.ui.adapter.ItemDecoration.InfoItemDecoration
import com.promiseeight.www.ui.common.InfoFragment
import com.promiseeight.www.ui.meeting.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MeetingInfoPlaceFragment : InfoFragment<FragmentMeetingInfoPlaceBinding>() {

    private val viewModel : InfoViewModel by viewModels ({ getHostFragment() })

    private val candidateAdapter: CandidateAdapter by lazy {
        CandidateAdapter(binding.rvPlace) { candidate ->
            viewModel.removeMeetingPlaceCandidate(candidate.title)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoPlaceBinding {
        return FragmentMeetingInfoPlaceBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        setParentFragmentBranch(
            { viewModel.setPage(4) },
            { viewModel.setPage(6) }
        )
    }

    override fun initView() {
        super.initView()
        initRecyclerView(binding.rvPlace)
        initObserver()
        binding.run {
            btnNext.setOnClickListener {
                setParentFragmentBranch(
                    onJoin = {
                        viewModel?.joinMeeting()
                    },
                    onAdd = {
                        viewModel?.createMeeting()
                    }
                )
            }
            ivAdd.setOnClickListener {
                if(viewModel?.checkMeetingPlaceDuplicate() == true) {
                    viewModel?.addMeetingPlaceCandidate()
                } else {
                    Toast.makeText(requireContext(),getString(R.string.info_place_duplicate),Toast.LENGTH_SHORT).show()
                }
            }
            showKeyboardWithEditText(etInfoPlace)
            root.setOnClickListener {
                hideKeyboardWithLayout(etInfoPlace.windowToken)
            }
        }

    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.run {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = candidateAdapter
            removeItemDecorations(recyclerView) // 추가된 ItemDecoration이 있으면 삭제한다.
            addItemDecoration(InfoItemDecoration(requireContext())) // ItemDocoration을 추가한다.
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.meetingPlaces.collectLatest { candidates ->
                        candidateAdapter.submitList(candidates)
                    }
                }
                launch {
                    viewModel.meetingInvitation.collectLatest {
                        if(it != null)  Navigation.findNavController(requireActivity(), R.id.fcv_main) // 인터페이스 만들어보기
                            .navigate(AddMeetingFragmentDirections.actionFragmentAddMeetingToFragmentMeetingShare(
                                 it.code,it.shortLink
                            ))
                    }
                }
                launch {
                    viewModel.meetingJoinState.collectLatest {
                        if(it) Navigation.findNavController(requireActivity(),R.id.fcv_main)
                            .navigate(Uri.parse("https://www/meeting/detail/${viewModel.getMeetingId()}"),NavOptions.Builder().apply {
                                setPopUpTo(R.id.fragment_home,false)
                            }.build())
                    }
                }
                launch {
                    viewModel.infoMessage.collectLatest {
                        if(it.isNotBlank()){
                            showToast(it)
                            viewModel.setInfoMessageEmpty()
                        }
                    }
                }
            }
        }
    }
}