package com.promiseeight.www.ui.meeting.info.date

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.databinding.FragmentDatePagerBinding
import com.promiseeight.www.domain.model.PromiseTime
import com.promiseeight.www.ui.adapter.TimeAdapter
import com.promiseeight.www.ui.common.BaseFragment
import com.promiseeight.www.ui.meeting.InfoViewModel
import com.promiseeight.www.ui.model.CandidateUiModel
import com.promiseeight.www.ui.model.TimeUiModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import timber.log.Timber

class DatePagerFragment : BaseFragment<FragmentDatePagerBinding>(){

    private val viewModel : InfoViewModel by viewModels({ requireParentFragment().requireParentFragment().requireParentFragment() })

    private val adapter : TimeAdapter by lazy {
        TimeAdapter(){
            viewModel.selectMeetingDateTime(it.id)
        }

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDatePagerBinding {
        return FragmentDatePagerBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("asdasd : ${viewModel.hashCode()}")
        initRecyclerView(binding.rvDatePager)
        initObserver()
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = GridLayoutManager(requireContext(),4,GridLayoutManager.VERTICAL,false)
        recyclerView.adapter = adapter
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.meetingDateTime.collectLatest {
                        val position = arguments?.getInt(FRAGMENT_POSITION) ?: 0
                        if(it.isNotEmpty()){
                            val list = mutableListOf<TimeUiModel>()
                            for(i in 0 .. 3){
                                list += listOf(it[position * 16 + (4 * i)],it[position * 16 + (4 * i) + 1],it[position* 16 + (4 * i) + 2],it[position* 16 + (4 * i) + 3])
                            }
                            adapter.submitList(list)
                        }
                    }
                }
            }
        }

    }

    companion object {
        const val FRAGMENT_POSITION = "position"
    }
}
