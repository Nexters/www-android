package com.promiseeight.www.ui.meeting.info

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.databinding.FragmentMeetingInfoDateBinding
import com.promiseeight.www.ui.adapter.CandidateAdapter
import com.promiseeight.www.ui.common.InfoFragment
import com.promiseeight.www.ui.meeting.InfoViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MeetingInfoDateFragment : InfoFragment<FragmentMeetingInfoDateBinding>() {

    private val viewModel: InfoViewModel by viewModels({ getHostFragment() })

    private val candidateAdapter: CandidateAdapter by lazy {
        CandidateAdapter()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoDateBinding {
        return FragmentMeetingInfoDateBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        setParentFragmentBranch(
            { viewModel.setPage(3) },
            { viewModel.setPage(5) }
        )
    }

    override fun initView() {
        super.initView()
        binding.btnNext.setOnClickListener {
            setParentFragmentBranch(
                onJoin = {
                    findNavController().navigate(
                        ACTION_JOIN_DATE_TO_PLACE
                    )
                },
                onAdd = {
                    findNavController().navigate(
                        ACTION_ADD_DATE_TO_PLACE
                    )
                }
            )
        }

        initRecyclerView(binding.rvSelectedDate)
        initObserver()
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
                    viewModel.meetingDateCandidates.collectLatest { candidates ->
                        candidateAdapter.submitList(candidates)
                    }
                }
            }
        }
    }
}
