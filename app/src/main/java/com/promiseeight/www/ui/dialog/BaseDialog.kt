package com.promiseeight.www.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.promiseeight.www.R


abstract class BaseDialog<VB: ViewDataBinding>(@LayoutRes private val layoutId: Int): DialogFragment() {

    protected var _binding: VB? = null
    val binding: VB
        get() = _binding ?: throw IllegalStateException("binding fail")

    enum class ButtonType {
        ONE, TWO
    }

    class DialogBody(title : String, message : String) {
        lateinit var title : String
        lateinit var message : String
    }

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setStyle(STYLE_NO_TITLE, R.style.dialog_default)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstance: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setEvent()
    }

    open fun initView() {}
    open fun setEvent() {}
}