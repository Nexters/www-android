package com.promiseeight.www.ui.meeting.info.date

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.databinding.FragmentDatePagerBinding
import com.promiseeight.www.ui.adapter.TimeAdapter
import com.promiseeight.www.ui.common.BaseFragment

class DatePagerFragment : BaseFragment<FragmentDatePagerBinding>(){

    private val adapter : TimeAdapter by lazy {
        TimeAdapter()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDatePagerBinding {
        return FragmentDatePagerBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(binding.rvDatePager)
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = GridLayoutManager(requireContext(),4,GridLayoutManager.VERTICAL,false)
        recyclerView.adapter = adapter
    }

}
