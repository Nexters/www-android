package com.promiseeight.www.ui.meeting.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.promiseeight.www.domain.usecase.meeting.ChangeMeetingStatusUseCase
import com.promiseeight.www.domain.usecase.meeting.GetMeetingByIdUseCase
import com.promiseeight.www.ui.model.*
import com.promiseeight.www.domain.model.MeetingStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MeetingDetailViewModel @Inject constructor(
    private val getMeetingByIdUseCase: GetMeetingByIdUseCase,
    private val changeMeetingStatusUseCase: ChangeMeetingStatusUseCase
) : ViewModel() {

    private var _meetingId = MutableStateFlow(-1L)
    val meetingId : StateFlow<Long> get() = _meetingId

    private var _meetingDetail = MutableStateFlow<MeetingDetailUiModel?>(null)
    val meetingDetail : StateFlow<MeetingDetailUiModel?> get() = _meetingDetail

    private val _dateRanks = MutableStateFlow(listOf<DateRankUiModel>())
    val dateRanks: StateFlow<List<DateRankUiModel>> get() = _dateRanks

    private val _placeRanks = MutableStateFlow(listOf<PlaceRankUiModel>())
    val placeRanks: StateFlow<List<PlaceRankUiModel>> get() = _placeRanks


    fun selectPlace(id : String){
        _placeRanks.value = placeRanks.value.map {
            if(id == it.id){
                if(it.selected) it.copy(selected = false)
                else it.copy(selected = true)
            }
            else it.copy()
        }
    }

    fun votePlace(){
        _placeRanks.value = placeRanks.value.map {
            it.copy(userVoted = true)
        }
    }

    fun confirmDate(id : String) {
        _dateRanks.value = dateRanks.value.map {
            if(id == it.id){
                if(it.confirmed) it.copy(confirmed = false)
                else it.copy(confirmed = true)
            } else it.copy(confirmed = false)
        }
    }

    fun confirmPlace(id : String) {
        _placeRanks.value = placeRanks.value.map {
            if(id == it.id){
                if(it.confirmed) it.copy(confirmed = false)
                else it.copy(confirmed = true)
            } else it.copy(confirmed = false)
        }
    }

    fun setMeetingId(meetingId: Long){
        _meetingId.value = meetingId
    }

    fun setDateRanks() {
        viewModelScope.launch {
            meetingDetail.value?.let {
                when(it.meetingStatus){
                    MeetingStatus.WAITING,
                    MeetingStatus.VOTING-> {
                        _dateRanks.emit(
                            getDateRankUiModelList(it.userPromiseDateTimeList,it.joinedUserCount)
                        )
                    }
                    else -> {

                    }
                }

            }

        }
    }

    fun setPlaceRanks() {
        viewModelScope.launch {
            meetingDetail.value?.let {
                when(it.meetingStatus){
                    MeetingStatus.WAITING -> {
                        _placeRanks.emit(
                            it.userPromisePlaceList?.map {
                                PlaceRankUiModel(
                                    id = it.promisePlace,
                                    name = it.promisePlace,
                                    count = 0,
                                    progress = 0,
                                    ranking = 0,
                                    meetingVotingStarted = false
                                )
                            } ?: emptyList()
                        )
                    }
                    MeetingStatus.VOTING -> {
                        // 투표 반영해서 바꿔야함
                        _placeRanks.emit(
                            it.userPromisePlaceList?.map {
                                PlaceRankUiModel(
                                    id = it.promisePlace,
                                    name = it.promisePlace,
                                    count = 0,
                                    progress = 0,
                                    ranking = 0,
                                    meetingVotingStarted = false
                                )
                            } ?: emptyList()
                        )
                    }
                    else -> {

                    }
                }
            }
        }
    }

    fun getMeetingDetailById(meetingId : Long) {
        try {
            viewModelScope.launch {
                getMeetingByIdUseCase(meetingId)
                    .catch {

                    }.collectLatest {
                        it.onSuccess { meetingDetail ->
                            Timber.d(it.toString())
                            _meetingDetail.emit(meetingDetail.toMeetingDetailUiModel())

                        }.onFailure {
                            Timber.d("WwwException : MeetingDetailViewModel - getMeetingDetailById ${it.toString()}")
                        }
                    }
            }
        } catch (e : Exception) {
            Timber.d("WwwException : MeetingDetailViewModel - getMeetingDetailById")
        }
    }

    fun changeMeetingStatus() {
        viewModelScope.launch {
            meetingDetail.value?.let { meetingDetail ->
                changeMeetingStatusUseCase(meetingDetail.meetingId, getNextMeetingStatus(meetingDetail.meetingStatus))
                    .catch {

                    }.collectLatest {
                        if (it.isSuccess) {
                            getMeetingDetailById(meetingDetail.meetingId)
                        } else {

                        }
                    }
            }
        }
    }

    fun getNextMeetingStatus(meetingStatus : MeetingStatus) : MeetingStatus { // 현재 상태를 통해 다음 상태를 받아온다.
        return when(meetingStatus){
            MeetingStatus.WAITING -> MeetingStatus.VOTING
            MeetingStatus.VOTING -> MeetingStatus.VOTED
            MeetingStatus.VOTED -> MeetingStatus.CONFIRMED
            MeetingStatus.CONFIRMED -> MeetingStatus.DONE
            else -> MeetingStatus.TERMINATED
        }
    }
}
