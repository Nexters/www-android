package com.promiseeight.www.ui.model

abstract class RankModel (
    open val id : String,
    open val ranking : Int,
    open val type : RankType,
    open val progress: Int,
    open val selected : Boolean,
    open val count : Int,
    open val confirmed : Boolean,
    open val tied : Boolean
)

enum class RankType {
    DATE,
    PLACE
}
