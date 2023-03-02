package com.promiseeight.www.ui.meeting.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentMeetingDetailRankBinding
import com.promiseeight.www.databinding.FragmentMeetingDetailVoteBinding
import com.promiseeight.www.ui.adapter.PlaceAdapter
import com.promiseeight.www.ui.common.BaseFragment
import com.promiseeight.www.ui.meeting.detail.confirm.MeetingDetailConfirmWhenFragmentDirections
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MeetingDetailVoteFragment : BaseFragment<FragmentMeetingDetailVoteBinding>() {

    private val viewModel : MeetingDetailViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    private val placeAdapter: PlaceAdapter by lazy {
        PlaceAdapter{
            if(!it.userVoted)
                viewModel.selectPlace(it.id)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingDetailVoteBinding {
        return FragmentMeetingDetailVoteBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setStatusBarColor(R.color.www_white)
        binding.let {
            initRecyclerView(it.rvRank)
            it.btnVote.setOnClickListener {
                viewModel.votePlaces()
            }
            it.ivBack.setOnClickListener {
                onClickBackIcon()
            }
        }

        initObserver()
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.run {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = placeAdapter
            itemAnimator = null
        }


    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.placeRanks.collectLatest {
                        placeAdapter.submitList(it)
                    }
                }
                launch {
                    viewModel.meetingDetail.collectLatest {
                        it?.let {
                            if(it.userVoted){
                                binding.btnVote.isEnabled = false
                                binding.btnVote.text = resources.getString(R.string.meeting_detail_voted)
                            }
                        }
                    }
                }
            }
        }
    }
}