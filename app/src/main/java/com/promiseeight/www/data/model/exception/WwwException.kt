package com.promiseeight.www.data.model.exception

fun getWwwException(code : Int) : Exception {
    return when(code) {
        403 -> Exception("접근이 거부되었습니다.")
        500 -> Exception("서버에서 에러가 발생하였습니다.")
        1000 -> Exception("서버 요청 중 에러가 발생하였습니다.")
        3001 -> Exception("이미 참여한 약속방입니다.")
        4000 -> Exception("방이 존재하지 않습니다.")
        4001 -> Exception("존재하지 않는 유저입니다.")
        5000 -> Exception("이미 투표가 시작된 방입니다.")
        else -> Exception("에러가 발생하였습니다.")
    }
}
