package com.jagaFakta.fact_check_android.network.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val status: String
)
data class Data(

	@field:SerializedName("result")
	val prediction: String
)
