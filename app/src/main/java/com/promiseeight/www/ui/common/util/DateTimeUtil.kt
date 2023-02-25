package com.promiseeight.www.ui.common.util

import com.promiseeight.www.domain.model.PromiseTime
import com.promiseeight.www.ui.model.TimeUiModel
import org.joda.time.DateTime
import timber.log.Timber

object DateTimeUtil {
    fun getDateTimeTableSize(startDate : DateTime, endDate : DateTime) : Int{
        return (endDate.dayOfYear - startDate.dayOfYear + 1) / 4 + if((endDate.dayOfYear - startDate.dayOfYear + 1) % 4 == 0 ) 0 else 1
    }

    fun getTimeUiModelList(startDate : DateTime, endDate : DateTime, pagePosition : Int): List<TimeUiModel> {
        val list = mutableListOf<TimeUiModel>()
        for(time in 0 .. 3){
            for(day in 0 .. 3){
                list.add(
                    TimeUiModel(
                        id = System.currentTimeMillis() + pagePosition * 16 + 4 * time + day,
                        date = startDate.plusDays(pagePosition * 4 + day),
                        time = PromiseTime.values().get(time),
                        selected = false,
                        disabled = startDate.plusDays(pagePosition * 4 + day).dayOfYear > endDate.dayOfYear
                    )
                )
                Timber.d("asd ${pagePosition * 4 + 4 * time + day}")
            }
        }
        return list
    }
}