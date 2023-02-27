package com.promiseeight.www.ui.model

import com.promiseeight.www.domain.model.UserPromisePlace
import com.promiseeight.www.domain.model.UserPromiseTime

data class PlaceRankUiModel(
    override val id: String,
    override val ranking : Int,
    override val type: RankType = RankType.PLACE,
    override val progress: Int,
    override val selected : Boolean = false,
    override val count : Int,
    override val confirmed : Boolean = false,
    override val tied : Boolean = false,
    val name: String,
    val meetingVotingStarted : Boolean = true,
    val userVoted : Boolean = false
) : RankModel(id,ranking,type,progress,selected,count,confirmed,tied)