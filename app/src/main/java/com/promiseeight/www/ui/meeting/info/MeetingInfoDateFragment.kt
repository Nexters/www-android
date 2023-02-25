package com.promiseeight.www.ui.meeting.info

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
import androidx.viewpager2.widget.ViewPager2
import com.promiseeight.www.databinding.FragmentMeetingInfoDateBinding
import com.promiseeight.www.ui.adapter.CandidateAdapter
import com.promiseeight.www.ui.adapter.ItemDecoration.InfoItemDecoration
import com.promiseeight.www.ui.common.InfoFragment
import com.promiseeight.www.ui.meeting.InfoViewModel
import com.promiseeight.www.ui.meeting.info.date.DatePagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class MeetingInfoDateFragment : InfoFragment<FragmentMeetingInfoDateBinding>() {

    private val viewModel: InfoViewModel by viewModels({ getHostFragment() })

    private val candidateAdapter: CandidateAdapter by lazy {
        CandidateAdapter(binding.rvSelectedDate){
            viewModel.removeMeetingDateCandidate(it.id)
        }
    }

    private var dateViewPagerAdapter : DatePagerAdapter? = null

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoDateBinding {
        return FragmentMeetingInfoDateBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        Timber.d("asdasd : ${viewModel.hashCode()}")

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
        binding.vpDate.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvFirstDay.text = getKoreanDate(position,0)
                binding.tvSecondDay.text = getKoreanDate(position,1)
                binding.tvThirdDay.text = getKoreanDate(position,2)
                binding.tvFourthDay.text = getKoreanDate(position,3)
            }
        })
        initObserver()
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.run {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = candidateAdapter
            removeItemDecorations(recyclerView) // 추가된 ItemDecoration이 있으면 삭제한다.
            addItemDecoration(InfoItemDecoration(requireContext())) // ItemDocoration을 추가한다.
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
                launch {
                        viewModel.meetingPeriodSize.collectLatest {
                            dateViewPagerAdapter = null
                            dateViewPagerAdapter = DatePagerAdapter(this@MeetingInfoDateFragment,  it )
                            binding.vpDate.adapter = dateViewPagerAdapter
                            binding.diDate.attachTo(binding.vpDate)
                        }
//                        viewModel.startDate.combine(viewModel.endDate){ start, end ->
//                            // 년이 바뀔 때 처리 필요
//                           // if (.isNotEmpty()){
//                                dateViewPagerAdapter = null
//                                dateViewPagerAdapter = DatePagerAdapter(this@MeetingInfoDateFragment,  (end.dayOfYear - start.dayOfYear + 1) / 4)
//                                binding.vpDate.adapter = dateViewPagerAdapter
//                           // }
//                    }
                }
            }
        }
    }

    private fun getKoreanDate(position: Int,offset : Int) : String {
        return viewModel.meetingDateTimeFromPeriod.value[position * 16 + offset].date.let {
            "${it.dayOfMonth}일 (${it.dayOfWeek().getAsText(Locale.KOREAN)[0]})"
        }
    }
}
