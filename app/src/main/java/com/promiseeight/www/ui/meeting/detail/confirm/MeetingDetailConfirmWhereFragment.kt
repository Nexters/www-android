package com.promiseeight.www.ui.meeting.detail.confirm

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
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentMeetingDetailConfirmWhereBinding
import com.promiseeight.www.databinding.FragmentMeetingDetailRankBinding
import com.promiseeight.www.ui.adapter.RankAdapter
import com.promiseeight.www.ui.adapter.RankConfirmAdapter
import com.promiseeight.www.ui.common.BaseFragment
import com.promiseeight.www.ui.meeting.detail.MeetingDetailFragmentDirections
import com.promiseeight.www.ui.meeting.detail.MeetingDetailViewModel
import com.promiseeight.www.ui.model.PlaceRankUiModel
import com.promiseeight.www.ui.model.RankModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MeetingDetailConfirmWhereFragment : BaseFragment<FragmentMeetingDetailConfirmWhereBinding>() {

    private val viewModel : MeetingDetailViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    private val rankAdapter: RankConfirmAdapter<RankModel> by lazy {
        RankConfirmAdapter{
            viewModel.confirmPlace(it.id)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingDetailConfirmWhereBinding {
        return FragmentMeetingDetailConfirmWhereBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarColor(R.color.gray_50)
        binding.viewModel = viewModel

        binding.let {
            initRecyclerView(it.rvRank)

            it.btnConfirm.setOnClickListener {
                viewModel.confirmMeeting()
            }
            it.ivBack.setOnClickListener {
                onClickBackIcon()
            }
            it.tvRankCount.setOnClickListener {
                findNavController().navigate(
                    MeetingDetailConfirmWhereFragmentDirections.actionMeetingDetailConfirmWhereFragmentToMeetingDetailVotingUsersFragment(
                        false
                    )
                )
            }
        }

        initObserver()
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.run {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = rankAdapter
            itemAnimator = null
        }


    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.placeRanks.collectLatest {
                        rankAdapter.submitList(it)
                        binding.btnConfirm.isEnabled = it.any { it.confirmed }
                    }
                }
                launch {
                    viewModel.meetingDetail.collectLatest {
                        it?.let { meetingDetail ->
                            if(meetingDetail.confirmedDate != null && meetingDetail.confirmedDate.isNotBlank()){
                                findNavController().navigate(
                                    MeetingDetailConfirmWhereFragmentDirections.actionMeetingDetailConfirmWhereFragmentToFragmentMeetingDetail(meetingDetail.meetingId.toString())
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}