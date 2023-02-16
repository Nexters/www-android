package com.promiseeight.www.data.source.remote.api

import com.promiseeight.www.data.model.request.MeetingCreateRequest
import com.promiseeight.www.data.model.response.BaseResponse
import com.promiseeight.www.data.model.response.MeetingCreateResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface MeetingService {
    @POST("/meetings")
    suspend fun createMeeting(
        @Body meetingCreateRequest : MeetingCreateRequest
    ) : BaseResponse<MeetingCreateResponse>
}