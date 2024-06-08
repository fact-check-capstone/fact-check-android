package com.jagaFakta.fact_check_android.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.jagaFakta.fact_check_android.R
import com.jagaFakta.fact_check_android.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binging: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binging = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binging.root)

        // initiate firebase
        auth = Firebase.auth

        setUserInfo()
        btnLogout()
    }

    private fun btnLogout(){
        binging.btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    private fun setUserInfo(){
        val user = auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email

            binging.lbEmail.text = email
            binging.lbName.text = name
        }
    }
}