package com.promiseeight.www.notification

data class WwwNotificationModel(
    val id : Long,
    val contentType : ContentType,
    val contentId : Long,
    val text : String,
    val title : String

)

enum class ContentType {
    MEETING
}