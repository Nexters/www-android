package com.promiseeight.www.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    
    private var _doingMeetings = MutableStateFlow<List<String>>(emptyList()) // MeetingUiModel 이 아직 없어서 일단은 String으로만 ..
    val doingMeeting : StateFlow<List<String>> get() = _doingMeetings // 진행 중 약속 리스트, 변수 이름은 더 고민해보기..

    private var _doneMeetings = MutableStateFlow<List<String>>(emptyList())
    val doneMeeting : StateFlow<List<String>> get() = _doneMeetings //  완료된 약속 리스트, 변수 이름은 더 고민해보기..

    init {
        _doingMeetings.value = doingMeetingsDummy
        _doneMeetings.value = doneMeetingsDummy
    }
}

val doingMeetingsDummy = listOf(
    "진행중약속1", "진행중약속2", "진행중약속3"
)

val doneMeetingsDummy = listOf(
    "종료된약속1", "종료된약속2"
)