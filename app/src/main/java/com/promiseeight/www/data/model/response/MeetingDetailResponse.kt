package com.promiseeight.www.data.model.response

import com.google.gson.annotations.SerializedName
import com.promiseeight.www.domain.model.MeetingDetail
import com.promiseeight.www.domain.model.PlaceVote

data class MeetingDetailResponse(
    @SerializedName("hostName")
    val hostName : String,
    @SerializedName("isHost")
    val isHost : Boolean,
    @SerializedName("isJoined")
    val isJoined : Boolean,
    @SerializedName("joinedUserCount")
    val joinedUserCount : Long,
    @SerializedName("meetingCode")
    val meetingCode : String,
    @SerializedName("meetingId")
    val meetingId : Long,
    @SerializedName("meetingName")
    val meetingName : String,
    @SerializedName("meetingStatus")
    val meetingStatus : String,
    @SerializedName("minimumAlertMembers")
    val minimumAlertMembers : Long,
    @SerializedName("shortLink")
    val shortLink : String,
    @SerializedName("confirmedDate")
    val confirmedDate : String?,
    @SerializedName("confirmedPlace")
    val confirmedPlace : String?,
    @SerializedName("confirmedTime")
    val confirmedTime : String?,
    @SerializedName("currentUserName")
    val currentUserName : String?,
    @SerializedName("userPromiseDateTimeList")
    val userPromiseDateTimeList : List<UserPromiseTimeResponse>,
    @SerializedName("userPromisePlaceList")
    val userPromisePlaceList : List<UserPromisePlaceResponse>?,
    @SerializedName("userVoteList")
    val userVoteList : List<Map<String,List<String>>>?,
    @SerializedName("votingUserCount")
    val votingUserCount : Int,
    @SerializedName("joinedUserInfoList")
    val joinedUserInfoList : List<UserResponse>,
    @SerializedName("startDate")
    val startDate : String,
    @SerializedName("endDate")
    val endDate : String,
    @SerializedName("yaksokiType")
    val yaksokiType: String
)

fun MeetingDetailResponse.toMeetingDetail() = MeetingDetail(
    hostName = hostName,
    isHost = isHost,
    isJoined = isJoined,
    joinedUserCount = joinedUserCount,
    meetingCode = meetingCode,
    meetingId = meetingId,
    meetingName = meetingName,
    meetingStatus = meetingStatus,
    minimumAlertMembers = minimumAlertMembers,
    shortLink = shortLink,
    confirmedDate = confirmedDate,
    confirmedPlace = confirmedPlace,
    confirmedTime = confirmedTime,
    currentUserName = currentUserName,
    userPromiseDateTimeList = userPromiseDateTimeList.map {
        it.toUserPromiseTime()
    },
    userPromisePlaceList = userPromisePlaceList?.map {
        it.toUserPromisePlace()
    },
    userVoteList = getUserVoteList(userVoteList),
    votingUserCount = votingUserCount,
    joinedUserInfoList = joinedUserInfoList.map{
        it.toUser()
    },
    startDate = startDate,
    endDate = endDate,
    yaksogi = yaksokiType
)

fun getUserVoteList(mapList : List<Map<String,List<String>>>?) : List<PlaceVote> {
    val mutableList = mutableListOf<PlaceVote>()
    mapList?.forEachIndexed { index, map ->
        map.keys.forEach {
            mutableList.add(
                PlaceVote(
                    placeName = it,
                    userNameList = map[it]?: emptyList()
                )
            )
        }
    }

    return mutableList
}