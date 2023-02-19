package com.promiseeight.www.ui.model

abstract class RankModel (
    open val id : Int,
    open val ranking : Int,
    open val type : RankType,
    open val progress: Int
)

enum class RankType {
    DATE,
    PLACE
}
