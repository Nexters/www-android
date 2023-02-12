package com.promiseeight.www.domain.usecase.auth

import com.promiseeight.www.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class GetAccessTokenWithFcmTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val setAccessTokenInDeviceUseCase: SetAccessTokenInDeviceUseCase
) {
    suspend operator fun invoke(fcmToken : String) : Result<Boolean> {
       try {
           val token = authRepository.getAccessToken(fcmToken).getOrThrow()
           try {
               return Result.success(setAccessTokenInDeviceUseCase(token).getOrThrow())
           } catch (e: Exception){
               return Result.failure(Exception("GET_ACCESS_TOKEN_FAILED"))
           }
       } catch (e : Exception){
           return Result.failure(Exception("GET_FCM_TOKEN_FAILED"))
       }
    }
}