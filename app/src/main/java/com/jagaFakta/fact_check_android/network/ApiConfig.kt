package com.jagaFakta.fact_check_android.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiConfig {
    companion object {
        private fun instanceRetro(): Retrofit {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://jagafakta-production.up.railway.app/")
                .addConverterFactory(GsonConverterFactory.create())
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