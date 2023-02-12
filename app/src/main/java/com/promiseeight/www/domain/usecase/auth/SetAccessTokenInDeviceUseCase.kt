package com.promiseeight.www.domain.usecase.auth

import com.promiseeight.www.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class SetAccessTokenInDeviceUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(accessToken : String) : Result<Boolean> {
        try {
            return Result.success(authRepository.setAccessTokenInDevice(accessToken).getOrThrow())
        } catch (e : Exception){
            return Result.failure(e)
        }
    }
}