package com.promiseeight.www.data.model.response

import com.promiseeight.www.domain.model.PlaceVote

data class EntryResponse(
    val key : String,
    val value : List<String>
)

fun EntryResponse.toPlaceVote() = PlaceVote(
    placeName = key,
    userNameList = value
)