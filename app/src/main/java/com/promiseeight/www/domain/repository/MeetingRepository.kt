package com.promiseeight.www.domain.repository

import com.promiseeight.www.domain.model.*
import kotlinx.coroutines.flow.Flow

interface MeetingRepository {
    fun createMeeting(meetingCondition: MeetingCondition) : Flow<Result<MeetingInvitation>>

    fun getMeetingDetailByCode(code : String) : Flow<Result<MeetingDetail>>

    fun joinMeeting(meetingId : Long, meetingJoinCondition: MeetingJoinCondition) : Flow<Result<Unit>>

    fun getMeetings() : Flow<Result<MeetingMainList>>
}