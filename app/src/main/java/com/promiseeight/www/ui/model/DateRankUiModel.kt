package com.promiseeight.www.ui.model

data class DateRankUiModel(
    override val id: Int,
    override val ranking : Int,
    override val type: RankType = RankType.DATE,
    override val progress: Int,
    override val selected : Boolean = false,
    override val count : Int,
    override val confirmed : Boolean = false,
    val date : String,
    val time : String,
) : RankModel(id,ranking,type,progress,selected,count,confirmed)

