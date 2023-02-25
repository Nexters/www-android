package com.promiseeight.www.ui.meeting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.promiseeight.www.domain.model.*
import com.promiseeight.www.domain.usecase.meeting.CreateMeetingUseCase
import com.promiseeight.www.domain.usecase.meeting.GetMeetingByCodeUseCase
import com.promiseeight.www.domain.usecase.meeting.JoinMeetingUseCase
import com.promiseeight.www.ui.model.CandidateUiModel
import com.promiseeight.www.ui.model.enums.CodeStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val createMeetingUseCase: CreateMeetingUseCase,
    private val getMeetingByCodeUseCase: GetMeetingByCodeUseCase,
    private val joinMeetingUseCase: JoinMeetingUseCase
) : ViewModel() {
    var totalPage = 1

    val codeMaxSize = 6

    private var _page = MutableStateFlow(1)
    val page: StateFlow<Int>
        get() = _page

    val meetingName = MutableStateFlow("")

    val meetingUserName = MutableStateFlow("")

    val meetingPlace = MutableStateFlow("")

    val meetingCode = MutableStateFlow("")

    private var meetingId : Long? = null

    private var _meetingCodeStatus = MutableStateFlow(CodeStatus.READY)
    val meetingCodeStatus: StateFlow<CodeStatus> get() = _meetingCodeStatus

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
        if (candidates.isEmpty() && registeredPlaces.isEmpty()) listOf(CandidateUiModel("예시) 강남역",false))
        else candidates.reversed() + registeredPlaces
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = emptyList()
    )

    private val _meetingInvitation = MutableStateFlow<MeetingInvitation?>(null)
    val meetingInvitation : StateFlow<MeetingInvitation?> get() = _meetingInvitation

    private val _meetingJoinState = MutableStateFlow(false)
    val meetingJoinState : StateFlow<Boolean> get() = _meetingJoinState

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

    fun checkCodeValid() {
        viewModelScope.launch {
            getMeetingByCodeUseCase(meetingCode.value)
                .catch {
                    _meetingCodeStatus.value = CodeStatus.INVALID
                    Timber.d("WWW error : getMeetingByCode")
                }.collectLatest {
                    it.onSuccess {
                        meetingId = it.meetingId
                        _meetingCodeStatus.value = CodeStatus.SUCCESS
                    }.onFailure {
                        _meetingCodeStatus.value = CodeStatus.INVALID
                    }
                }
        }
    }

    fun setCodeStatus(codeStatus: CodeStatus) {
        _meetingCodeStatus.value = codeStatus
    }

    fun createMeeting() {
        viewModelScope.launch {
            createMeetingUseCase(
                MeetingCondition(
                    meetingName = meetingName.value,
                    userName = meetingUserName.value,
                    startDate = "2023-02-22",
                    endDate = "2023-03-04",
                    minimumAlertMembers = meetingCapacity.value.toLong(),
                    promiseTimeList = listOf(
                        UserPromiseTime(
                            promiseDate = "2023-02-23",
                            promiseTime = PromiseTime.DINNER
                        ),
                        UserPromiseTime(
                            promiseDate = "2023-02-24",
                            promiseTime = PromiseTime.MORNING
                        ),
                        UserPromiseTime(promiseDate = "2023-02-25", promiseTime = PromiseTime.LUNCH)

                    ),
                    promisePlaceList = meetingPlaceCandidates.value.map {
                        it.title
                    }
                )
            ).catch {
                Timber.d("WWW error : createMeeting")
            }.collectLatest {
                _meetingInvitation.value = it.getOrThrow()
            }
        }
    }

    fun joinMeeting() {
        meetingId?.let {  meetingId ->
            viewModelScope.launch {
                joinMeetingUseCase(
                    meetingId = meetingId,
                    meetingJoinCondition = MeetingJoinCondition(
                        nickname = meetingUserName.value,
                        promisePlaceList = meetingPlaceCandidates.value.map { it.title },
                        userPromiseTimeList = listOf(
                            UserPromiseTime(
                                promiseDate = "2023-02-23",
                                promiseTime = PromiseTime.DINNER
                            ),
                            UserPromiseTime(
                                promiseDate = "2023-02-24",
                                promiseTime = PromiseTime.MORNING
                            ),
                            UserPromiseTime(promiseDate = "2023-02-25", promiseTime = PromiseTime.LUNCH)

                        )
                    )
                ).catch {

                }.collectLatest {
                    if(it.isSuccess) _meetingJoinState.value = true
                }
            }
        }

    }

    fun getMeetingId() = meetingId
}