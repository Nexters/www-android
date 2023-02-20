package com.promiseeight.www.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.promiseeight.www.ui.home.HomeTabPagerFragment

class HomeTabAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2 // 수정 필요
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> HomeTabPagerFragment()
            else -> HomeTabPagerFragment()
        }
    }
}