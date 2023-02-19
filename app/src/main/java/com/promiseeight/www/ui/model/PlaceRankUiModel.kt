package com.promiseeight.www.ui.model

data class PlaceRankUiModel(
    override val id: Int,
    override val ranking : Int,
    override val type: RankType = RankType.PLACE,
    override val progress: Int,
    val name: String,
    val count : Int,
    val selected : Boolean = false
) : RankModel(id,ranking,type,progress)