package com.jagaFakta.fact_check_android.ui.predict

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.jagaFakta.fact_check_android.R
import com.jagaFakta.fact_check_android.databinding.ActivityHomeBinding
import com.jagaFakta.fact_check_android.model.ModelPredict
import com.jagaFakta.fact_check_android.network.ApiConfig
import com.jagaFakta.fact_check_android.network.response.PredictHistoryResponse
import com.jagaFakta.fact_check_android.network.response.PredictResponse
import com.jagaFakta.fact_check_android.ui.historyPredict.HistoryActivity
import com.jagaFakta.fact_check_android.ui.resultPredict.ResultActivity
import com.jagaFakta.fact_check_android.ui.login.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    lateinit var binging: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var idUser:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binging = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binging.root)

        // initiate firebase
        auth = Firebase.auth
        val user = auth.currentUser
        user?.let {
            idUser = it.uid
        }

        setbtnScan()

    }

    
    private fun setbtnScan(){
        binging.btnScan.setOnClickListener { 
            val input = binging.textScan.text.toString()
            if (input.isNotEmpty()){
                loading(true)
                CoroutineScope(Dispatchers.IO).launch{
                    predict(input)
                }
            }else{
                loading(false)
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

                        val bundle = Bundle()
                        bundle.putString("result", responseBody.data.prediction)
                        bundle.putString("text", responseBody.data.text)

                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                        CoroutineScope(Dispatchers.Main).launch {
                            loading(false)
                        }
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

    private fun loading(lod:Boolean){
        if (lod == true){
            binging.ProgressBar.visibility = View.VISIBLE
            binging.btnScan.isEnabled = false
        }else{
            binging.ProgressBar.visibility = View.GONE
        }
    }

}