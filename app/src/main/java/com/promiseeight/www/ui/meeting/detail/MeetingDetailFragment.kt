package com.promiseeight.www.ui.meeting.detail

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
import com.promiseeight.www.databinding.FragmentMeetingDetailBinding
import com.promiseeight.www.ui.adapter.RankAdapter
import com.promiseeight.www.ui.common.BaseFragment
import com.promiseeight.www.ui.model.DateRankUiModel
import com.promiseeight.www.ui.model.PlaceRankUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MeetingDetailFragment : BaseFragment<FragmentMeetingDetailBinding>() {

    private val viewModel : MeetingDetailViewModel by viewModels()

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

        binding.let {
            initRecyclerViews(it.rvWhen,it.rvWhere)

            it.btnVote.setOnClickListener {
                findNavController().navigate(
                    //MeetingDetailFragmentDirections.actionFragmentMeetingDetailToFragmentMeetingDetailRank()
                    MeetingDetailFragmentDirections.actionFragmentMeetingDetailToMeetingDetailVoteFragment()
                )
            }
        }

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
            }
        }
    }
}