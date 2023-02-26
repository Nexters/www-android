package com.promiseeight.www.domain.model

import com.google.gson.annotations.SerializedName
import com.promiseeight.www.data.model.response.EntryResponse
import com.promiseeight.www.data.model.response.UserPromisePlaceResponse
import com.promiseeight.www.data.model.response.UserPromiseTimeResponse
import com.promiseeight.www.data.model.response.UserResponse

data class MeetingDetail(
    val hostName : String,
    val isHost : Boolean,
    val isJoined : Boolean,
    val joinedUserCount : Long,
    val meetingCode : String,
    val meetingId : Long,
    val meetingName : String,
    val meetingStatus : String,
    val minimumAlertMembers : Long,
    val shortLink : String,
    val confirmedDate : String?,
    val confirmedPlace : String?,
    val confirmedTime : String?,
    val currentUserName : String?,
    val userPromiseDateTimeList : List<UserPromiseTime>,
    val userPromisePlaceList : List<UserPromisePlace>?,
    val userVoteList : List<PlaceVote>?,
    val votingUserCount : Int,
    val joinedUserInfoList : List<User>,
    val startDate : String,
    val endDate : String
)
