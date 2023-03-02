package com.promiseeight.www.domain.usecase.auth

import com.promiseeight.www.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIsFirstUseCase   @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() : Flow<Result<Boolean>> {
        return authRepository.getIsFirst()
    }
}