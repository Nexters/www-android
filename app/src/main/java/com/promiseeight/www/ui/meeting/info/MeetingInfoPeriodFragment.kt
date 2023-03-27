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
import androidx.recyclerview.widget.GridLayoutManager
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentMeetingInfoPeriodBinding
import com.promiseeight.www.ui.adapter.CalendarAdapter
import com.promiseeight.www.ui.common.InfoFragment
import com.promiseeight.www.ui.common.util.CalendarUtil
import com.promiseeight.www.ui.meeting.InfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.promiseeight.www.ui.model.CalendarUiModel
import com.promiseeight.www.ui.model.enums.InfoMessage
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import java.util.*

@AndroidEntryPoint
class MeetingInfoPeriodFragment : InfoFragment<FragmentMeetingInfoPeriodBinding>() {

    private val viewModel: InfoViewModel by viewModels({ getHostFragment() })

    private val calendarAdapter: CalendarAdapter by lazy {
        CalendarAdapter {
            if (it.isCurrentMonth == true && (it.dateTime.dayOfYear == DateTime.now().dayOfYear || it.dateTime.isAfterNow))
                viewModel.selectDate(it)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeetingInfoPeriodBinding {
        return FragmentMeetingInfoPeriodBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        initObserver()
        viewModel.setPage(4)
    }

    override fun initView() {
        super.initView()
        binding.run {
            btnNext.setOnClickListener {
                findNavController().navigate(ACTION_ADD_PERIOD_TO_DATE)
            }
            rvCalendar.itemAnimator = null
            //calendarAdapter.setHasStableIds(true)
            rvCalendar.adapter = calendarAdapter
            rvCalendar.layoutManager = GridLayoutManager(requireContext(), 7).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (CalendarUtil.calendarList[position].isMonth) 7 else 1
                    }
                }
                isItemPrefetchEnabled = true
                initialPrefetchItemCount = 500
            }
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.meetingInitialPeriod.collectLatest {
                        calendarAdapter.submitList(it)
                    }
                }
                launch {
                    viewModel.meetingPeriodState.collectLatest {
                        viewModel.updatePeriod()
                        binding.tvSubtitle.text=
                            if(it.meetingPeriodStart == null && it.meetingPeriodEnd == null)
                                getString(R.string.info_period_subtitle)
                            else {
                                getString(R.string.info_period_subtitle_selected,
                                    it.meetingPeriodStart?.dateTime?.monthOfYear,
                                    it.meetingPeriodStart?.dateTime?.dayOfMonth,
                                    it.meetingPeriodStart?.dateTime?.dayOfWeek()?.getAsText(Locale.KOREAN)?.substring(0,1)
                                    )+ " " + getString(R.string.minus) + " " +
                                     if(it.meetingPeriodEnd?.dateTime != null) getString(R.string.info_period_subtitle_selected,
                                        it.meetingPeriodEnd.dateTime.monthOfYear,
                                        it.meetingPeriodEnd.dateTime.dayOfMonth,
                                        it.meetingPeriodEnd.dateTime.dayOfWeek().getAsText(Locale.KOREAN)?.substring(0,1)
                                    ) else getString(R.string.empty_text)
                            }
                        binding.btnNext.isEnabled = it.meetingPeriodStart != null && it.meetingPeriodEnd != null
                    }
                }
                launch {
                    viewModel.infoMessage.collectLatest {
                        when(it){
                            InfoMessage.PeriodWarning14 -> {
                                showToast(getString(R.string.info_period_warning_14))
                                viewModel.setInfoMessageEmpty()
                            }
                            InfoMessage.PeriodWarningEndStart -> {
                                showToast(getString(R.string.info_period_warning_end_start))
                                viewModel.setInfoMessageEmpty()
                            }
                            else -> {
                                //
                            }
                        }
                    }
                }
            }
        }
    }
}

