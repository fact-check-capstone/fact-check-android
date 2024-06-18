package com.jagaFakta.fact_check_android.network.response

import com.google.gson.annotations.SerializedName

data class responseDeletePredict(
    @field:SerializedName("message")
    val status: String
)