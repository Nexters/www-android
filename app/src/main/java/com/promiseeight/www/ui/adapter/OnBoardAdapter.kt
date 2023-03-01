package com.promiseeight.www.ui.adapter

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.promiseeight.www.ui.common.BaseFragment

class OnBoardAdapter(
    list: ArrayList<BaseFragment<out ViewDataBinding>>,
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    private val fragmentList = list

    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int) = fragmentList[position]
}