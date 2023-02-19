package com.promiseeight.www.ui.model

data class DateRankUiModel(
    override val id: Int,
    override val ranking : Int,
    override val type: RankType = RankType.DATE,
    override val progress: Int,
    val count : Int,
    val date : String,
    val time : String,
) : RankModel(id,ranking,type,progress)

