package com.promiseeight.www.ui.meeting.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentMeetingDetailUsersBinding
import com.promiseeight.www.databinding.FragmentMeetingDetailVoteBinding
import com.promiseeight.www.databinding.FragmentMeetingDetailVotingUsersBinding
import com.promiseeight.www.ui.adapter.DetailUserAdapter
import com.promiseeight.www.ui.common.BaseFragment
import com.promiseeight.www.ui.common.util.CalendarUtil
import com.promiseeight.www.ui.model.MeetingDetailUserUiModel
import com.promiseeight.www.ui.model.enums.CharacterType
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MeetingDetailVotingUsersFragment : BaseFragment<FragmentMeetingDetailVotingUsersBinding>() {

    private val viewModel: MeetingDetailViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    private val detailUserAdapter: DetailUserAdapter by lazy {
        DetailUserAdapter()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingDetailVotingUsersBinding {
        return FragmentMeetingDetailVotingUsersBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setStatusBarColor(R.color.gray_50)
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
            adapter = detailUserAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun initLayoutManager() {
        binding.rvDetailUser.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (detailUserAdapter.currentList[position].isHeader) 2 else 1
                    }
                }
            }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.meetingDetail.collectLatest {
                        it?.let { meetingDetail ->
                            val list = mutableListOf<MeetingDetailUserUiModel>()
                            if (navArgs<MeetingDetailVotingUsersFragmentArgs>().value.isWhen) {
                                meetingDetail.userPromiseDateTimeList.forEachIndexed { index, userPromiseTime ->
                                    list.add(
                                        MeetingDetailUserUiModel(
                                            id = userPromiseTime.timetableId.toString(),
                                            isHeader = true,
                                            headerTitle = "${userPromiseTime.promiseDate} ${userPromiseTime.promiseTime.korean}:",
                                            rank = index + 1,
                                            count = userPromiseTime.userInfoList.size,
                                        )
                                    )
                                    userPromiseTime.userInfoList.forEach {
                                        list.add(
                                            MeetingDetailUserUiModel(
                                                id = "${userPromiseTime.promiseDate} ${userPromiseTime.promiseTime.korean}" + it.joinedUserName,
                                                userName = it.joinedUserName,
                                                characterType = CharacterType.valueOf(it.characterType),
                                                isHost = CharacterType.CREATOR == CharacterType.valueOf(
                                                    it.characterType
                                                ),
                                                isHeader = false
                                            )
                                        )
                                    }
                                }
                            }else {
                                meetingDetail.userPromisePlaceList?.forEachIndexed { index, userPlaceList ->
                                    list.add(
                                        MeetingDetailUserUiModel(
                                            id = userPlaceList.placeId.toString(),
                                            isHeader = true,
                                            headerTitle = "${userPlaceList.promisePlace}:",
                                            rank = index + 1,
                                            count = userPlaceList.userInfoList.size,
                                        )
                                    )
                                    userPlaceList.userInfoList.forEach {
                                        list.add(
                                            MeetingDetailUserUiModel(
                                                id = userPlaceList.promisePlace + it.joinedUserName,
                                                userName = it.joinedUserName,
                                                characterType = CharacterType.valueOf(it.characterType),
                                                isHost = CharacterType.CREATOR == CharacterType.valueOf(
                                                    it.characterType
                                                ),
                                                isHeader = false
                                            )
                                        )
                                    }
                                }
                            }
                            detailUserAdapter.submitList(list)
                            initLayoutManager()
                        }
                    }
                }
            }
        }
    }
}