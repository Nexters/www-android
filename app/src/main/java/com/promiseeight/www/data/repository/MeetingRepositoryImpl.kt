package com.promiseeight.www.data.repository

import com.promiseeight.www.data.model.request.toMeetingCreateRequest
import com.promiseeight.www.data.model.response.toMeetingDetail
import com.promiseeight.www.data.model.response.toMeetingInvitation
import com.promiseeight.www.data.model.response.toMeetingMainList
import com.promiseeight.www.data.source.remote.MeetingRemoteDataSource
import com.promiseeight.www.domain.model.*
import com.promiseeight.www.domain.repository.MeetingRepository
import com.promiseeight.www.domain.model.MeetingStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
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

    override fun getMeetingDetailByCode(code: String): Flow<Result<MeetingDetail>> = flow {
        meetingRemoteDataSource.getMeetingDetailByCode(code).onSuccess {
            try{
                emit(Result.success(it.toMeetingDetail()))
            } catch (e : Exception){
                Timber.d("asdasd ${e.toString()}")
            }
        }.onFailure {
            Timber.d("asdasd ${it.toString()}")
            emit(Result.failure(it))
        }
    }

    override fun getMeetingDetailById(meetingId: Long): Flow<Result<MeetingDetail>> = flow {
        meetingRemoteDataSource.getMeetingDetailById(meetingId).runCatching {
            getOrThrow()
        }.onSuccess {
            emit(Result.success(it.toMeetingDetail()))
        }.onFailure {
            emit(Result.failure(it))
        }
    }

    override fun joinMeeting(
        meetingId: Long,
        meetingJoinCondition: MeetingJoinCondition
    ): Flow<Result<Unit>> = flow {
        meetingRemoteDataSource.joinMeeting(meetingId,meetingJoinCondition)
            .onSuccess {
                emit(Result.success(Unit))
            }.onFailure {
                emit(Result.failure(it))
            }
    }

    override fun putMeetingStatus(
        meetingId: Long,
        meetingStatus: MeetingStatus
    ): Flow<Result<Unit>> = flow {
        meetingRemoteDataSource.putMeetingStatus(meetingId,meetingStatus)
            .onSuccess {
                emit(Result.success(Unit))
            }.onFailure {
                emit(Result.failure(it)) //확인부탁
            }
    }

    override fun getMeetings(): Flow<Result<MeetingMainList>> = flow {
        meetingRemoteDataSource.getMeetings()
            .onSuccess {
                emit(Result.success(it.toMeetingMainList()))
            }.onFailure {
                emit(Result.failure(it))
            }
    }
}