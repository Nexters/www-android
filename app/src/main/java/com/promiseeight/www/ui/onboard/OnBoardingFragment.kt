package com.promiseeight.www.ui.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.promiseeight.www.ui.adapter.OnBoardAdapter
import com.promiseeight.www.databinding.FragmentOnBoardingBinding
import com.promiseeight.www.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>() {

    private val viewModel: OnBoardingViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnBoardingBinding {
        return FragmentOnBoardingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter = OnBoardAdapter(
            this
        )

        binding.viewpagerOnboard.adapter = adapter
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isFirst.collectLatest {
                        if(!it.getOrDefault(true)){
                            findNavController().navigate(
                                OnBoardingFragmentDirections.actionFragmentOnBoardingToFragmentHome()
                            )
                        }
                    }
                }
            }
        }
    }
}