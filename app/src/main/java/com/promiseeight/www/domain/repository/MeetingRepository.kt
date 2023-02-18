package com.promiseeight.www.domain.repository

import com.promiseeight.www.domain.model.MeetingCondition
import com.promiseeight.www.domain.model.MeetingInvitation
import kotlinx.coroutines.flow.Flow

interface MeetingRepository {
    fun createMeeting(meetingCondition: MeetingCondition) : Flow<Result<MeetingInvitation>>
}