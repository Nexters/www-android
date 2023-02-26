package com.promiseeight.www.ui.meeting

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

    private var _meetingCapacity = MutableStateFlow(1)
    val meetingCapacity: StateFlow<Int> get() = _meetingCapacity

    private var _startDate = MutableStateFlow(DateTime.now())
    val startDate : StateFlow<DateTime> get() = _startDate
    private var _endDate = MutableStateFlow(DateTime.now().plusDays(10))
    val endDate : StateFlow<DateTime> get() = _endDate

    var meetingDateTimes = MutableStateFlow<List<TimeUiModel>>(emptyList())

    val meetingDateTimeFromPeriod : StateFlow<List<TimeUiModel>> = combine(startDate, endDate){ start, end ->
        val size = getDateTimeTableSize(start, end)
        val dateTimes = mutableListOf<TimeUiModel>()
        for(i in 0 until size){
            dateTimes += getTimeUiModelList(start,end,i)
        }
        meetingDateTimes.value = dateTimes
        dateTimes
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    private var _meetingDateCandidates = MutableStateFlow<List<CandidateUiModel>>(emptyList())
    val meetingDateCandidates : StateFlow<List<CandidateUiModel>> = combine(_meetingDateCandidates,meetingDateTimes){ candidates, dateTimes ->
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

    private var _meetingPeriodState = MutableStateFlow(MeetingInfoPeriodState())
    val meetingPeriodState: StateFlow<MeetingInfoPeriodState> get() = _meetingPeriodState

    private val _meetingJoinState = MutableStateFlow(false)
    val meetingJoinState : StateFlow<Boolean> get() = _meetingJoinState

    private var _infoMessage = MutableStateFlow("")
    val infoMessage : StateFlow<String> get() = _infoMessage

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
        meetingDateTimes.value = meetingDateTimes.value.map {
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
        var index = 0L
        viewModelScope.launch {
            getMeetingByCodeUseCase(meetingCode.value)
                .catch {
                    _meetingCodeStatus.value = CodeStatus.INVALID
                }.collectLatest {
                    it.onSuccess {
                        if(!it.isJoined) {
                            meetingId = it.meetingId
                            _startDate.value = DateTime.parse(it.startDate)
                            _endDate.value = DateTime.parse(it.endDate)
                            meetingNicknameHint.value = it.currentUserName ?: ""
                            meetingNicknameList.value = it.joinedUserInfoList.map {
                                it.joinedUserName
                            }
                            _meetingRegisteredPlaces.value = it.userPromisePlaceList?.map {
                                CandidateUiModel(index++, it.promisePlace, isPossibleDelete = false)
                            } ?: emptyList<CandidateUiModel>()
                            _meetingCodeStatus.value = CodeStatus.SUCCESS
                        } else {
                            _meetingCodeStatus.value = CodeStatus.IS_JOINED
                        }
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
                    if (meetingPeriodState.value.meetingPeriodStart?.dateTime == it.dateTime){
                        if(meetingPeriodState.value.meetingPeriodStart?.dateTime?.dayOfWeek == 6
                            || it.dateTime.dayOfMonth().withMaximumValue().dayOfMonth == it.dateTime.dayOfMonth) it.copy(dateState = DateUiState.SELECTED_SATURDAY_START)
                        else it.copy(dateState = DateUiState.SELECTED_START)
                    }
                    else if (meetingPeriodState.value.meetingPeriodEnd?.dateTime == it.dateTime){
                        if(meetingPeriodState.value.meetingPeriodEnd?.dateTime?.dayOfWeek == 7
                            || it.dateTime.dayOfMonth().withMinimumValue().dayOfMonth == it.dateTime.dayOfMonth) it.copy(dateState = DateUiState.SELECTED_SUNDAY_END)
                        else it.copy(dateState = DateUiState.SELECTED_END)
                    }
                    else if (it.dateTime.millis in meetingPeriodState.value.meetingPeriodStart!!.dateTime.millis..meetingPeriodState.value.meetingPeriodEnd!!.dateTime.millis)
                        if(it.dateTime.dayOfWeek == 7 || it.dateTime.dayOfMonth().withMinimumValue().dayOfMonth == it.dateTime.dayOfMonth) it.copy(dateState = DateUiState.PASS_START)
                        else if(it.dateTime.dayOfWeek == 6  || it.dateTime.dayOfMonth().withMaximumValue().dayOfMonth == it.dateTime.dayOfMonth) it.copy(dateState = DateUiState.PASS_END)
                        else it.copy(dateState = DateUiState.PASS)
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
                        setMeetingPeriodEnd(calendarUiModel)
                        _startDate.value = meetingPeriodState.value.meetingPeriodStart?.dateTime
                        _endDate.value = meetingPeriodState.value.meetingPeriodEnd?.dateTime
                    } else {
                        _infoMessage.value = "14일 이내로 선택할 수 있어요"
                    }
                }
                else {
                    _infoMessage.value = "끝 날짜는 시작 날짜 이후로 선택할 수 있어요"
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
                    startDate = startDate.value.toString("yyyy-MM-dd"),
                    endDate =  endDate.value.toString("yyyy-MM-dd"),
                    minimumAlertMembers = meetingCapacity.value.toLong(),
                    promiseTimeList = meetingDateTimes.value.filter {
                        it.selected
                    }.map {
                        UserPromiseTime(
                            promiseDate = it.date.toString("yyyy-MM-dd"),
                            promiseTime = it.time
                        )
                    },
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
        meetingDateTimes.value = meetingDateTimes.value.map {
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
                        userPromiseTimeList = meetingDateTimes.value.filter {
                            it.selected
                        }.map {
                            UserPromiseTime(
                                promiseDate = it.date.toString("yyyy-MM-dd"),
                                promiseTime = it.time
                            )
                        }
                    )
                ).catch {

                }.collectLatest {
                   it.onSuccess {
                       _meetingJoinState.value = true
                   }.onFailure {
                       _infoMessage.value = it.message ?: "방에 참여할 수 없어요"
                   }
                }
            }
        }

    }

    fun getMeetingId() = meetingId

    fun setInfoMessageEmpty(){
        _infoMessage.value = ""
    }
}