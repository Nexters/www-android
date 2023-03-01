package com.promiseeight.www.domain.usecase.meeting

import com.promiseeight.www.domain.repository.MeetingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VotePlacesUseCase @Inject constructor(
    private val meetingRepository: MeetingRepository
) {
    operator fun invoke(meetingId : Long, placeIdList : List<Long>) : Flow<Result<Unit>> {
        return meetingRepository.votePlaces(meetingId,placeIdList)
    }
}