package com.promiseeight.www.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.promiseeight.www.domain.usecase.auth.GetAccessTokenWithFcmTokenUseCase
import com.promiseeight.www.domain.usecase.auth.GetIsFirstUseCase
import com.promiseeight.www.domain.usecase.auth.IsAccessTokenInDeviceUseCase
import com.promiseeight.www.domain.usecase.auth.SetIsFirstFalseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAccessTokenWithFcmTokenUseCase: GetAccessTokenWithFcmTokenUseCase,
    isAccessTokenInDeviceUseCase: IsAccessTokenInDeviceUseCase
) : ViewModel() {

    val isAccessTokenInDevice = isAccessTokenInDeviceUseCase()

    private var _splashStatus = MutableStateFlow<SplashStatus>(SplashStatus.READY)
    val splashStatus : StateFlow<SplashStatus> get() = _splashStatus



    fun getAccessTokenWithFcmToken(fcmToken: String) {
        viewModelScope.launch {
            getAccessTokenWithFcmTokenUseCase(fcmToken)
                .onSuccess {
                    _splashStatus.value = SplashStatus.SUCCESS
                }.onFailure {
                    _splashStatus.value = SplashStatus.ERROR
                }
        }
    }


}