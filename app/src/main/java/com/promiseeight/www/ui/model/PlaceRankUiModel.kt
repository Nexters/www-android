package com.promiseeight.www.ui.model

data class PlaceRankUiModel(
    override val id: Int,
    override val ranking : Int,
    override val type: RankType = RankType.PLACE,
    override val progress: Int,
    override val selected : Boolean = false,
    override val count : Int,
    override val confirmed : Boolean = false,
    val name: String,
    val userVoted : Boolean = false
) : RankModel(id,ranking,type,progress,selected,count,confirmed)