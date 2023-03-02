package com.promiseeight.www.domain.usecase.auth

import com.promiseeight.www.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetIsFirstFalseUseCase   @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke()  {
        return authRepository.setIsFirstFalse()
    }
}