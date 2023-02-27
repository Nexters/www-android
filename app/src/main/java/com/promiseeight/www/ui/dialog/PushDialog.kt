package com.promiseeight.www.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.promiseeight.www.R
import com.promiseeight.www.databinding.FragmentHomeBinding
import com.promiseeight.www.databinding.FragmentPushDialogBinding
import java.math.BigInteger.TWO

//class PushDialog: BaseDialog<FragmentPushDialogBinding>(R.layout.fragment_push_dialog){
//
//    private val buttonType: ButtonType,
//    private val content:DialogBody,
//    private var onCancelListener : (()->Unit)? = null,
//    private var onConfirmListener : (()->Unit)? = null
//
//    override fun getFragmentBinding(
//        inflater: LayoutInflater,
//        container: ViewGroup?
//    ): FragmentPushDialogBinding {
//        return FragmentPushDialogBinding.inflate(inflater,container,false)
//    }
//
//    override fun initView() {
//        super.initView()
//
//        //Button Type
//        when(buttonType) {
//            ButtonType.ONE -> {
//                binding.layoutBottomTypeOne.visibility.
//            }
//            ButtonType.TWO -> {
//                binding.layoutBottomTypeTwo.visible()
//            }
//        }
//
//        content.run {
//            binding.
//        }
//    }
//
//    override fun setEvent() {
//        super.setEvent()
//        binding.btnCancleTypeTwo.setOnClickListener {
//            onCancelListener?.invoke()
//            dismiss()
//        }
//        binding.btnCompleteTypeTwo.setOnClickListener {
//            onConfirmListener?.invoke()
//            dismiss()
//        }
//        binding.btnCompleteTypeOne.setOnClickListener {
//            onConfirmListener?.invoke()
//            dismiss()
//        }
//    }
//
//    fun visible() {
//
//    }
//
//}