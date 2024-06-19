package com.jagaFakta.fact_check_android.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.jagaFakta.fact_check_android.R
import com.jagaFakta.fact_check_android.databinding.ActivityProfileBinding
import com.jagaFakta.fact_check_android.ui.login.MainActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityProfileBinding
    private lateinit var idUser:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        getUserInfo()
        btnLogout()
    }

    private fun getUserInfo(){
        val user = auth.currentUser
        user?.let {
            idUser = it.uid
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            binding.tvName.text = name
            binding.tvEmail.text = email
            Glide.with(this)
                .load(photoUrl)
                .error(R.drawable.profile)
                .into(binding.ImgProfile)
        }
    }

        private fun btnLogout(){
        binding.btnLogout.setOnClickListener {
            loading(true)
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun loading(lod:Boolean){
        if (lod == true){
            binding.ProgressBar.visibility = View.VISIBLE
        }else{
            binding.ProgressBar.visibility = View.GONE
        }
    }
}