package com.promiseeight.www.domain.usecase.meeting

import com.promiseeight.www.domain.model.MeetingCondition
import com.promiseeight.www.domain.model.MeetingInvitation
import com.promiseeight.www.domain.model.MeetingJoinCondition
import com.promiseeight.www.domain.repository.MeetingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JoinMeetingUseCase @Inject constructor(
    private val meetingRepository: MeetingRepository
) {
    operator fun invoke(meetingId : Long,meetingJoinCondition: MeetingJoinCondition) : Flow<Result<Unit>> {
        return meetingRepository.joinMeeting(meetingId,meetingJoinCondition)
    }
}