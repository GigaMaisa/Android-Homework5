package com.example.homeworkfive

import android.annotation.SuppressLint
import android.content.Intent
import android.text.SpannableString
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.text.style.UnderlineSpan
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.example.homeworkfive.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity(), BaseSteps {
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var emailInput: AppCompatEditText
    private lateinit var passwordInput: AppCompatEditText
    private lateinit var nextButton: AppCompatButton
    private lateinit var userNameInput: AppCompatEditText
    private lateinit var backButton: ImageView
    private lateinit var textAgreement: AppCompatTextView
    private lateinit var registerButton: AppCompatButton
    private lateinit var user: FirebaseUser
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        emailInput = binding.etEmail
        passwordInput = binding.etPass
        nextButton = binding.btnNext
        userNameInput = binding.etUsername
        backButton = binding.btnBack
        textAgreement = binding.tvAgreement
        registerButton = binding.btnRegister

        nextButton.setOnClickListener {
            val email: String? = getValidEmail(emailInput, this)
            val password: String? = getValidPassword(passwordInput, this)

            email?.let { _ ->
                password?.let { _ ->
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                user = auth.currentUser!!
                                changeDesign()
                            } else {
                                showToast(baseContext, "Authentication failed.")
                            }
                        }
                }
            }
        }

        registerButton.setOnClickListener {
            val userName: String? = getTextFromView(userNameInput, this)
            userName?.let {
                val profileUpdates = userProfileChangeRequest {
                    displayName = userName
                }

                user = Firebase.auth.currentUser!!
                user.updateProfile(profileUpdates).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, LoggedInActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onBackButtonClick(view: View) {
        finish()
    }

    @SuppressLint("SetTextI18n")
    private fun changeDesign() {
        userNameInput.visibility = View.VISIBLE
        emailInput.visibility = View.GONE
        passwordInput.visibility = View.GONE
        nextButton.visibility = View.GONE
        registerButton.visibility = View.VISIBLE

        textAgreement.visibility = View.VISIBLE
        backButton.visibility = View.INVISIBLE
    }
}