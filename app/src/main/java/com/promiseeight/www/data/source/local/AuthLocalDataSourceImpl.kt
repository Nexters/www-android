package com.promiseeight.www.data.source.local

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.promiseeight.www.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

/*
    Auth(DeviceId) 관련 LocalDataSource 구현 클래스
 */

class AuthLocalDataSourceImpl @Inject constructor(
    private val preferenceDataStore: DataStore<Preferences>,
    @ApplicationContext private val context: Context
) : AuthLocalDataSource {
    private val KEY_FCM_TOKEN = stringPreferencesKey("fcm_token")
    private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
    private val IS_FIRST = booleanPreferencesKey("is_first")

    override fun getPreferenceFcmToken(): Flow<Result<String>> =
        preferenceDataStore.data.map { preferences ->
            val r = preferences[KEY_FCM_TOKEN] ?: "NO_TOKEN"
            if (r == "NO_TOKEN") {
                Result.failure(Exception("NO_TOKEN"))
            } else {
                Result.success(r)
            }
        }


    override suspend fun setFcmTokenStatus(fcmToken: String) {
        preferenceDataStore.edit { settings ->
            settings[KEY_FCM_TOKEN] = fcmToken
        }
    }

    @SuppressLint("HardwareIds")
    override fun getDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    override fun getAccessToken(): Flow<Result<String>> =
        preferenceDataStore.data.map { preferences ->
            val r = preferences[KEY_ACCESS_TOKEN] ?: "NO_TOKEN"
            if (r == "NO_TOKEN" || r.isBlank()) {
                Result.failure(Exception("NO_TOKEN"))
            } else {
                Result.success(r)
            }
        }

    override suspend fun setAccessToken(accessToken: String) {
        preferenceDataStore.edit { settings ->
            settings[KEY_ACCESS_TOKEN] = accessToken
        }
    }

    override fun getIsFirst(): Flow<Result<Boolean>> = flow {
        try {
            preferenceDataStore.data.map { preferences ->
                val r = preferences[IS_FIRST] ?: true
                Timber.d("asdasd ${r}")
                emit(Result.success(r))
            }
        } catch (e:Exception){
            Timber.d("asdasd ${e}")
            emit(Result.failure<Boolean>(e))
        }
    }

    override suspend fun setIsFirstFalse() {
        preferenceDataStore.edit { settings ->
            settings[IS_FIRST] = false
        }
    }

}