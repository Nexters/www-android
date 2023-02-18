package com.promiseeight.www.ui.meeting.detail

import androidx.lifecycle.ViewModel
import com.promiseeight.www.ui.model.DateRankUiModel
import com.promiseeight.www.ui.model.PlaceRankUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MeetingDetailViewModel (

) : ViewModel() {

    private val _dateRanks = MutableStateFlow(listOf<DateRankUiModel>())
    val dateRanks : StateFlow<List<DateRankUiModel>> get() = _dateRanks

    private val _placeRanks = MutableStateFlow(listOf<PlaceRankUiModel>())
    val placeRanks : StateFlow<List<PlaceRankUiModel>> get() = _placeRanks

    init {
        _dateRanks.value = dateDummy
        _placeRanks.value = placeDummy
    }
}

val dateDummy = listOf(
    DateRankUiModel(
        id = 1,
        ranking = 1,
        date = "23.01.20",
        time = "아침",
        count = 3,
        progress = 100 * 3 / 5
    ),
    DateRankUiModel(
        id = 2,
        ranking = 2,
        date = "23.01.21",
        time = "아침",
        count = 3,
        progress = 100 * 3 / 5
    ),
    DateRankUiModel(
        id = 3,
        ranking = 2,
        date = "23.01.19",
        time = "점심",
        count = 3,
        progress = 100 * 3 / 5
    ),DateRankUiModel(
        id = 4,
        ranking = 3,
        date = "23.01.22",
        time = "아침",
        count = 2,
        progress = 100 * 2 / 5
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
        ranking = 2,
        name = "내방역",
        count = 3,
        progress = 100 * 3 / 5
    ),
    PlaceRankUiModel(
        id = 3,
        ranking = 3,
        name = "이수역",
        count = 0,
        progress = 100 * 0 / 5
    ),
    PlaceRankUiModel(
        id = 4,
        ranking = 2,
        name = "강남역",
        count = 3,
        progress = 100 * 3 / 5
    ),
    PlaceRankUiModel(
        id = 5,
        ranking = 2,
        name = "내방역",
        count = 3,
        progress = 100 * 3 / 5
    ),
    PlaceRankUiModel(
        id = 6,
        ranking = 3,
        name = "이수역",
        count = 0,
        progress = 100 * 0 / 5
    ),
    PlaceRankUiModel(
        id = 7,
        ranking = 2,
        name = "강남역",
        count = 3,
        progress = 100 * 3 / 5
    ),
    PlaceRankUiModel(
        id = 8,
        ranking = 2,
        name = "내방역",
        count = 3,
        progress = 100 * 3 / 5
    ),
    PlaceRankUiModel(
        id = 9,
        ranking = 3,
        name = "이수역",
        count = 0,
        progress = 100 * 0 / 5
    ),
    PlaceRankUiModel(
        id = 12,
        ranking = 2,
        name = "강남역",
        count = 3,
        progress = 100 * 3 / 5
    ),
    PlaceRankUiModel(
        id = 18,
        ranking = 2,
        name = "내방역",
        count = 3,
        progress = 100 * 3 / 5
    ),
    PlaceRankUiModel(
        id = 19,
        ranking = 3,
        name = "이수역",
        count = 0,
        progress = 100 * 0 / 5
    )
)