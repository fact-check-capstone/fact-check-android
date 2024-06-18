package com.jagaFakta.fact_check_android.ui.resultPredict

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jagaFakta.fact_check_android.R
import com.jagaFakta.fact_check_android.databinding.ActivityResultBinding
import com.jagaFakta.fact_check_android.network.ApiConfig
import com.jagaFakta.fact_check_android.ui.historyPredict.HistoryActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val result = bundle!!.getString("result")
        val text = bundle.getString("text")
        val idHistory = bundle.getString("idHistori")

        if (idHistory == null){
            binding.btnHps.visibility = View.GONE
        }

        binding.btnHps.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val retrofit = ApiConfig.PredictService().Delete(idHistory!!)
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(this@ResultActivity, "${retrofit.status}", Toast.LENGTH_SHORT).show()
//                        startActivity(Intent(this@ResultActivity,HistoryActivity::class.java))
                        finish()
//                        finishActivity(11)
                    }
            }
        }

        binding.tvHasil.text = result
        binding.tvText.text = text

        if (result != "Terindikasi hoax"){
            binding.imgResult.setImageResource(R.drawable.icon_ceklis)
        }else{
            binding.imgResult.setImageResource(R.drawable.icon_silang)

        }
    }
}