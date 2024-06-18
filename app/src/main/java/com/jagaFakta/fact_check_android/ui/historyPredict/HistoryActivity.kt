package com.jagaFakta.fact_check_android.ui.historyPredict

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.jagaFakta.fact_check_android.R
import com.jagaFakta.fact_check_android.databinding.ActivityHistoryBinding
import com.jagaFakta.fact_check_android.network.ApiConfig
import com.jagaFakta.fact_check_android.network.response.History
import com.jagaFakta.fact_check_android.network.response.PredictHistoryResponse
import com.jagaFakta.fact_check_android.ui.login.MainActivity
import com.jagaFakta.fact_check_android.ui.predict.HomeActivity
import com.jagaFakta.fact_check_android.ui.profile.ProfileActivity
import com.jagaFakta.fact_check_android.ui.resultPredict.ResultActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var viewModel: historyViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var idUser:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        getUserInfo()
        predict()
        getDataHistoty()
        profile()
    }
    override fun onStart() {
        super.onStart()
        getDataHistoty()

        val currentUser = auth.currentUser
        if (currentUser == null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun profile(){
        binding.btnProfile.setOnClickListener {
            startActivity(Intent(this,ProfileActivity::class.java))
        }
    }
    private fun getUserInfo(){
        val user = auth.currentUser
        user?.let {
            idUser = it.uid
            val name = it.displayName

            binding.lbName.text = name
        }
    }

    private fun predict(){
        binding.btnScan.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }
    }
    private fun getDataHistoty(){
        showLoading(true)
        viewModel =ViewModelProvider(this,(HistoryViewModelFactory(ApiConfig.HistoriService())))[historyViewModel::class.java]
        viewModel.getHistory(idUser)
        viewModel.histori.observe(this){
            setRecycleView(it)
        }
    }
    private fun setRecycleView(histori: List<History>){
        val layoutManager = LinearLayoutManager(this)
        binding.rvHistory.layoutManager = layoutManager
//        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
//        binding.rvHistory.addItemDecoration(itemDecoration)

        val adapter = historyAdapter(histori)
        binding.rvHistory.adapter = adapter

        showLoading(false)

        adapter.setOnItemClickCallback(object :historyAdapter.OnItemClickCallBack{
            override fun onItemClicked(data: History) {
                val intent = Intent(this@HistoryActivity, ResultActivity::class.java)
                val bundle = Bundle()
                bundle.putString("result", data.result)
                bundle.putString("text", data.text)
                bundle.putString("idHistori",data.id)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}