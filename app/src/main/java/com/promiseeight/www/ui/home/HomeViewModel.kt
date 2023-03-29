package com.promiseeight.www.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.promiseeight.www.domain.model.MeetingMainList
import com.promiseeight.www.domain.usecase.auth.SetIsFirstFalseUseCase
import com.promiseeight.www.domain.usecase.meeting.GetMeetingsUseCase
import com.promiseeight.www.ui.model.MeetingMainListUiModel
import com.promiseeight.www.ui.model.MeetingUiModel
import com.promiseeight.www.ui.model.toMeetingMainListUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hilt_aggregated_deps._com_promiseeight_www_ui_meeting_InfoViewModel_HiltModules_BindsModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMeetingsUseCase : GetMeetingsUseCase,
    private val setIsFirstFalseUseCase: SetIsFirstFalseUseCase
    ) : ViewModel() {

    private var _meetingMainList = MutableStateFlow<MeetingMainListUiModel?>(null) //들어간 아이템 null인지 아닌지~
    val meetingMainList : StateFlow<MeetingMainListUiModel?> get() = _meetingMainList

    private var _homePosition = MutableStateFlow(0)
    val homePosition : StateFlow<Int> get() = _homePosition

    private var _ingPage = MutableStateFlow(1)
    val ingPage : StateFlow<Int> get() = _ingPage

    private var _endPage = MutableStateFlow(1)
    val endPage : StateFlow<Int> get() = _endPage

    private var _fabState = MutableStateFlow(false)
    val fabState : StateFlow<Boolean> get() = _fabState

    fun getMeetings() {
        try {
            viewModelScope.launch {
                getMeetingsUseCase()
                    .catch {
                        Timber.e(it)
                    }
                    .collectLatest {
                        it.onSuccess {
                            _meetingMainList.emit(it.toMeetingMainListUiModel())
                        }.onFailure {
                            Timber.e(it)
                        }
                    }
            }
        } catch (e: Exception) {
            Timber.d(e) //exception log (timber)
        }


    }

    fun setIngPage(page : Int){
        _ingPage.value = page
    }

    fun setEndPage(page : Int){
        _endPage.value = page
    }

    fun setHomePosition(position : Int){
        _homePosition.value = position
    }

    fun setIsFirstFalse() {
        viewModelScope.launch {
            setIsFirstFalseUseCase()
        }
    }

    fun updateFabState(bool : Boolean? = null) {
        _fabState.value = bool ?: !fabState.value
    }
}
