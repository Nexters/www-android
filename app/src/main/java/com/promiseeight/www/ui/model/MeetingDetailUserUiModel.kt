package com.promiseeight.www.ui.model

import com.promiseeight.www.domain.model.User
import com.promiseeight.www.ui.model.enums.CharacterType

data class MeetingDetailUserUiModel(
    val id : String,
    val isHeader : Boolean,
    val headerTitle : String = "",
    val rank : Int = 0,
    val count : Int = 0,
    val userName : String = "",
    val characterType : CharacterType,
    val isHost : Boolean = false
)

fun User.toMeetingDetailUserUiModel() = MeetingDetailUserUiModel(
    id = joinedUserName,
    isHeader = false,
    characterType = CharacterType.valueOf(characterType),
    isHost = CharacterType.valueOf(characterType) == CharacterType.CREATOR,
    userName = joinedUserName
)
