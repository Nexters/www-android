package com.promiseeight.www.data.model.exception

fun getWwwException(code : Int) : Exception {
    return when(code) {
        403 -> Exception("접근이 거부되었어요")
        500 -> Exception("서버에서 에러가 발생했어요")
        1000 -> Exception("서버 요청 중 에러가 발생했어요")
        3001 -> Exception("이미 참여한 약속방이에요")
        4000 -> Exception("존재하지 않는 방이에요")
        4001 -> Exception("존재하지 않는 유저에요")
        5000 -> Exception("이미 투표가 시작된 방이에요")
        else -> Exception("에러가 발생하였어요")
    }
}
