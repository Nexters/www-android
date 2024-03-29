package com.promiseeight.www.ui.meeting.detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentMeetingDetailBinding
import com.promiseeight.www.ui.adapter.RankAdapter
import com.promiseeight.www.ui.common.BaseFragment
import com.promiseeight.www.ui.model.DateRankUiModel
import com.promiseeight.www.ui.model.PlaceRankUiModel
import com.promiseeight.www.domain.model.MeetingStatus
import com.promiseeight.www.ui.common.util.SnackBarUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MeetingDetailFragment : BaseFragment<FragmentMeetingDetailBinding>() {

    private val viewModel: MeetingDetailViewModel by hiltNavGraphViewModels(R.id.main_navigation)

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
        viewModel.getMeetingDetailById(-1L)
        viewModel.setMeetingId((navArgs<MeetingDetailFragmentArgs>().value.meetingId).toLong())
        binding.viewModel = viewModel
        binding.let {
            initRecyclerViews(it.rvWhen, it.rvWhere)
            setStatusBarColor(R.color.www_green_transparent_20)
            it.btnVote.setOnClickListener {
                clickNextButton()
            }

            it.vShare.setOnClickListener {
                viewModel.meetingDetail.value?.let {
                    copyCode(it.meetingCode)
                }

            }

            it.tvWhen.setOnClickListener {
                findNavController().navigate(
                    MeetingDetailFragmentDirections.actionFragmentMeetingDetailToFragmentMeetingDetailRank(true)
                )
            }

            it.tvWhere.setOnClickListener {
                findNavController().navigate(
                    MeetingDetailFragmentDirections.actionFragmentMeetingDetailToFragmentMeetingDetailRank(false)
                )
            }

            it.ivParticipant.setOnClickListener {
                findNavController().navigate(
                    MeetingDetailFragmentDirections.actionFragmentMeetingDetailToMeetingDetailUsersFragment()
                )
            }

            it.tvWhenCount.setOnClickListener {
                findNavController().navigate(
                    MeetingDetailFragmentDirections.actionFragmentMeetingDetailToMeetingDetailVotingUsersFragment(
                        true
                    )
                )
            }

            it.tvWhereCount.setOnClickListener {
                findNavController().navigate(
                    MeetingDetailFragmentDirections.actionFragmentMeetingDetailToMeetingDetailVotingUsersFragment(
                        false
                    )
                )
            }
            it.ivBack.setOnClickListener {
                onClickBackIcon()
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
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.dateRanks.collectLatest {
                        if(it.size > 4){
                            dateRankAdapter.submitList(it.subList(0,4))
                        } else {
                            dateRankAdapter.submitList(it)
                        }

                    }
                }
                launch {
                    viewModel.placeRanks.collectLatest {
                        if(it.size > 4){
                            placeRankAdapter.submitList(it.subList(0,4))
                        } else {
                            placeRankAdapter.submitList(it)                        }
                    }
                }
                launch {
                    viewModel.meetingId.collectLatest {
                        if (it >= 0L) {
                            viewModel.getMeetingDetailById(it)
                        }
                    }
                }
                launch {
                    viewModel.meetingDetail.collectLatest {
                        if (it != null) {
                            viewModel.setDateRanks()
                            viewModel.setPlaceRanks()
                            updateButton()
                        }
                    }
                }
            }
        }
    }

    private fun updateButton() {
        viewModel.meetingDetail.value?.let {
            if (it.isHost) {
                when (it.meetingStatus) {
                    MeetingStatus.WAITING -> {
                        binding.vBottom.visibility = View.VISIBLE
                        binding.btnVote.text = "투표 시작하기"
                        binding.btnVote.isEnabled = true
                    }
                    MeetingStatus.VOTING -> {
                        binding.vBottom.visibility = View.VISIBLE
                        if (!it.userVoted) {
                            binding.btnVote.text = "투표 하러가기"
                            binding.btnVote.isEnabled = true
                        } else {
                            binding.btnVote.text = "투표 종료하기"
                            binding.btnVote.isEnabled = true
                        }
                    }
                    MeetingStatus.VOTED -> {
                        binding.btnVote.text = "약속 확정하기"
                        binding.btnVote.isEnabled = true
                        binding.vBottom.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.vBottom.visibility = View.GONE
                    }
                }


            } else if (it.isJoined) {
                when (it.meetingStatus) {
                    MeetingStatus.WAITING -> {
                        binding.vBottom.visibility = View.VISIBLE
                        binding.btnVote.text = "투표 하러가기"
                        binding.btnVote.isEnabled = false
                    }
                    MeetingStatus.VOTING -> {
                        if (!it.userVoted) {
                            binding.btnVote.isEnabled = true
                            binding.btnVote.text = "투표 하러가기"
                            binding.vBottom.visibility = View.VISIBLE
                        } else binding.vBottom.visibility = View.GONE
                    }
                    else -> {
                        binding.vBottom.visibility = View.GONE
                    }
                }
            }

        }
    }

    private fun clickNextButton() {
        viewModel.meetingDetail.value?.let { meetingDetail ->
            if (meetingDetail.isHost) {
                when (meetingDetail.meetingStatus) {
                    MeetingStatus.WAITING -> {
                        viewModel.changeMeetingStatus()
                    }
                    MeetingStatus.VOTING -> {
                        if (!meetingDetail.userVoted) { // 방장이 투표하기전이면 투표하는 화면으로 이동
                            findNavController().navigate(
                                MeetingDetailFragmentDirections.actionFragmentMeetingDetailToMeetingDetailVoteFragment()
                            )
                        } else { // 방장이 투표했으면 투표종료하기 버튼으로 종료할 수 있음
                            viewModel.changeMeetingStatus()
                        }
                    }
                    MeetingStatus.VOTED -> {
                        findNavController().navigate(
                            MeetingDetailFragmentDirections.actionFragmentMeetingDetailToMeetingDetailConfirmWhenFragment()
                        )
                    }
                    else -> {

                    }
                }


            } else if (meetingDetail.isJoined) {
                when (meetingDetail.meetingStatus) {
                    MeetingStatus.WAITING -> {
                        //버튼 막혀있음
                    }
                    MeetingStatus.VOTING -> {
                        if (!meetingDetail.userVoted)
                            findNavController().navigate(
                                MeetingDetailFragmentDirections.actionFragmentMeetingDetailToMeetingDetailVoteFragment()
                            )
                        else {
                            //버튼 막혀있음
                        }
                    }
                    else -> {

                    }
                }
            }
        }
    }

    private fun copyCode(text: String) {
        try {
            (requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).run {
                setPrimaryClip(ClipData.newPlainText("link", text))
                SnackBarUtil.showSnackBarSimple(
                    requireContext(),
                    binding.root,
                    getString(R.string.copy_code_success),
                    if(binding.btnVote.isVisible) binding.btnVote else null
                )
            }
        } catch (e: Exception) {
            SnackBarUtil.showSnackBarSimple(
                requireContext(),
                binding.root,
                getString(R.string.copy_code_fail),
                if(binding.btnVote.isVisible) binding.btnVote else null
            )

        }

    }

    private fun navigateToDetailConfirm() {
        viewModel.confirmDate("") // 초기화
        viewModel.confirmPlace("") // 초기화
        findNavController().navigate(
            MeetingDetailFragmentDirections.actionFragmentMeetingDetailToMeetingDetailConfirmWhenFragment()
        )
    }

    override fun onResume() {
        super.onResume()
        setStatusBarColor(R.color.www_green_transparent_20)
    }
}