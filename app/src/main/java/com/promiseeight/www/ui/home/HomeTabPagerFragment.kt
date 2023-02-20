package com.promiseeight.www.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.promiseeight.www.databinding.FragmentHomeTabPagerBinding
import com.promiseeight.www.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeTabPagerFragment : BaseFragment<FragmentHomeTabPagerBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeTabPagerBinding {
        return FragmentHomeTabPagerBinding.inflate(inflater,container,false)
    }
}