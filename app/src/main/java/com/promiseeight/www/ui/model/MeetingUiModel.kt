package com.promiseeight.www.ui.model

import android.icu.number.IntegerWidth
import android.os.Build
import androidx.annotation.RequiresApi
import com.promiseeight.www.domain.model.MeetingMain
import com.promiseeight.www.domain.model.MeetingStatus
import com.promiseeight.www.ui.model.enums.MeetingYaksogi
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
    val dDay : String = ""
)

@RequiresApi(Build.VERSION_CODES.O)
fun MeetingMain.toMeetingUiModel() = MeetingUiModel(
    hostName = hostName,
    joinedUserCount = joinedUserCount,
    meetingId = meetingId,
    meetingName,
    meetingStatus = MeetingStatus.valueOf(meetingStatus)
    , minimumAlertMembers, confirmedDate, confirmedPlace, confirmedTime, votingUserCount,
    yaksogi = MeetingYaksogi.valueOf(yaksokiType),
    dDay = getDday(confirmedDate)
)

@RequiresApi(Build.VERSION_CODES.O)
fun getDday(confirmedDate: String?) : String {
    var dDay : String
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val endDate = dateFormat.parse(confirmedDate).time

    val today = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time.time

    var calcuDate = (endDate- today) / (60 * 60 * 24 * 1000)
    dDay = calcuDate.toString()
    return dDay
}