package com.promiseeight.www.ui.common.util

import android.graphics.Color
import androidx.annotation.ColorInt
import com.promiseeight.www.ui.model.CalendarUiModel
import com.promiseeight.www.ui.model.enums.DateUiState
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants


object CalendarUtil {
    val calendarList = getNowCalendarList()
    var index = 0L

    fun getNowCalendarList() : List<CalendarUiModel> {
        val now = DateTime.now()
        val calendarList = mutableListOf<CalendarUiModel>()
        for(i in 0 .. 11){
            calendarList.add(CalendarUiModel(
                index++,
                "${now.plusMonths(i).year}.${now.plusMonths(i).monthOfYear}",
                true,
                now.plusMonths(i)
            ))
            getMonthList(now.plusMonths(i)).map { dateTime ->
                calendarList.add(CalendarUiModel(
                    index++,
                    dateTime.dayOfMonth.toString(),
                    false,
                    dateTime,
                    DateUiState.INITIAL,
                    dateTime.monthOfYear == now.plusMonths(i).monthOfYear
                ))
            }
        }
        return calendarList
    }

    fun getMonthList(now : DateTime) : List<DateTime> {
        val first = now.withDayOfMonth(1)
        val last = now.withDayOfMonth(now.dayOfMonth().maximumValue)
        val prevOffset = getPrevOffset(first)
        val nextOffset = getNextOffset(last)
        val monthList = mutableListOf<DateTime>()
        for(i in 0 until now.dayOfMonth().maximumValue + prevOffset + nextOffset){
            monthList.add(first.minusDays(prevOffset).plusDays(i))
        }
        return monthList.toList()
    }

    private fun getPrevOffset(dateTime: DateTime): Int {
        var prevMonthTailOffset = dateTime.dayOfWeek
        if (prevMonthTailOffset >= 7) prevMonthTailOffset %= 7
        return prevMonthTailOffset
    }

    private fun getNextOffset(dateTime: DateTime): Int {
        var nextMonthTailOffset = dateTime.dayOfWeek
        if (nextMonthTailOffset >= 7) nextMonthTailOffset %= 7
        return 6 - nextMonthTailOffset
    }

    fun isSameDate(first: DateTime, second: DateTime): Boolean =
        first.year == second.year && first.monthOfYear == second.monthOfYear && first.dayOfMonth == second.dayOfMonth

    fun isSelectedSaturdayStart(startDateTime : DateTime?, dateTime : DateTime) : Boolean {
        return startDateTime?.dayOfWeek == 6 || dateTime.dayOfMonth().withMaximumValue().dayOfMonth == dateTime.dayOfMonth
    }

    fun isSelectedSaturdayStartFirstDate(startDateTime : DateTime?, dateTime : DateTime) : Boolean {
        return startDateTime?.dayOfWeek == 6 && dateTime.dayOfMonth().withMinimumValue().dayOfMonth == dateTime.dayOfMonth
    }

    fun isSelectedSundayEnd(endDateTime : DateTime?, dateTime : DateTime) : Boolean {
        return endDateTime?.dayOfWeek == 7 || dateTime.dayOfMonth().withMinimumValue().dayOfMonth == dateTime.dayOfMonth
    }

    fun isSelectedSundayEndLastDate(endDateTime : DateTime?, dateTime : DateTime) : Boolean {
        return endDateTime?.dayOfWeek == 7 && dateTime.dayOfMonth().withMaximumValue().dayOfMonth == dateTime.dayOfMonth
    }

    fun isPassStart(dateTime : DateTime) : Boolean{
        return dateTime.dayOfWeek == 7 || dateTime.dayOfMonth().withMinimumValue().dayOfMonth == dateTime.dayOfMonth
    }

    fun isPassStartLastDate(dateTime : DateTime) : Boolean{
        return dateTime.dayOfWeek == 7 && dateTime.dayOfMonth().withMaximumValue().dayOfMonth == dateTime.dayOfMonth
    }

    fun isPassEnd(dateTime : DateTime) : Boolean{
        return dateTime.dayOfWeek == 6  || dateTime.dayOfMonth().withMaximumValue().dayOfMonth == dateTime.dayOfMonth
    }

    fun isPassEndFirstDate(dateTime : DateTime) : Boolean{
        return dateTime.dayOfWeek == 6  && dateTime.dayOfMonth().withMinimumValue().dayOfMonth == dateTime.dayOfMonth
    }

    fun isInStartTimeAndEndTime(startDateTime: DateTime, endDateTime: DateTime, dateTime: DateTime) : Boolean {
        return dateTime.millis in startDateTime.millis..endDateTime.millis
    }
}
