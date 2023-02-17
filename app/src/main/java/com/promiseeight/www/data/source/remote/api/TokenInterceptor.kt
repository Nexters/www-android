package com.promiseeight.www.data.source.remote.api

import com.promiseeight.www.data.source.local.AuthLocalDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

/**
 * Header에 AccessToken을 넣어주는 용도
 */
class TokenInterceptor @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val token = runBlocking {
          authLocalDataSource.getAccessToken().first().getOrThrow()
        }
        val request = original.newBuilder().apply {
            header("Authorization", "Bearer ${token}")
            method(original.method, original.body)
        }.build()
        return chain.proceed(request)
    }
}