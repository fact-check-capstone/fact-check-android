package com.jagaFakta.fact_check_android.network

import com.jagaFakta.fact_check_android.model.ModelPredict
import com.jagaFakta.fact_check_android.network.response.PredictHistoryResponse
import com.jagaFakta.fact_check_android.network.response.PredictResponse
import com.jagaFakta.fact_check_android.network.response.responseDeletePredict
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface PredictService {
//    @FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("predict/{userId}")
    fun Predict(
        @Path("userId") id:String,
        @Body requestBody: ModelPredict
    ): retrofit2.Call<PredictResponse>

    @Headers("Content-Type: application/json")
    @DELETE("predict/{Id}")
    suspend fun Delete(
        @Path("Id") id:String,
    ): responseDeletePredict
}

interface HistoriPredictService {
    @Headers("Content-Type: application/json")
    @GET("predict/{userId}")
    fun Histori(
        @Path("userId") id:String,
    ): retrofit2.Call<PredictHistoryResponse>
}