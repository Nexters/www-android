package com.promiseeight.www.ui.common

import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback

class CalendarPageChangeCallback(
    private val onCalendarPageSelected : (Int) -> Unit
) : OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        onCalendarPageSelected(position)
    }
}