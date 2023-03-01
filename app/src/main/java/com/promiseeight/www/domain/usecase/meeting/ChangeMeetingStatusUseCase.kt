package com.promiseeight.www.domain.usecase.meeting

import com.promiseeight.www.domain.model.MeetingStatus
import com.promiseeight.www.domain.repository.MeetingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangeMeetingStatusUseCase @Inject constructor(
    private val meetingRepository: MeetingRepository
) {
    operator fun invoke(meetingId : Long, meetingStatus : MeetingStatus) : Flow<Result<Unit>> {
        return meetingRepository.putMeetingStatus(meetingId,meetingStatus)
    }
}