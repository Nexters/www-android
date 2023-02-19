package com.promiseeight.www.ui.common.util

import android.content.Context
import kotlin.math.roundToInt

fun Int.dpToPx(context : Context): Int {
    val density = context.resources.displayMetrics.density
    return (this * density).roundToInt()
}