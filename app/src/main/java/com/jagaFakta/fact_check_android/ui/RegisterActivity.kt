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
import com.google.firebase.auth.userProfileChangeRequest
import com.jagaFakta.fact_check_android.R
import com.jagaFakta.fact_check_android.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.btnSignUp.setOnClickListener {
            val nma = binding.inName.text.toString()
            val eml = binding.inEmail.text.toString()
            val pas = binding.inPass.text.toString()

            createAccountEmailpas(eml, pas, nma)
        }
    }

    private fun createAccountEmailpas(eml:String, pas:String, nma:String){
        auth.createUserWithEmailAndPassword(eml, pas)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    addUserName(nma)
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(baseContext, "buat akun sukses", Toast.LENGTH_SHORT,).show()
                    auth.signOut()
                    finish()
                    startActivity(Intent(this,RegisterActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT,).show()
                }
            }
    }

    fun addUserName(name:String){
        val user = auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                }
            }
    }
}