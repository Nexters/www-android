package com.promiseeight.www.ui.model

abstract class RankModel (
    open val id : Int,
    open val ranking : Int,
    open val type : RankType,
    open val progress: Int,
    open val selected : Boolean,
    open val count : Int,
    open val confirmed : Boolean
)

enum class RankType {
    DATE,
    PLACE
}
