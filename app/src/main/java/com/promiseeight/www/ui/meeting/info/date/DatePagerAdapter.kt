package com.promiseeight.www.ui.meeting.info.date

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DatePagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return DatePagerFragment()
    }
}