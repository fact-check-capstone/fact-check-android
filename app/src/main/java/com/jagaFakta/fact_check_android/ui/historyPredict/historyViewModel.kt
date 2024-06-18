package com.jagaFakta.fact_check_android.ui.historyPredict

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jagaFakta.fact_check_android.network.ApiConfig
import com.jagaFakta.fact_check_android.network.HistoriPredictService
import com.jagaFakta.fact_check_android.network.response.History
import com.jagaFakta.fact_check_android.network.response.PredictHistoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class historyViewModel(private val data: HistoriPredictService):ViewModel() {

    private var _hstri = MutableLiveData<List<History>>()
    var histori: LiveData<List<History>> = _hstri

    fun getHistory(id: String){
        val client = ApiConfig.HistoriService().Histori(id)
        client.enqueue(object : Callback<PredictHistoryResponse> {
            override fun onResponse(
                call: Call<PredictHistoryResponse>,
                response: Response<PredictHistoryResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.message == "berhasil" ){
                            _hstri.postValue(responseBody.data)
                        }
                    }
                } else {
                    Log.e(TAG, "gagal: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<PredictHistoryResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

}

