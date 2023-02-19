package com.promiseeight.www.domain.usecase.meeting

import com.promiseeight.www.domain.model.MeetingDetail
import com.promiseeight.www.domain.repository.MeetingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMeetingByCodeUseCase @Inject constructor(
    private val meetingRepository: MeetingRepository
) {
    operator fun invoke(code: String) : Flow<Result<MeetingDetail>> {
        return meetingRepository.getMeetingDetailByCode(code)
    }
}