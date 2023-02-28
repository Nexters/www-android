package com.promiseeight.www.ui.meeting.detail

data class MeetingDetailState(
    val meetingStatus : MeetingStatus = MeetingStatus.WAITING
)

enum class MeetingStatus{
    WAITING,
    VOTING,
    VOTED,
    DONE,
    CONFIRMED,
    TERMINATED // 완전 끝
}