package com.jagaFakta.fact_check_android.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.jagaFakta.fact_check_android.R
import com.jagaFakta.fact_check_android.databinding.ActivityHomeBinding
import com.jagaFakta.fact_check_android.model.ModelPredict
import com.jagaFakta.fact_check_android.network.ApiConfig
import com.jagaFakta.fact_check_android.network.HistoriPredictService
import com.jagaFakta.fact_check_android.network.response.PredictHistoryResponse
import com.jagaFakta.fact_check_android.network.response.PredictResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    lateinit var binging: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var idUser:String

//    companion object{
//        data class data(
//            val text:String
//        )
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binging = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binging.root)

        // initiate firebase
        auth = Firebase.auth

        setUserInfo()
        setbtnScan()
        histori()
        btnLogout()
    }
    private fun setUserInfo(){
        val user = auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email
            idUser = it.uid

            binging.lbName.text = name
        }
    }
    
    private fun setbtnScan(){
        binging.btnScan.setOnClickListener { 
            val input = binging.textScan.text.toString()
            if (input.isNotEmpty()){
                CoroutineScope(Dispatchers.IO).launch{
                    predict(input)
                }

            }else{
                Toast.makeText(this, "Please fill in the news you want to check for hoaxes!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun predict(inputPredict:String){
        var requestBody = ModelPredict(inputPredict)

        val client = ApiConfig.PredictService().Predict(idUser,requestBody)
        client.enqueue(object : Callback<PredictResponse>{
            override fun onResponse(
                call: Call<PredictResponse>,
                response: Response<PredictResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.e(TAG, "Succes: ${responseBody}")
                        val intent = Intent(this@HomeActivity, ResultActivity::class.java)
                        intent.putExtra("result",responseBody.data.prediction)
                        startActivity(intent)
                    }
                } else {
                    Log.e(TAG, "gagal: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun histori(){
        val client = ApiConfig.HistoriService().Histori(idUser)
        client.enqueue(object : Callback<PredictHistoryResponse>{
            override fun onResponse(
                call: Call<PredictHistoryResponse>,
                response: Response<PredictHistoryResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.e(TAG, "Succes fetch histori: ${responseBody}")
                    }
                } else {
                    Log.e(TAG, "gagal histori: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<PredictHistoryResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
    private fun btnLogout(){
        binging.btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}