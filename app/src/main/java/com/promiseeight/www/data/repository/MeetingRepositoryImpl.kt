package com.promiseeight.www.data.repository

import com.promiseeight.www.data.model.request.toMeetingCreateRequest
import com.promiseeight.www.data.model.response.toMeetingInvitation
import com.promiseeight.www.data.source.remote.MeetingRemoteDataSource
import com.promiseeight.www.domain.model.MeetingCondition
import com.promiseeight.www.domain.model.MeetingInvitation
import com.promiseeight.www.domain.repository.MeetingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MeetingRepositoryImpl @Inject constructor(
    private val meetingRemoteDataSource : MeetingRemoteDataSource
) : MeetingRepository {
    override fun createMeeting(meetingCondition: MeetingCondition): Flow<Result<MeetingInvitation>> = flow {
        meetingRemoteDataSource.createMeeting(meetingCondition.toMeetingCreateRequest()).runCatching {
            getOrThrow()
        }.onSuccess {
            emit(Result.success(it.toMeetingInvitation()))
        }.onFailure {
            emit(Result.failure(it))
        }
    }
}