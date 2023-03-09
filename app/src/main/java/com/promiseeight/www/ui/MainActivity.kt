package com.promiseeight.www.ui

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.promiseeight.www.BuildConfig
import com.promiseeight.www.R
import com.promiseeight.www.databinding.ActivityMainBinding
import com.promiseeight.www.ui.common.util.DialogUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()

        viewModel.getRecentVersion()
    }

    fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recentVersion.collectLatest {
                    if(BuildConfig.VERSION_NAME != it){
                        showAppUpdateDialog()
                    }
                }
            }
        }
    }

    private fun showAppUpdateDialog() {
        DialogUtil.showBasicAlertDialog(
            this,
            getString(R.string.update_dialog),
            ok = {
                goToMarket()
            },
            okText = "이동하기",
            cancelText = "닫기",
            cancelable = false
        )
    }

    private fun goToMarket() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("market://details?id=" + packageName)
            })
            finish()
        } catch (e : Exception){
            Timber.e(e)
        }
    }
}
