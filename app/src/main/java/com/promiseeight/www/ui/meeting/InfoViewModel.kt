package com.promiseeight.www.ui.meeting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.promiseeight.www.ui.common.util.CalendarUtil
import com.promiseeight.www.ui.meeting.info.MeetingInfoPeriodState
import com.promiseeight.www.ui.model.CalendarUiModel
import com.promiseeight.www.domain.model.*
import com.promiseeight.www.domain.usecase.meeting.CreateMeetingUseCase
import com.promiseeight.www.domain.usecase.meeting.GetMeetingByCodeUseCase
import com.promiseeight.www.ui.common.util.DateTimeUtil.getDateTimeTableSize
import com.promiseeight.www.ui.common.util.DateTimeUtil.getTimeUiModelList
import com.promiseeight.www.domain.usecase.meeting.JoinMeetingUseCase
import com.promiseeight.www.ui.model.CandidateUiModel
import com.promiseeight.www.ui.model.TimeUiModel
import com.promiseeight.www.ui.model.enums.CodeStatus
import com.promiseeight.www.ui.model.enums.DateUiState
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

    val meetingPeriodSize : StateFlow<Int> = combine(startDate, endDate) { start, end ->
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
    private var _meetingInitialPeriod = MutableStateFlow(CalendarUtil.calendarList)
    val meetingInitialPeriod: StateFlow<List<CalendarUiModel>> get() = _meetingInitialPeriod


    private var _meetingPeriodStart = MutableStateFlow<CalendarUiModel?>(null)
    val meetingPeriodStart: StateFlow<CalendarUiModel?> get() = _meetingPeriodStart

    private var _meetingPeriodEnd = MutableStateFlow<CalendarUiModel?>(null)
    val meetingPeriodEnd: StateFlow<CalendarUiModel?> get() = _meetingPeriodEnd

    private var _meetingPeriodState = MutableStateFlow(MeetingInfoPeriodState())
    val meetingPeriodState: StateFlow<MeetingInfoPeriodState> get() = _meetingPeriodState

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

    fun updatePeriod() {
        _meetingInitialPeriod.value = meetingInitialPeriod.value.map {
            if (it.isCurrentMonth == true) {
                if (meetingPeriodState.value.meetingPeriodStart != null && meetingPeriodState.value.meetingPeriodEnd != null) {
                    if (meetingPeriodState.value.meetingPeriodStart?.dateTime == it.dateTime)
                        it.copy(dateState = DateUiState.SELECTED_START)
                    else if (meetingPeriodState.value.meetingPeriodEnd?.dateTime == it.dateTime)
                        it.copy(dateState = DateUiState.SELECTED_END)
                    //월요일일 때, 토요일일 떄, 1일일때, 28,30,31일때  조건 추가
                    else if (it.dateTime.millis in meetingPeriodState.value.meetingPeriodStart!!.dateTime.millis..meetingPeriodState.value.meetingPeriodEnd!!.dateTime.millis)
                        it.copy(dateState = DateUiState.PASS)
                    else it.copy(dateState = DateUiState.INITIAL)
                } else if (meetingPeriodState.value.meetingPeriodStart != null && meetingPeriodState.value.meetingPeriodEnd == null) {
                    if (meetingPeriodState.value.meetingPeriodStart?.dateTime == it.dateTime) {
                        it.copy(dateState = DateUiState.SELECTED)
                    } else {
                        it.copy(dateState = DateUiState.INITIAL)
                    }
                } else {
                    it.copy(dateState = DateUiState.INITIAL)
                }
            } else it.copy(dateState = DateUiState.INITIAL)
        }
    }

    fun setMeetingPeriodStart(calendarUiModel: CalendarUiModel?) {
        _meetingPeriodState.value = meetingPeriodState.value.copy(
            meetingPeriodStart = calendarUiModel
        )
    }

    fun setMeetingPeriodEnd(calendarUiModel: CalendarUiModel?) {
        _meetingPeriodState.value = meetingPeriodState.value.copy(
            meetingPeriodEnd = calendarUiModel
        )
    }

    fun setMeetingPeriodState(
        stateCalendarUiModel: CalendarUiModel? = null,
        endCalendarUiModel: CalendarUiModel? = null
    ) {
        _meetingPeriodState.value = meetingPeriodState.value.copy(
            meetingPeriodEnd = null,
            meetingPeriodStart = null
        )
    }

    fun selectDate(calendarUiModel: CalendarUiModel) {
        if (meetingPeriodState.value.meetingPeriodStart == null) { // start가 null 이면 start에 값 넣는다.
            setMeetingPeriodStart(calendarUiModel)
        } else if (meetingPeriodState.value.meetingPeriodStart?.dateTime == calendarUiModel.dateTime) { //  start를 다시 누르면 null 로 된다.
            setMeetingPeriodState()
        } else if (meetingPeriodState.value.meetingPeriodEnd?.dateTime == null) { // 누른 날짜가 start가 아니고 end에 값이 없으면 end에 값 넣는다
            meetingPeriodState.value.meetingPeriodStart?.dateTime?.let {
                if(calendarUiModel.dateTime.isAfter(it.millis)) { // end가 이후가 맞는지?
                    if(it.plusDays(14).dayOfYear > calendarUiModel.dateTime.dayOfYear ){
                        Log.d("asdasd", "맞다")
                        setMeetingPeriodEnd(calendarUiModel)
                    } else {
                        Log.d("asdasd", "14일 초과")
                    }
                }
                else {
                    Log.d("asdasd","선택안됨")
                }
            }

        } else if (meetingPeriodState.value.meetingPeriodEnd?.dateTime == calendarUiModel.dateTime) { // 누른 날짜가 end랑 같으면 null
            setMeetingPeriodState()
        } else {
            setMeetingPeriodState()
        }
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