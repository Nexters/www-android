package com.promiseeight.www.ui.onboard

import androidx.lifecycle.ViewModel
import com.promiseeight.www.domain.usecase.auth.GetIsFirstUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    getIsFirstUseCase: GetIsFirstUseCase,
) : ViewModel(){
    val isFirst = getIsFirstUseCase()
}