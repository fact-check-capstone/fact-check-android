package com.jagaFakta.fact_check_android.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiConfig {
    companion object {
        private fun instanceRetro(): Retrofit {
//            val retrofit = Retrofit.Builder()
//                .baseUrl("https://capstone-v1-s23lwelwoa-et.a.run.app/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//            return retrofit

            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://main-backend-dot-jagafakta-capstone.et.r.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit
        }

        fun PredictService(): PredictService {
            return instanceRetro().create(PredictService::class.java)
        }
        fun HistoriService(): HistoriPredictService{
            return instanceRetro().create(HistoriPredictService::class.java)
        }
    }
}