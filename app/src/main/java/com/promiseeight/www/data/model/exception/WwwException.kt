package com.promiseeight.www.data.model

import com.promiseeight.www.data.model.response.BaseResponse

fun getWwwException(code : Int) : Exception {
    return when(code) {
        403 -> Exception("접근이 거부되었습니다.")
        1000 -> Exception("서버 요청 중 에러가 발생하였습니다.")
        4000 -> Exception("방이 존재하지 않습니다.")
        5000 -> Exception("이미 투표가 시작된 방입니다.")
        else -> Exception("에러가 발생하였습니다.")
    }
}
