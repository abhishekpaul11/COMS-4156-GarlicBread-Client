package com.garlicbread.includify

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.garlicbread.includify.databinding.ActivityLoginBinding
import com.garlicbread.includify.models.request.LoginRequest
import com.garlicbread.includify.retrofit.RetrofitInstance
import com.garlicbread.includify.util.Constants.Companion.API
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_ACCESS_TOKEN
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_EMAIL
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_PASSWORD
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.trim().toString()
            val password = binding.etPassword.text.trim().toString()

            if (email.isNotBlank() && password.isNotBlank()) login(email, password)
        }
    }

    private fun login(email: String, password: String) {
        RetrofitInstance.api.login(
            LoginRequest(
                email,
                password
            )
        ).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    response.body()
                        ?.let { saveDetails(email, password, it) }
                    val newIntent = Intent(this@LoginActivity, DashboardActivity::class.java)
                    startActivity(newIntent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Invalid Credentials !!!", Toast.LENGTH_LONG).show()
                    Log.e(API, "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Server down, please try again.", Toast.LENGTH_LONG).show()
                Log.e(API, "Failure: ${t.message}")
            }
        })
    }

    private fun saveDetails(
        email: String,
        password: String,
        accessToken: String
    ) {
        val prefs = this.getSharedPreferences(SHARED_PREF_TAG, MODE_PRIVATE)
        prefs.edit().putString(SHARED_PREF_EMAIL, email).apply()
        prefs.edit().putString(SHARED_PREF_PASSWORD, password).apply()
        prefs.edit().putString(SHARED_PREF_ACCESS_TOKEN, accessToken).apply()
    }

}