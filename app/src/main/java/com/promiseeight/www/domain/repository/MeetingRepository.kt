package com.promiseeight.www.domain.repository

import com.promiseeight.www.domain.model.MeetingCondition
import com.promiseeight.www.domain.model.MeetingDetail
import com.promiseeight.www.domain.model.MeetingInvitation
import com.promiseeight.www.domain.model.MeetingJoinCondition
import com.promiseeight.www.ui.meeting.detail.MeetingStatus
import com.promiseeight.www.domain.model.*
import kotlinx.coroutines.flow.Flow

interface MeetingRepository {
    fun createMeeting(meetingCondition: MeetingCondition) : Flow<Result<MeetingInvitation>>

    fun getMeetingDetailByCode(code : String) : Flow<Result<MeetingDetail>>

    fun getMeetingDetailById(meetingId: Long) : Flow<Result<MeetingDetail>>

    fun joinMeeting(meetingId : Long, meetingJoinCondition: MeetingJoinCondition) : Flow<Result<Unit>>

    fun getMeetings() : Flow<Result<MeetingMainList>>

    fun putMeetingStatus(meetingId : Long, meetingStatus : MeetingStatus) : Flow<Result<Unit>>

    fun votePlaces(meetingId : Long, placeIdList : List<Long>) : Flow<Result<Unit>>
}