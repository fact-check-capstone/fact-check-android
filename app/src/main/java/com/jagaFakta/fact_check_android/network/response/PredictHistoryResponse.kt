package com.jagaFakta.fact_check_android.network.response

import com.google.gson.annotations.SerializedName

data class PredictHistoryResponse(

	@field:SerializedName("data")
	val data: List<History>
)

data class History(

	@field:SerializedName("result")
	val prediction: String? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

)
