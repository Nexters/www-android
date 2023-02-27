package com.promiseeight.www.domain.usecase.meeting

import com.promiseeight.www.domain.model.MeetingDetail
import com.promiseeight.www.domain.repository.MeetingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMeetingByIdUseCase @Inject constructor(
    private val meetingRepository: MeetingRepository
) {
    operator fun invoke(meetingId : Long) : Flow<Result<MeetingDetail>> {
        return meetingRepository.getMeetingDetailById(meetingId)
    }
}