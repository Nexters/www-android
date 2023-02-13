package com.promiseeight.www.ui.meeting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.promiseeight.www.ui.model.CandidateUiModel
import com.promiseeight.www.ui.model.enums.CodeStatus
import kotlinx.coroutines.flow.*

class InfoViewModel : ViewModel() {
    var totalPage = 1

    val codeMaxSize = 6

    private var _page = MutableStateFlow(1)
    val page: StateFlow<Int>
        get() = _page

    val meetingName = MutableStateFlow("")

    val meetingUserName = MutableStateFlow("")

    val meetingPlace = MutableStateFlow("")

    val meetingCode = MutableStateFlow("")

    private var _meetingCodeStatus = MutableStateFlow(CodeStatus.READY)
    val meetingCodeStatus: StateFlow<CodeStatus> get() = _meetingCodeStatus
//        = _meetingCodeStatus.asStateFlow().combine(meetingCode){ status , code ->
//            if(code.length < codeMaxSize) CodeStatus.READY
//            else if(status == CodeStatus.INVALID) CodeStatus.INVALID
//            else if(code.length == codeMaxSize) CodeStatus.ACTIVE
//            else CodeStatus.READY
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(500),
//        initialValue = CodeStatus.READY
//    )

    private var _meetingCapacity = MutableStateFlow(1)
    val meetingCapacity: StateFlow<Int> get() = _meetingCapacity

    private var _meetingDateCandidates = MutableStateFlow<List<CandidateUiModel>>(emptyList())
    val meetingDateCandidates: StateFlow<List<CandidateUiModel>> get() = _meetingDateCandidates

    private var _meetingPlaceCandidates = MutableStateFlow<List<CandidateUiModel>>(emptyList())
    val meetingPlaceCandidates: StateFlow<List<CandidateUiModel>> get() = _meetingPlaceCandidates

    private var _meetingRegisteredPlaces = MutableStateFlow<List<CandidateUiModel>>(emptyList())
    val meetingRegisteredPlaces: StateFlow<List<CandidateUiModel>> get() = _meetingRegisteredPlaces

    val meetingPlaces: StateFlow<List<CandidateUiModel>> = combine(
        meetingPlaceCandidates,
        meetingRegisteredPlaces
    ) { candidates, registeredPlaces ->
        if (candidates.isEmpty() && registeredPlaces.isEmpty()) listOf(CandidateUiModel("예시) 강남역"))
        else candidates + registeredPlaces
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = emptyList()
    )


    init { //dummy 데이터 넣는 init임
        _meetingDateCandidates.value = mutableListOf(
            CandidateUiModel("25 (토) 낮"),
            CandidateUiModel("26 (일) 저녁"),
            CandidateUiModel("26 (일) 밤"),
            CandidateUiModel("27 (월) 저녁")
        )
    }

    fun setPage(page: Int) {
        _page.value = page
    }

    fun setMeetingNameEmpty() {
        meetingName.value = ""
    }

    fun setMeetingUserNameEmpty() {
        meetingUserName.value = ""
    }

    fun setMeetingCodeEmpty() {
        meetingCode.value = ""
    }

    fun setMeetingCapacity(capacity: Int) {
        _meetingCapacity.value = capacity
    }

    fun plusMeetingCapacity() {
        _meetingCapacity.value = meetingCapacity.value + 1
    }

    fun minusMeetingCapacity() {
        _meetingCapacity.value = meetingCapacity.value - 1
    }

    fun addMeetingDateCandidate(candidateUiModel: CandidateUiModel) {

    }

    fun removeMeetingDateCandidate() {

    }

    fun checkMeetingPlaceDuplicate(): Boolean {
        return meetingPlaces.value.none {
            it.title == meetingPlace.value.trim() // 공백 제거한 문자 중복 검사
        }
    }

    fun addMeetingPlaceCandidate() {
        _meetingPlaceCandidates.value = meetingPlaceCandidates.value.plus(
            CandidateUiModel(
                meetingPlace.value.trim() // 공백 제거한 문자 추가
            )
        )
        meetingPlace.value = "" // 장소명 초기화
    }

    fun removeMeetingPlaceCandidate(title : String) {
        _meetingPlaceCandidates.value = meetingPlaceCandidates.value.filterNot {
            it.title == title
        }
    }

    fun checkCodeValid() : Boolean {
        return if(meetingCode.value == "aaaaaa"){
            _meetingCodeStatus.value = CodeStatus.ACTIVE
            true
        }
        else {
            _meetingCodeStatus.value = CodeStatus.INVALID
            false
        }
    }

    fun setCodeStatus(codeStatus: CodeStatus) {
        _meetingCodeStatus.value = codeStatus
    }
}