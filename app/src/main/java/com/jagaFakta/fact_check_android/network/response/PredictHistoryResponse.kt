package com.jagaFakta.fact_check_android.network.response

import com.google.gson.annotations.SerializedName

data class PredictHistoryResponse(

	@field:SerializedName("data")
	val data: List<History>,

	@field:SerializedName("message")
	val message: String? = null
)

data class History(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String,
)
