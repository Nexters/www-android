package com.promiseeight.www.data.source.remote.api

import com.promiseeight.www.data.model.request.JoinMeetingRequest
import com.promiseeight.www.data.model.request.MeetingCreateRequest
import com.promiseeight.www.data.model.response.BaseResponse
import com.promiseeight.www.data.model.response.MeetingCreateResponse
import com.promiseeight.www.data.model.response.MeetingDetailResponse
import com.promiseeight.www.data.model.response.MeetingMainListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MeetingService {
    @POST("/meetings")
    suspend fun createMeeting(
        @Body meetingCreateRequest : MeetingCreateRequest
    ) : BaseResponse<MeetingCreateResponse>

    @GET("/meetings")
    suspend fun getMeetings()
    : BaseResponse<MeetingMainListResponse>//arguments x

    @GET("/meetings/code/{meetingCode}")
    suspend fun getMeetingDetailByCode( //arguments 있음
        @Path("meetingCode") meetingCode : String
    ) : BaseResponse<MeetingDetailResponse>

    @POST("/meetings/{meetingId}")
    suspend fun joinMeeting(
        @Path("meetingId") meetingId : Long,
        @Body joinMeetingRequest : JoinMeetingRequest
    ) : BaseResponse<Unit>

    @GET("/meetings/{meetingId}")
    suspend fun getMeetingDetailById(
        @Path("meetingId") meetingId : Long
    ) : BaseResponse<MeetingDetailResponse>

    @PUT("/meetings/{meetingId}/meetingStatus/{meetingStatus}")
    suspend fun putMeetingStatus(
        @Path("meetingId") meetingId : Long,
        @Path("meetingStatus") meetingStatus : String
    ) : BaseResponse<Unit>


}