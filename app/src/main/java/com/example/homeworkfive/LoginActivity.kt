package com.example.homeworkfive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.example.homeworkfive.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity(), BaseSteps {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var emailEditText: AppCompatEditText
    private lateinit var passwordEditText: AppCompatEditText
    private lateinit var loginButton: AppCompatButton
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        emailEditText = binding.etEmail
        passwordEditText = binding.etPass
        loginButton = binding.btnLogin
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()

        loginButton.setOnClickListener {
            val email: String? = getValidEmail(emailEditText, this)
            val password: String? = getValidPassword(passwordEditText, this)

            email?.let { _ ->
                password?.let {_ ->
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) {task ->
                            if (task.isSuccessful) {
                                auth.currentUser
                                startActivity(Intent(this, LoggedInActivity::class.java))
                            } else {
                                showToast(baseContext, "Authentication failed.")
                            }
                        }
                }
            }
        }
    }

    override fun onBackButtonClick(view: View) {
        finish()
    }
}