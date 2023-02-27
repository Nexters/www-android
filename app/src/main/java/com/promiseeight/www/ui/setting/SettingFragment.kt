package com.promiseeight.www.ui.setting

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.promiseeight.www.BuildConfig
import com.promiseeight.www.databinding.FragmentSettingBinding
import com.promiseeight.www.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSettingAppVersion.text = getVersionInfo()
        binding.ivBack.setOnClickListener {
            findNavController().navigate(
                SettingFragmentDirections.actionFragmentSettingToFragmentHome()
            )
        }

    }

    private fun getVersionInfo() = BuildConfig.VERSION_NAME

    private fun switchToggleBtn() {
        binding.pushToggleSetting.setOnClickListener {
//            val builder = AlertDialog.Builder(this.parentFragmentManager.getFragment(SettingFragment))

        }

    }
}