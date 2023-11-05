package com.example.homeworkfive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homeworkfive.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginButtonClick()
        registerButtonClick()
    }

    override fun onStart() {
        super.onStart()
        auth()
    }

    override fun onResume() {
        super.onResume()
        auth()
    }

    private fun loginButtonClick() {
        binding.btnLogin.setOnClickListener {
            if (user != null) {
                startActivity(Intent(this, LoggedInActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }

    private fun registerButtonClick() {
        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }

    private fun auth() {
        user = Firebase.auth.currentUser
        user?.let {
            binding.username.text = it.displayName
            binding.email.text = it.email
        }
    }
}