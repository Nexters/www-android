package com.promiseeight.www.domain.usecase.auth

import com.promiseeight.www.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVersionUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() : Flow<Result<String>> {
        return authRepository.getVersion()
    }
}