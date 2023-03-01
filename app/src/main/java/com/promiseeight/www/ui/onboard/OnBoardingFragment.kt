package com.promiseeight.www.ui.onboard

import OnBoard1Fragment
import OnBoard2Fragment
import OnBoard3Fragment
import OnBoard4Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.promiseeight.www.ui.adapter.OnBoardAdapter
import com.promiseeight.www.databinding.FragmentOnBoardingBinding
import com.promiseeight.www.ui.common.BaseFragment
import com.promiseeight.www.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnBoardingBinding {
        return FragmentOnBoardingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()

    }

    private fun setupViewPager() {
        val fragmentList = arrayListOf(
            OnBoard1Fragment.newInstance(),
            OnBoard2Fragment.newInstance(),
            OnBoard3Fragment.newInstance(),
            OnBoard4Fragment.newInstance()
        )

        val adapter = OnBoardAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewpagerOnboard.adapter = adapter
        binding.viewpagerOnboard.isUserInputEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}