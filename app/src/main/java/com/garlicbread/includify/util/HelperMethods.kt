package com.garlicbread.includify.util

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.garlicbread.includify.LoginActivity
import com.garlicbread.includify.models.request.LoginRequest
import com.garlicbread.includify.retrofit.RetrofitInstance

class HelperMethods {

    companion object {

        fun getAccessToken(context: Context): String {
            val sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_TAG, MODE_PRIVATE)
            val accessToken = sharedPreferences.getString(Constants.SHARED_PREF_ACCESS_TOKEN, null)

            if (accessToken.isNullOrBlank()) {
                val email = sharedPreferences.getString(Constants.SHARED_PREF_EMAIL, null)
                val password = sharedPreferences.getString(Constants.SHARED_PREF_PASSWORD, null)
                return if (email.isNullOrBlank() || password.isNullOrBlank()) {
                    signOut(context)
                    ""
                }
                else
                    refreshAccessToken(email, password, context)
            }
            else {
                return accessToken
            }
        }

        private fun refreshAccessToken(email: String, password: String, context: Context): String {
            val response = RetrofitInstance.api.login(
                LoginRequest(
                    email,
                    password
                )).execute()

            return if (response.isSuccessful && !response.body().isNullOrBlank()) {
                response.body()!!
            }
            else {
                Toast.makeText(context, "Session expired. Please login again.", Toast.LENGTH_LONG).show()
                signOut(context)
                ""
            }
        }

        private fun signOut(context: Context) {
            val newIntent = Intent(context, LoginActivity::class.java)
            newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(newIntent)
        }
    }
}