package com.promiseeight.www.ui.model

import com.promiseeight.www.domain.model.UserPromiseTime

data class DateRankUiModel(
    override val id: String,
    override val ranking : Int,
    override val type: RankType = RankType.DATE,
    override val progress: Int,
    override val selected : Boolean = false,
    override val count : Int,
    override val confirmed : Boolean = false,
    override val tied : Boolean = false,
    val date : String,
    val time : String,
) : RankModel(id,ranking,type,progress,selected,count,confirmed, tied)

fun getDateRankUiModelList(userPromiseDateTimeList : List<UserPromiseTime>) : List<DateRankUiModel> {
    var rank = 1
    var lastUserCount = 0
    val firstRankCount = userPromiseDateTimeList.maxBy {
        it.userInfoList.size
    }
    val tiedMap : HashMap<Int,Boolean> = hashMapOf()
    val list : MutableList<DateRankUiModel> = mutableListOf()
    userPromiseDateTimeList.forEachIndexed { index, userPromiseTime ->
        if(index == 0) lastUserCount = userPromiseTime.userInfoList.size
        if(lastUserCount != userPromiseTime.userInfoList.size){
            rank++
            lastUserCount = userPromiseTime.userInfoList.size
        }
        var tied = false
        if(tiedMap.contains(rank)){
            tied = true
        }else {
            tiedMap.set(rank,true)
        }
        list.add(
            DateRankUiModel(
                id =userPromiseTime.promiseDate + userPromiseTime.promiseTime.name,
                ranking = rank,
                count = userPromiseTime.userInfoList.size,
                date = userPromiseTime.promiseDate,
                progress = 100 * userPromiseTime.userInfoList.size / firstRankCount.userInfoList.size,
                time = userPromiseTime.promiseTime.korean,
                tied = tied
            )
        )
    }
    return list
}

