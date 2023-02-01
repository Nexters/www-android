package com.promiseeight.www.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.promiseeight.www.databinding.FragmentHomeBinding
import com.promiseeight.www.ui.common.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater,container,false)
    }
}