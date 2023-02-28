package com.promiseeight.www.ui.model

import com.promiseeight.www.domain.model.PlaceVote
import com.promiseeight.www.domain.model.UserPromisePlace

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
    val userVoted : Boolean = false,
    val placeId : Long = 0
) : RankModel(id,ranking,type,progress,selected,count,confirmed,tied)

fun getPlaceRankUiModelList(userPromisePlaceList : List<UserPromisePlace>,userVoteList : List<PlaceVote>,userVoted : Boolean = false) : List<PlaceRankUiModel> {

    val map = userVoteList.map {
        it.placeName to it.userNameList.size
    }.toMap()
    var rank = 1
    var lastUserCount = 0
    val firstRankCount = if(userVoteList.isNotEmpty()) userVoteList.maxBy {
        it.userNameList.size
    }.userNameList.size else 1
    val tiedMap : HashMap<Int,Boolean> = hashMapOf()
    val list : MutableList<PlaceRankUiModel> = mutableListOf()

    userPromisePlaceList.sortedByDescending { map.get(it.promisePlace) }.forEachIndexed { index, userPromisePlace ->
        if(index == 0) lastUserCount = map[userPromisePlace.promisePlace] ?: 0
        if(lastUserCount != (map[userPromisePlace.promisePlace] ?: 0)){
            rank++
            lastUserCount = (map[userPromisePlace.promisePlace] ?: 0)
        }
        var tied = false
        if(tiedMap.contains(rank)){
            tied = true
        }else {
            tiedMap.set(rank,true)
        }
        list.add(
            PlaceRankUiModel(
                id =userPromisePlace.promisePlace + map[userPromisePlace.promisePlace],
                ranking = rank,
                count = (map[userPromisePlace.promisePlace] ?: 0),
                progress = 100 * (map[userPromisePlace.promisePlace] ?: 0) / firstRankCount,
                tied = tied,
                type = RankType.PLACE,
                name = userPromisePlace.promisePlace,
                placeId = userPromisePlace.placeId
            )
        )
    }
    return list
}