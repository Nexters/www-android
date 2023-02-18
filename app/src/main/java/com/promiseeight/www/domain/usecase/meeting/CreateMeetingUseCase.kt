package com.promiseeight.www.domain.usecase.meeting

import com.promiseeight.www.domain.model.MeetingCondition
import com.promiseeight.www.domain.model.MeetingInvitation
import com.promiseeight.www.domain.repository.MeetingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateMeetingUseCase @Inject constructor(
    private val meetingRepository: MeetingRepository
) {
    operator fun invoke(meetingCondition: MeetingCondition) : Flow<Result<MeetingInvitation>> {
        return meetingRepository.createMeeting(meetingCondition)
    }
}