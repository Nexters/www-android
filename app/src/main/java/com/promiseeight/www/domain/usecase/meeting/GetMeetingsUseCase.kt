package com.promiseeight.www.domain.usecase.meeting

import com.promiseeight.www.domain.model.MeetingDetail
import com.promiseeight.www.domain.model.MeetingMainList
import com.promiseeight.www.domain.repository.MeetingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject



class GetMeetingsUseCase @Inject constructor(
    private val meetingRepository: MeetingRepository
) {
    operator fun invoke() : Flow<Result<MeetingMainList>> {
        return meetingRepository.getMeetings()
    }
}