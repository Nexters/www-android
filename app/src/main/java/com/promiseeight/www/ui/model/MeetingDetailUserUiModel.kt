package com.promiseeight.www.ui.model

import com.promiseeight.www.ui.model.enums.UserImage

data class MeetingDetailUserUiModel(
    val id : String,
    val isHeader : Boolean,
    val headerTitle : String = "",
    val rank : Int = 0,
    val count : Int = 0,
    val userName : String = "",
    val userImage : UserImage
)

