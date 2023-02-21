package com.promiseeight.www.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.promiseeight.www.databinding.FragmentHomeTabPagerBinding
import com.promiseeight.www.ui.adapter.HomeMeetingAdapter
import com.promiseeight.www.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeTabPagerFragment : BaseFragment<FragmentHomeTabPagerBinding>() {

    private val viewModel : HomeViewModel by viewModels({ requireParentFragment() })

    private var homeMeetingAdapter : HomeMeetingAdapter? = null

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeTabPagerBinding {
        return FragmentHomeTabPagerBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeMeetingAdapter = HomeMeetingAdapter()

        binding.let {
            initViewPager(it.vpMeeting)
        }

        initObserver()
    }

    private fun initViewPager(viewPager: ViewPager2) {
        viewPager.adapter = homeMeetingAdapter
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    arguments?.getInt(TAB_POSITION)?.let { position -> // 개선할 여지가 많은 코드..
                        when(position){
                            0 -> {
                                viewModel.doingMeeting.collectLatest {
                                    homeMeetingAdapter?.submitList(it)
                                }
                            }
                            else -> {
                                viewModel.doneMeeting.collectLatest {
                                    homeMeetingAdapter?.submitList(it)
                                }
                            }
                        }
                    }

                }
            }
        }
    }
    companion object {
        const val TAB_POSITION = "tabPosition"
    }
}