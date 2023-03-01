package com.promiseeight.www.ui.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentHomeTabPagerBinding
import com.promiseeight.www.ui.adapter.HomeMeetingAdapter
import com.promiseeight.www.ui.common.BaseFragment
import com.promiseeight.www.ui.model.MeetingUiModel
import com.promiseeight.www.ui.model.toMeetingUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeTabPagerFragment : BaseFragment<FragmentHomeTabPagerBinding>() {

    private val viewModel: HomeViewModel by viewModels({ requireParentFragment() })

    private var homeMeetingAdapter: HomeMeetingAdapter? = null

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeTabPagerBinding {
        return FragmentHomeTabPagerBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeMeetingAdapter = HomeMeetingAdapter { meeting ->
            navigateToMeetingDetail(meeting)
        }

        binding.let {
            initViewPager(it.vpMeeting)
        }

        initObserver()
    }

    private fun initViewPager(viewPager: ViewPager2) {
        viewPager.adapter = homeMeetingAdapter
        viewPager.offscreenPageLimit = 3

//        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.size_43_5)
//        val pagerWidth = resources.displayMetrics.widthPixels - resources.getDimensionPixelOffset(R.dimen.size_43_5)
//        val screenWidth = resources.displayMetrics.widthPixels
//        val offsetPx = screenWidth - pageMarginPx  - pagerWidth
//
        viewPager.setPageTransformer { page, position ->
            page.translationX = position * -resources.getDimensionPixelOffset(R.dimen.size_60)
        }

    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.meetingMainList.collectLatest { it ->
                        Timber.d("asdasd ${it?.meetingIngList?.get(0)?.confirmedDate == null}")
                        it?.let { meetingMainList ->
                            arguments?.getInt(TAB_POSITION)?.let { position -> // 개선할 여지가 많은 코드..
                                when (position) {
                                    0 -> {
                                        if(meetingMainList.meetingIngList.isNotEmpty()){
                                            homeMeetingAdapter?.submitList(meetingMainList.meetingIngList)
                                            binding.tvEmpty.run {
                                                visibility = View.INVISIBLE
                                            }
                                        } else {
                                            binding.tvEmpty.run {
                                                text = resources.getString(R.string.tv_ongoing_meeting_empty)
                                                visibility = View.VISIBLE
                                            }
                                        }

                                    }

                                    else -> {
                                        if(meetingMainList.meetingEndList.isNotEmpty()) {
                                            homeMeetingAdapter?.submitList(meetingMainList.meetingEndList)
                                            binding.tvEmpty.run {
                                                visibility = View.INVISIBLE
                                            }
                                        }
                                        else {
                                            binding.tvEmpty.run {
                                                text = resources.getString(R.string.tv_done_meeting_empty)
                                                visibility = View.VISIBLE
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    private fun navigateToMeetingDetail(meetingUiModel: MeetingUiModel) {
        requireParentFragment().findNavController().navigate(
            Uri.parse("https://www/meeting/detail/${meetingUiModel.meetingId}"),
            NavOptions.Builder().apply {
                setPopUpTo(R.id.fragment_home,false)
            }.build()
        )
    }

    companion object {
        const val TAB_POSITION = "tabPosition"
    }
}
