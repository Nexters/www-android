package com.promiseeight.www.ui.meeting.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentMeetingDetailRankBinding
import com.promiseeight.www.databinding.FragmentMeetingDetailUsersBinding
import com.promiseeight.www.ui.adapter.DetailUserAdapter
import com.promiseeight.www.ui.adapter.RankAdapter
import com.promiseeight.www.ui.common.BaseFragment
import com.promiseeight.www.ui.model.MeetingDetailUserUiModel
import com.promiseeight.www.ui.model.PlaceRankUiModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MeetingDetailUsersFragment : BaseFragment<FragmentMeetingDetailUsersBinding>() {

    private val viewModel : MeetingDetailViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    private val detailUserAdapter: DetailUserAdapter by lazy {
        DetailUserAdapter()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingDetailUsersBinding {
        return FragmentMeetingDetailUsersBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setStatusBarColor(R.color.www_white)
        binding.let {
            initRecyclerView(it.rvDetailUser)
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
            adapter = detailUserAdapter
        }


    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.meetingDetail.collectLatest {
                        it?.let { meetingDetail ->
                            detailUserAdapter.submitList(meetingDetail.joinedUserList)
                        }
                    }
                }
            }
        }
    }
}