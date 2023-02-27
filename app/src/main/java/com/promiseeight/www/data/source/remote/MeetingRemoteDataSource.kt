package com.promiseeight.www.data.source.remote

import com.promiseeight.www.data.model.request.JoinMeetingRequest
import com.promiseeight.www.data.model.request.MeetingCreateRequest
import com.promiseeight.www.data.model.response.MeetingCreateResponse
import com.promiseeight.www.data.model.response.MeetingDetailResponse
import com.promiseeight.www.data.model.response.MeetingMainListResponse
import com.promiseeight.www.domain.model.MeetingJoinCondition

/*
    Meeting(약속) 관련 RemoteDataSource 인터페이스
 */

interface MeetingRemoteDataSource {
    suspend fun createMeeting(meetingCreateRequest: MeetingCreateRequest) : Result<MeetingCreateResponse>

    suspend fun getMeetingDetailByCode(meetingCode : String) : Result<MeetingDetailResponse>

    suspend fun joinMeeting(meetingId : Long, meetingJoinCondition: MeetingJoinCondition) : Result<Unit>

    suspend fun getMeetings() : Result<MeetingMainListResponse>
}