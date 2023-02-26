package com.promiseeight.www.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.promiseeight.www.databinding.FragmentMainFinishBinding
import com.promiseeight.www.databinding.FragmentMainOnGoingBinding
import com.promiseeight.www.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFinishFragment : BaseFragment<FragmentMainFinishBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainFinishBinding {
        return FragmentMainFinishBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}