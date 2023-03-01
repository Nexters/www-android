package com.promiseeight.www.ui.model

import android.icu.number.IntegerWidth
import android.os.Build
import androidx.annotation.RequiresApi
import com.promiseeight.www.data.model.response.getPromiseDateFormatted
import com.promiseeight.www.domain.model.MeetingMain
import com.promiseeight.www.domain.model.MeetingStatus
import com.promiseeight.www.domain.model.PromiseTime
import com.promiseeight.www.ui.model.enums.MeetingYaksogi
import org.joda.time.DateTime
import org.joda.time.Period
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class MeetingUiModel(
    val hostName : String,
    val joinedUserCount : Int,
    val meetingId : Long, // pk
    val meetingName : String,
    val meetingStatus : MeetingStatus,
    val minimumAlertMembers : Long,
    val confirmedDate : String?,
    val confirmedPlace : String?,
    val confirmedTime : String?,
    val votingUserCount : Int,
    val yaksogi: MeetingYaksogi,
    val dDay : String = "",
    val confirmedDayOfWeek :  String?
)

fun MeetingMain.toMeetingUiModel() = MeetingUiModel(
    hostName = hostName,
    joinedUserCount = joinedUserCount,
    meetingId = meetingId,
    meetingName,
    meetingStatus = MeetingStatus.valueOf(meetingStatus)
    , minimumAlertMembers,
    confirmedDate = getPromiseDateFormatted(confirmedDate), confirmedPlace,
    confirmedTime = if(confirmedTime!=null) PromiseTime.valueOf(confirmedTime).korean else "",
    votingUserCount,
    yaksogi = MeetingYaksogi.valueOf(yaksokiType),
    dDay = getDday(confirmedDate),
    confirmedDayOfWeek
)

fun getDday(confirmedDate: String?) : String {
    if(confirmedDate == null) return ""
    val targetDate = DateTime.parse(confirmedDate).withTime(0,0,0,0)
    val nowDate = DateTime.now().withTime(0,0,0,0)
    val period = Period(targetDate,nowDate).days
    return if(period < 0) period.toString()
            else if(period == 0) "-Day"
            else "+$period"

}