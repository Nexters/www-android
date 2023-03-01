package com.promiseeight.www.ui.setting

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.promiseeight.www.BuildConfig
import com.promiseeight.www.databinding.FragmentSettingBinding
import com.promiseeight.www.databinding.ItemCustomSnackbarEnterRoomBinding
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

        binding.tvSettingAppVersion.text = getVersionInfo();

        binding.tvSettingFeedback.setOnClickListener {
            UriParse("")
        }

        binding.tvSettingPrivateInfo.setOnClickListener {
            UriParse("https://whenwherewhat.notion.site/WWW-d44308e244964fb7a12006efe2eb94cb") //클릭시 개인정보방침
        }

    }

    private fun getVersionInfo() = BuildConfig.VERSION_NAME

    private fun switchToggleBtn() {
        binding.pushToggleSetting.setOnClickListener {
//            val builder = AlertDialog.Builder(this.parentFragmentManager.getFragment(SettingFragment))

        }
    }

    private fun UriParse(urlGet : String) {
        val url = urlGet
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

}