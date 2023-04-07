package com.promiseeight.www.ui.common.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.promiseeight.www.R
import com.promiseeight.www.databinding.LayoutSnackbarSimpleBinding

object SnackBarUtil {
    fun showSnackBarSimple(context : Context, view:View, message : String?, anchorView : View? = null){
        val binding : LayoutSnackbarSimpleBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_snackbar_simple,null, false)
        binding.tvTitle.text = message

        Snackbar.make(view,"",Snackbar.LENGTH_SHORT).apply {
            (this.view as Snackbar.SnackbarLayout).also {
                it.setPadding(18.dpToPx(context),0,18.dpToPx(context),0)
                it.setBackgroundColor(ContextCompat.getColor(context,R.color.transparent))
                it.addView(binding.root,0)
            }
            anchorView?.let {
                setAnchorView(it)
            }
        }.show()


    }
}