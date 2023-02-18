package com.promiseeight.www.data.source.remote

import android.util.Log
import com.promiseeight.www.data.model.request.MeetingCreateRequest
import com.promiseeight.www.data.model.request.UserPromiseTimeRequest
import com.promiseeight.www.data.model.response.MeetingCreateResponse
import com.promiseeight.www.data.source.remote.api.MeetingService
import timber.log.Timber
import javax.inject.Inject

/*
    Meeting(약속) 관련 RemoteDataSource 구현 클래스
 */

class MeetingRemoteDataSourceImpl @Inject constructor(
    private val meetingService : MeetingService
) : MeetingRemoteDataSource {
    override suspend fun createMeeting(
        meetingCreateRequest: MeetingCreateRequest
    ): Result<MeetingCreateResponse> {
        return try{
            val response =  meetingService.createMeeting(
                meetingCreateRequest
            )
            Result.success(response.result)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}