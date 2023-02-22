package com.promiseeight.www.ui.home

import androidx.lifecycle.ViewModel
import com.promiseeight.www.ui.model.MeetingUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    
    private var _doingMeetings = MutableStateFlow<List<MeetingUiModel>>(emptyList())
    val doingMeeting : StateFlow<List<MeetingUiModel>> get() = _doingMeetings // 진행 중 약속 리스트, 변수 이름은 더 고민해보기..

    private var _doneMeetings = MutableStateFlow<List<MeetingUiModel>>(emptyList())
    val doneMeeting : StateFlow<List<MeetingUiModel>> get() = _doneMeetings //  완료된 약속 리스트, 변수 이름은 더 고민해보기..

    init {
        _doingMeetings.value = doingMeetingsDummy
        _doneMeetings.value = doneMeetingsDummy
    }
}

val doingMeetingsDummy = listOf(
    MeetingUiModel(
        meetingId = 1,
        hostName = "석준",
        joinedUserCount = 3,
        meetingName = "넥버닝 앵콜",
        meetingStatus = "WAITING",
        minimumAlertMembers = 8,
        votingUserCount = 2
    ),
    MeetingUiModel(
        meetingId = 2,
        hostName = "민서",
        joinedUserCount = 6,
        meetingName = "넥모닝",
        meetingStatus = "VOTING",
        minimumAlertMembers = 6,
        votingUserCount = 3
    ),
    MeetingUiModel(
        meetingId = 3,
        hostName = "여종",
        joinedUserCount = 8,
        meetingName = "WWW 회식",
        meetingStatus = "CONFIRMED",
        minimumAlertMembers = 8,
        votingUserCount = 8,
        confirmedDate = "2023-03-05",
        confirmedPlace = "강남역",
        confirmedTime = "MORNING"
    )
)

val doneMeetingsDummy = listOf(
    MeetingUiModel(
        meetingId = 4,
        hostName = "석준",
        joinedUserCount = 8,
        meetingName = "넥버닝 앵콜(종료)",
        meetingStatus = "DONE",
        minimumAlertMembers = 8,
        votingUserCount = 8
    ),
    MeetingUiModel(
        meetingId = 5,
        hostName = "민서",
        joinedUserCount = 6,
        meetingName = "넥모닝(종료)",
        meetingStatus = "DONE",
        minimumAlertMembers = 6,
        votingUserCount = 6
    )
)