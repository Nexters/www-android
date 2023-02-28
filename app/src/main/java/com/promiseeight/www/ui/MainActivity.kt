package com.promiseeight.www.ui

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.promiseeight.www.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var _window : Window

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        statusBar()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


    private fun statusBar() {
        _window = getWindow();
        _window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        _window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        _window.setStatusBarColor(Color.parseColor("#F5F5F5"));

    }
    override fun onDestroy() {
        super.onDestroy()
    }

  }
