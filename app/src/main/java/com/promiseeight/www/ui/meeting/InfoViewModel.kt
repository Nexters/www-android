package com.promiseeight.www.ui.meeting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.promiseeight.www.domain.model.*
import com.promiseeight.www.domain.usecase.meeting.CreateMeetingUseCase
import com.promiseeight.www.domain.usecase.meeting.GetMeetingByCodeUseCase
import com.promiseeight.www.ui.common.util.DateTimeUtil.getDateTimeTableSize
import com.promiseeight.www.ui.common.util.DateTimeUtil.getTimeUiModelList
import com.promiseeight.www.domain.usecase.meeting.JoinMeetingUseCase
import com.promiseeight.www.ui.model.CandidateUiModel
import com.promiseeight.www.ui.model.TimeUiModel
import com.promiseeight.www.ui.model.enums.CodeStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import timber.log.Timber
import java.util.*
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

    private var _startDate = MutableStateFlow(DateTime.now())
    val startDate : StateFlow<DateTime> get() = _startDate
    private var _endDate = MutableStateFlow(DateTime.now().plusDays(10)) // 임시코드
    val endDate : StateFlow<DateTime> get() = _endDate

    var meetingDateTime = MutableStateFlow<List<TimeUiModel>>(emptyList())

    val meetingDateTimeFromPeriod : StateFlow<List<TimeUiModel>> = combine(startDate, endDate){ start, end ->
        val size = getDateTimeTableSize(start, end)
        val dateTimes = mutableListOf<TimeUiModel>()
        for(i in 0 until size){
            dateTimes += getTimeUiModelList(start,end,i)
        }
        meetingDateTime.value = dateTimes
        dateTimes
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    private var _meetingDateCandidates = MutableStateFlow<List<CandidateUiModel>>(emptyList())
    val meetingDateCandidates : StateFlow<List<CandidateUiModel>> = combine(_meetingDateCandidates,meetingDateTime){ candidates, dateTimes ->
        dateTimes.filter {
            it.selected
        }.map {
            CandidateUiModel(it.id , "${it.date.dayOfMonth} (${it.date.dayOfWeek().getAsText(Locale.KOREAN)[0]}) ${it.time.korean}")
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(500),
        emptyList()
    )

    private var _meetingPlaceCandidates = MutableStateFlow<List<CandidateUiModel>>(emptyList())
    val meetingPlaceCandidates: StateFlow<List<CandidateUiModel>> get() = _meetingPlaceCandidates

    private var _meetingRegisteredPlaces = MutableStateFlow<List<CandidateUiModel>>(emptyList())
    val meetingRegisteredPlaces: StateFlow<List<CandidateUiModel>> get() = _meetingRegisteredPlaces

    val meetingPeriodState : StateFlow<Int> = combine(startDate, endDate) { start, end ->
        getDateTimeTableSize(start, end)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = 0
    )

    fun initDates() {
        _startDate.value = DateTime.now()
        _endDate.value = DateTime.now().plusDays(10)
    }

    val meetingPlaces: StateFlow<List<CandidateUiModel>> = combine(
        meetingPlaceCandidates,
        meetingRegisteredPlaces
    ) { candidates, registeredPlaces ->
        if (candidates.isEmpty() && registeredPlaces.isEmpty()) listOf(CandidateUiModel(title = "예시) 강남역", isPossibleDelete =  false))
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

    fun removeMeetingDateCandidate(id : Long){
        meetingDateTime.value = meetingDateTime.value.map {
            if(it.id == id) it.copy(selected = false)
            else it.copy()
        }
    }

    fun checkMeetingPlaceDuplicate(): Boolean {
        return meetingPlaces.value.none {
            it.title == meetingPlace.value.trim() // 공백 제거한 문자 중복 검사
        }
    }

    fun addMeetingPlaceCandidate() {
        _meetingPlaceCandidates.value = meetingPlaceCandidates.value.plus(
            CandidateUiModel(
                title = meetingPlace.value.trim() // 공백 제거한 문자 추가
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

    fun selectMeetingDateTime(id : Long){
        meetingDateTime.value = meetingDateTime.value.map {
            if(it.id == id){
                it.copy(selected = !it.selected)
            }
            else {
                it.copy()
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