package com.promiseeight.www.ui.meeting.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentMeetingInfoPlaceBinding
import com.promiseeight.www.ui.adapter.CandidateAdapter
import com.promiseeight.www.ui.common.AddNavHostFragment
import com.promiseeight.www.ui.common.BaseFragment
import com.promiseeight.www.ui.common.InfoFragment
import com.promiseeight.www.ui.common.JoinNavHostFragment
import com.promiseeight.www.ui.meeting.*
import com.promiseeight.www.ui.model.CandidateUiModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MeetingInfoPlaceFragment : InfoFragment<FragmentMeetingInfoPlaceBinding>() {

    private val viewModel : InfoViewModel by viewModels ({ getHostFragment() })

    private val candidateAdapter: CandidateAdapter by lazy {
        CandidateAdapter()
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
        binding.btnNext.setOnClickListener {
            setParentFragmentBranch(
                onJoin = {

                },
                onAdd = {
                    Navigation.findNavController(requireActivity(), R.id.fcv_main) // 인터페이스 만들어보기
                        .navigate(
                            AddMeetingFragmentDirections.actionFragmentAddMeetingToFragmentMeetingShare()
                        )
                }
            )
        }
        binding.ivAdd.setOnClickListener {
            if(viewModel.checkMeetingPlaceDuplicate()) {
                viewModel.addMeetingPlaceCandidate()
            } else {
                Toast.makeText(requireContext(),getString(R.string.info_place_duplicate),Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.run {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = candidateAdapter
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
            }
        }
    }
}