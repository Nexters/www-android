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
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentMeetingDetailRankBinding
import com.promiseeight.www.ui.adapter.RankAdapter
import com.promiseeight.www.ui.common.BaseFragment
import com.promiseeight.www.ui.model.DateRankUiModel
import com.promiseeight.www.ui.model.PlaceRankUiModel
import com.promiseeight.www.ui.model.RankModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MeetingDetailRankFragment : BaseFragment<FragmentMeetingDetailRankBinding>() {

    private val viewModel : MeetingDetailViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    private val rankAdapter: RankAdapter<RankModel> by lazy {
        RankAdapter()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingDetailRankBinding {
        return FragmentMeetingDetailRankBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setStatusBarColor(R.color.www_white)

        binding.let {
            initRecyclerView(it.rvRank)
            it.ivBack.setOnClickListener {
                onClickBackIcon()
            }

            binding.tvTitle.text =
                if (navArgs<MeetingDetailVotingUsersFragmentArgs>().value.isWhen) resources.getString(R.string.meeting_detail_when)
                else resources.getString(R.string.meeting_detail_where)

        }

        initObserver()
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.run {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = rankAdapter
        }


    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    if (navArgs<MeetingDetailVotingUsersFragmentArgs>().value.isWhen){
                        viewModel.dateRanks.collectLatest {
                            rankAdapter.submitList(it)
                        }
                    }else {
                        viewModel.placeRanks.collectLatest {
                            rankAdapter.submitList(it)
                        }
                    }
                }
            }
        }
    }
}