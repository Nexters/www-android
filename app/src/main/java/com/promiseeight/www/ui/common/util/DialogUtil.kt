package com.promiseeight.www.ui.common.util

import android.content.Context
import androidx.appcompat.app.AlertDialog

object DialogUtil {

    //단순하게 title, content, ok, cancel 만 있는 다이얼로그를 부르는 함수
    fun showBasicAlertDialog(
        context : Context,
        title : String = "",
        content : String = "",
        ok : () -> Unit = {},
        okText : String = "확인",
        cancel : () -> Unit = {},
        cancelText : String = "취소",
        cancelable : Boolean = true
    ){
        AlertDialog.Builder(context).apply {
            setTitle(title)
            if(content.isNotBlank()) setMessage(content)
            setPositiveButton(okText){ _, _ ->
                    ok()
                }
            setNegativeButton(cancelText){ _, _ ->
                    cancel()
                }
            setCancelable(cancelable)
        }.show()
    }
}