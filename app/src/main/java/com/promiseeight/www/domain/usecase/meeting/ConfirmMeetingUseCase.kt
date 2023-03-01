package com.promiseeight.www.domain.usecase.meeting

import com.promiseeight.www.domain.repository.MeetingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConfirmMeetingUseCase @Inject constructor(
    private val meetingRepository: MeetingRepository
) {
    operator fun invoke(
        meetingId: Long,
        meetingPlaceId: Long,
        meetingUserTimetableId: Long
    ): Flow<Result<Unit>> {
        return meetingRepository.putMeetingStatusConfirmed(meetingId,meetingPlaceId,meetingUserTimetableId)
    }
}