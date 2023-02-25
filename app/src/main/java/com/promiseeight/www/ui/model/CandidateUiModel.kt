package com.promiseeight.www.ui.model

data class CandidateUiModel(
    var id : Long = 0L,
    val title : String,
    val isPossibleDelete : Boolean = true
)