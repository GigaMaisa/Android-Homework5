package com.example.homeworkfive

import android.content.Context
import android.util.Patterns
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.util.regex.Pattern

interface BaseSteps {
    fun showToast(context: Context, message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    fun isPasswordValid(password: String): Boolean {
        val passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"
        val pattern = Pattern.compile(passwordPattern)
        return pattern.matcher(password).matches()
    }

    fun onBackButtonClick(view: View)

    fun getValidPassword(viewElement: TextView, context: Context): String? {
        val userPass: String = viewElement.text.toString()

        if (userPass.isEmpty() || !isPasswordValid(userPass)) {
            showToast(context, "Password must be strong")

            return null
        }

        return userPass
    }

    fun getValidEmail(viewElement: TextView, context: Context): String? {
        val userEmail: String = viewElement.text.toString()

        if (userEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            showToast(context, "Type in valid email address")
            return null
        }

        return userEmail
    }

    fun getTextFromView(viewElement: TextView, context: Context): String? {
        val userName: String = viewElement.text.toString()

        if (userName.isEmpty()) {
            showToast(context, "Type in valid data")
            return null
        }

        return userName
    }
}