package com.promiseeight.www.ui.meeting.detail

import androidx.lifecycle.ViewModel
import com.promiseeight.www.ui.model.DateRankUiModel
import com.promiseeight.www.ui.model.PlaceRankUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MeetingDetailViewModel(

) : ViewModel() {

    private val _dateRanks = MutableStateFlow(listOf<DateRankUiModel>())
    val dateRanks: StateFlow<List<DateRankUiModel>> get() = _dateRanks

    private val _placeRanks = MutableStateFlow(listOf<PlaceRankUiModel>())
    val placeRanks: StateFlow<List<PlaceRankUiModel>> get() = _placeRanks

    init {
        _dateRanks.value = dateDummy
        _placeRanks.value = placeDummy
    }

    fun selectPlace(id : Int){
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

    fun confirmDate(id : Int) {
        _dateRanks.value = dateRanks.value.map {
            if(id == it.id){
                if(it.confirmed) it.copy(confirmed = false)
                else it.copy(confirmed = true)
            } else it.copy(confirmed = false)
        }
    }

    fun confirmPlace(id : Int) {
        _placeRanks.value = placeRanks.value.map {
            if(id == it.id){
                if(it.confirmed) it.copy(confirmed = false)
                else it.copy(confirmed = true)
            } else it.copy(confirmed = false)
        }
    }
}

val dateDummy = listOf(
    DateRankUiModel(
        id = 1,
        ranking = 1,
        date = "23.01.20",
        time = "아침",
        count = 3,
        progress = 100 * 3 / 5,
        selected = true
    ),
    DateRankUiModel(
        id = 2,
        ranking = 1,
        date = "23.01.20",
        time = "아침",
        count = 3,
        progress = 100 * 3 / 5,
        selected = true
    ),
    DateRankUiModel(
        id = 3,
        ranking = 1,
        date = "23.01.20",
        time = "아침",
        count = 3,
        progress = 100 * 3 / 5,
        selected = true
    )
)

val placeDummy = listOf(
    PlaceRankUiModel(
        id = 1,
        ranking = 2,
        name = "강남역",
        count = 3,
        progress = 100 * 3 / 5
    ),
    PlaceRankUiModel(
        id = 2,
        ranking = 4,
        name = "강남역",
        count = 3,
        progress = 100 * 3 / 5
    ),
    PlaceRankUiModel(
        id = 3,
        ranking = 5,
        name = "강남역",
        count = 3,
        progress = 100 * 3 / 5
    )
)