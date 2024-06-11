package com.jagaFakta.fact_check_android.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jagaFakta.fact_check_android.R
import com.jagaFakta.fact_check_android.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = intent.getStringExtra("result")

        binding.tvHasil.text = result

        if (result != "fact"){
            binding.imgResult.setImageResource(R.drawable.icon_ceklis)
        }else{
            binding.imgResult.setImageResource(R.drawable.icon_silang)

        }
    }
}