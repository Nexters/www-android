package com.promiseeight.www.ui.meeting.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentMeetingDetailBinding
import com.promiseeight.www.domain.model.MeetingDetail
import com.promiseeight.www.ui.adapter.RankAdapter
import com.promiseeight.www.ui.common.BaseFragment
import com.promiseeight.www.ui.model.DateRankUiModel
import com.promiseeight.www.ui.model.MeetingDetailUiModel
import com.promiseeight.www.ui.model.PlaceRankUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MeetingDetailFragment : BaseFragment<FragmentMeetingDetailBinding>() {

    private val viewModel : MeetingDetailViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    private val dateRankAdapter: RankAdapter<DateRankUiModel> by lazy {
        RankAdapter()
    }

    private val placeRankAdapter: RankAdapter<PlaceRankUiModel> by lazy {
        RankAdapter()
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingDetailBinding {
        return FragmentMeetingDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setMeetingId((navArgs<MeetingDetailFragmentArgs>().value.meetingId).toLong())
        binding.viewModel = viewModel
        binding.let {
            initRecyclerViews(it.rvWhen,it.rvWhere)

            it.btnVote.setOnClickListener {
                findNavController().navigate(
                    //MeetingDetailFragmentDirections.actionFragmentMeetingDetailToFragmentMeetingDetailRank()
                    MeetingDetailFragmentDirections.actionFragmentMeetingDetailToMeetingDetailVoteFragment()
                )
            }

            it.vShare.setOnClickListener {
                navigateToDetailConfirm()
            }
        }
        setOnBtnNext()
        initObserver()
    }

    private fun initRecyclerViews(dateRecyclerView: RecyclerView, placeRecyclerView: RecyclerView) {
        dateRecyclerView.run {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = dateRankAdapter
        }

        placeRecyclerView.run {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = placeRankAdapter
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.dateRanks.collectLatest {
                        dateRankAdapter.submitList(it)
                    }
                }
                launch {
                    viewModel.placeRanks.collectLatest {
                        placeRankAdapter.submitList(it)
                    }
                }
                launch {
                    viewModel.meetingId.collectLatest {
                        if(it >= 0L){
                            viewModel.getMeetingDetailById(it)
                        }
                    }
                }
                launch {
                    viewModel.meetingDetail.collectLatest {
                        if(it != null){
                            viewModel.setDateRanks()
                            viewModel.setPlaceRanks()
                            updateButton()
                        }
                    }
                }
            }
        }
    }

    private fun updateButton(){
        viewModel.meetingDetail.value?.let{
            if(it.isHost){
                when(it.meetingStatus){
                    MeetingStatus.WAITING -> {
                        binding.btnVote.text = "투표 시작하기"
                        binding.btnVote.isEnabled = true
                    }
                    MeetingStatus.VOTING -> {
                     //   if () { 방장이 투표안했으면
                            binding.btnVote.text = "투표 하러가기"
                            binding.btnVote.isEnabled = true
                       // } else {
                        binding.btnVote.text = "투표 종료하기"
                        binding.btnVote.isEnabled = true
                        //}
                    }
                    MeetingStatus.VOTED -> {
                        binding.btnVote.text = "약속 확정하기"
                        binding.btnVote.isEnabled = true
                    }
                    else -> {
                        binding.vBottom.visibility = View.GONE
                    }
                }


            } else if(it.isJoined){
                when(it.meetingStatus){
                    MeetingStatus.WAITING -> {
                        binding.btnVote.text = "투표 하러가기"
                        binding.btnVote.isEnabled = false
                    }
                    MeetingStatus.VOTING -> {
                        binding.btnVote.isEnabled = true
                        binding.btnVote.text = "투표 하러가기"
                    }
                    else -> {
                        binding.vBottom.visibility = View.GONE
                    }
                }
            }

        }
    }

    private fun setOnBtnNext() {
        //viewModel
//        if(meetingDetail.isHost){
//            when(meetingDetail.meetingStatus){
//                MeetingStatus.WAITING -> {
//
//                }
//                MeetingStatus.VOTING -> {
//                    //   if () { 방장이 투표안했으면
//                    binding.btnVote.text = "투표 하러가기"
//                    binding.btnVote.isEnabled = true
//                    // } else {
//                    binding.btnVote.text = "투표 종료하기"
//                    binding.btnVote.isEnabled = true
//                    //}
//                }
//                MeetingStatus.VOTED -> {
//
//                }
//                else -> {
//
//                }
//            }
//
//
//        } else if(meetingDetail.isJoined){
//            when(meetingDetail.meetingStatus){
//                MeetingStatus.WAITING -> {
//
//                }
//                MeetingStatus.VOTING -> {
//
//                }
//                else -> {
//                    binding.vBottom.visibility = View.GONE
//                }
//            }
//        }
    }

    private fun navigateToDetailConfirm() {
        viewModel.confirmDate("") // 초기화
        viewModel.confirmPlace("") // 초기화
        findNavController().navigate(
            MeetingDetailFragmentDirections.actionFragmentMeetingDetailToMeetingDetailConfirmWhenFragment()
        )
    }
}