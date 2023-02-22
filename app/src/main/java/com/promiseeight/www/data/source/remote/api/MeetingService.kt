package com.promiseeight.www.data.source.remote.api

import com.promiseeight.www.data.model.request.MeetingCreateRequest
import com.promiseeight.www.data.model.response.BaseResponse
import com.promiseeight.www.data.model.response.MeetingCreateResponse
import com.promiseeight.www.data.model.response.MeetingDetailResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MeetingService {
    @POST("/meetings")
    suspend fun createMeeting(
        @Body meetingCreateRequest : MeetingCreateRequest
    ) : BaseResponse<MeetingCreateResponse>

    @GET("/meetings/code/{meetingCode}")
    suspend fun getMeetingDetailByCode(
        @Path("meetingCode") meetingCode : String
    ) : BaseResponse<MeetingDetailResponse>
}