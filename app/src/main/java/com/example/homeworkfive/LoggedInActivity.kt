package com.example.homeworkfive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.example.homeworkfive.databinding.ActivityLoggedInBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoggedInActivity : AppCompatActivity(), BaseSteps {
    private lateinit var binding: ActivityLoggedInBinding
    private lateinit var username: AppCompatTextView
    private lateinit var email: AppCompatTextView
    private lateinit var signOutButton: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoggedInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = binding.tvUserName
        email = binding.tvEmail
        signOutButton = binding.btnSignOut

        val user = Firebase.auth.currentUser
        user?.let {
            username.text = it.displayName
            email.text = it.email
        }
    }

    override fun onStart() {
        super.onStart()
        binding.btnGoHome.setOnClickListener {
            goToMainActivity()
        }
    }

    fun onSignOut(view: View) {
        Firebase.auth.signOut()
        goToMainActivity()
    }

    override fun onBackButtonClick(view: View) {
        goToMainActivity()
    }

    private fun goToMainActivity() = startActivity(Intent(this, MainActivity::class.java))
}