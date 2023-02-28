package com.promiseeight.www.ui.meeting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.promiseeight.www.domain.usecase.meeting.GetMeetingByCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MeetingShareViewModel @Inject constructor(
    private val getMeetingByCodeUseCase: GetMeetingByCodeUseCase
) : ViewModel() {

    private var _meetingId = MutableStateFlow(-1L)
    val meetingId : StateFlow<Long> get() = _meetingId

    fun getMeetingByCode(code : String) {
        try {
            viewModelScope.launch {
                getMeetingByCodeUseCase(code)
                    .catch {
                        Timber.e(it)
                    }.collectLatest {
                        it.onSuccess {
                            _meetingId.emit(it.meetingId)
                        }.onFailure {
                            Timber.e(it)
                        }

                    }
            }
        } catch (e : Exception){
            Timber.e(e)
        }
    }
}