package com.promiseeight.www.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.withStyledAttributes
import com.promiseeight.www.R
import com.promiseeight.www.ui.model.enums.DateUiState

class DayTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes private val defStyleAttr: Int = R.attr.itemViewStyle
) : TextView(context, attrs){

    val paint = Paint().apply {
        color = Color.parseColor("#FF41E29E")
    }
    var date : String? = null
    init {
        /* Attributes */
        context.withStyledAttributes(attrs, R.styleable.CalendarView, defStyleAttr) {
            date = getString(R.styleable.CalendarView_dateState)

        }
    }

    override fun onDraw(canvas: Canvas?) {

        if(canvas == null) return
        val cx = width.toFloat() / 2f
        val cy = height.toFloat() / 2f
        if(date!=DateUiState.INITIAL.name && date!=null)
            canvas.drawCircle(cx,cy,cy*0.9f, paint)
        super.onDraw(canvas)

    }
}