package com.promiseeight.www.data.model.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: T
)