package com.promiseeight.www.data.model.response

import com.google.gson.annotations.SerializedName
import com.promiseeight.www.domain.model.PromiseTime
import com.promiseeight.www.domain.model.UserPromiseTime
import com.promiseeight.www.ui.model.enums.DayOfWeekKorean
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.util.*

data class UserPromiseTimeResponse(
    @SerializedName("promiseDate")
    val promiseDate : String,
    @SerializedName("promiseDayOfWeek")
    val promiseDayOfWeek : String,
    @SerializedName("promiseTime")
    val promiseTime : String,
    @SerializedName("userInfoList")
    val userInfoList : List<UserResponse>,
    @SerializedName("timetableId")
    val timetableId : Long
)

fun UserPromiseTimeResponse.toUserPromiseTime() = UserPromiseTime(
    promiseDate = getPromiseDateFormatted(promiseDate),
    promiseDayOfWeek = promiseDayOfWeek,
    promiseTime = PromiseTime.valueOf(promiseTime),
    userInfoList = userInfoList.map {
        it.toUser()
    },
    timetableId = timetableId
)

fun getPromiseDateFormatted(promiseDate: String?) : String{
    return if(promiseDate != null){
        val dateTime =  DateTime.parse(promiseDate)
        return dateTime.toString(DateTimeFormat.forPattern("yy.MM.dd"))
    }
    else ""
}


fun getDayOfWeekKorean(promiseDayOfWeek : String) : String {
    return when(DayOfWeekKorean.valueOf(promiseDayOfWeek)){
        DayOfWeekKorean.WED -> "수"
        DayOfWeekKorean.MON -> "월"
        DayOfWeekKorean.FRI -> "금"
        DayOfWeekKorean.TUE -> "화"
        DayOfWeekKorean.THU -> "목"
        DayOfWeekKorean.SAT -> "토"
        else -> "일"
    }
}
