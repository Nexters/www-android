package com.promiseeight.www.ui.meeting.info.date

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.promiseeight.www.ui.meeting.info.date.DatePagerFragment.Companion.FRAGMENT_POSITION

class DatePagerAdapter(fragment : Fragment,private val size : Int) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return DatePagerFragment().apply {
            arguments = Bundle().apply {
                putInt(FRAGMENT_POSITION,position)
            }
        }
    }
}