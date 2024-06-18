package com.jagaFakta.fact_check_android.ui.historyPredict

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jagaFakta.fact_check_android.network.HistoriPredictService

class HistoryViewModelFactory(private val data: HistoriPredictService): ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(historyViewModel::class.java)){
                return historyViewModel(data) as T
            }

            throw IllegalArgumentException("Unknown View Model Class: "+ modelClass.name)
        }
}