package com.promiseeight.www.ui.adapter

import OnBoardFirstFragment
import OnBoardSecondFragment
import OnBoardThirdFragment
import OnBoardFourthFragment
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = 4

    override fun createFragment(position: Int) : Fragment {
        return when(position){
            0 -> OnBoardFirstFragment()
            1 -> OnBoardSecondFragment()
            2 -> OnBoardThirdFragment()
            else -> OnBoardFourthFragment()
        }
    }
}