package com.promiseeight.www.domain.repository

import com.promiseeight.www.domain.model.MeetingCondition
import com.promiseeight.www.domain.model.MeetingDetail
import com.promiseeight.www.domain.model.MeetingInvitation
import com.promiseeight.www.domain.model.MeetingJoinCondition
import kotlinx.coroutines.flow.Flow

interface MeetingRepository {
    fun createMeeting(meetingCondition: MeetingCondition) : Flow<Result<MeetingInvitation>>

    fun getMeetingDetailByCode(code : String) : Flow<Result<MeetingDetail>>

    fun joinMeeting(meetingId : Long, meetingJoinCondition: MeetingJoinCondition) : Flow<Result<Unit>>
}