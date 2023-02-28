package com.promiseeight.www.data.source.remote

import com.promiseeight.www.data.model.exception.getWwwException
import com.promiseeight.www.data.model.request.*
import com.promiseeight.www.data.model.response.MeetingCreateResponse
import com.promiseeight.www.data.model.response.MeetingDetailResponse
import com.promiseeight.www.data.model.response.MeetingMainListResponse
import com.promiseeight.www.data.source.remote.api.MeetingService
import com.promiseeight.www.domain.model.MeetingJoinCondition
import com.promiseeight.www.ui.meeting.detail.MeetingStatus
import javax.inject.Inject

/*
    Meeting(약속) 관련 RemoteDataSource 구현 클래스
 */

class MeetingRemoteDataSourceImpl @Inject constructor(
    private val meetingService: MeetingService
) : MeetingRemoteDataSource {
    override suspend fun createMeeting(
        meetingCreateRequest: MeetingCreateRequest
    ): Result<MeetingCreateResponse> {
        return try {
            val response = meetingService.createMeeting(
                meetingCreateRequest
            )
            if (response.code == 0) Result.success(response.result)
            else Result.failure(getWwwException(response.code))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMeetingDetailByCode(meetingCode: String): Result<MeetingDetailResponse> {
        return try {
            val response = meetingService.getMeetingDetailByCode(
                meetingCode
            )
            if (response.code == 0) Result.success(response.result)
            else Result.failure(getWwwException(response.code))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMeetingDetailById(meetingId: Long): Result<MeetingDetailResponse> {
        return try {
            val response = meetingService.getMeetingDetailById(
                meetingId
            )
            if (response.code == 0) Result.success(response.result)
            else Result.failure(getWwwException(response.code))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun joinMeeting(
        meetingId: Long,
        meetingJoinCondition: MeetingJoinCondition
    ): Result<Unit> {
        return try {
            val response =
                meetingService.joinMeeting(meetingId, meetingJoinCondition.toJoinMeetingRequest())
            if (response.code == 0) Result.success(response.result)
            else Result.failure(getWwwException(response.code))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun putMeetingStatus(
        meetingId: Long,
        meetingStatus: MeetingStatus
    ): Result<Unit> {
        return try {
            val response = meetingService.putMeetingStatus(meetingId, meetingStatus.name)
            if (response.code == 0) Result.success(response.result)
            else Result.failure(getWwwException(response.code))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMeetings(): Result<MeetingMainListResponse> {
        return try {
            val response = meetingService.getMeetings()
            if (response.code == 0) Result.success(response.result)
            else Result.failure(getWwwException(response.code))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun votePlaces(meetingId: Long, placeIdList: List<Long>): Result<Unit> {
        return try {
            val response = meetingService.votePlaces(
                meetingId,
                PlaceVoteRequest(
                    meetingPlaceIdList = placeIdList
                )
            )
            if (response.code == 0) Result.success(response.result)
            else Result.failure(getWwwException(response.code))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun putMeetingStatusConfirmed(
        meetingId: Long,
        meetingPlaceId: Long,
        meetingUserTimetableId: Long
    ) : Result<Unit> {
        return try {
            val response = meetingService.putMeetingStatusConfirmed(
                meetingId,
                meetingConfirmRequest = MeetingConfirmRequest(
                    meetingPlaceId,meetingUserTimetableId
                )
            )

            if (response.code == 0) Result.success(response.result)
            else Result.failure(getWwwException(response.code))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}