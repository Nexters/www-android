package com.promiseeight.www.ui.common

import android.graphics.Color
import androidx.annotation.ColorInt
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import java.util.*

object CalendarUtil {
    fun getMonthList(now : DateTime) : List<DateTime> {
        val first = now.withDayOfMonth(1)
        val prevOffset = getPrevOffSet(first)
        val monthList = mutableListOf<DateTime>()
        for(i in 0 until now.dayOfMonth().maximumValue + prevOffset){
            monthList.add(first.minusDays(prevOffset).plusDays(i))
        }
        return monthList.toList()
    }

    private fun getPrevOffSet(dateTime: DateTime): Int {
        var prevMonthTailOffset = dateTime.dayOfWeek
        if (prevMonthTailOffset >= 7) prevMonthTailOffset %= 7
        return prevMonthTailOffset
    }

    fun getKoreanDayOfMonth(date : DateTime) : String {
        return date.dayOfWeek().getAsText(Locale.KOREAN).substring(0,1)
    }

    fun isPossibleDate(todayMonth: DateTime, targetDate: DateTime): Boolean
       = targetDate.isAfterNow || isSameDate(todayMonth,targetDate)

    @ColorInt
    fun getDateColor(@androidx.annotation.IntRange(from=1, to=7) dayOfWeek: Int): Int {
        return when (dayOfWeek) {
            /* 토요일은 파란색 */
            //DateTimeConstants.SATURDAY -> Color.parseColor("#2962FF")
            /* 일요일 빨간색 */
            DateTimeConstants.SUNDAY -> Color.parseColor("#D32F2F")
            /* 그 외 검정색 */
            else -> Color.parseColor("#000000")
        }
    }

    fun isSameDate(first: DateTime, second: DateTime): Boolean =
        first.year == second.year && first.monthOfYear == second.monthOfYear && first.dayOfMonth == second.dayOfMonth
}