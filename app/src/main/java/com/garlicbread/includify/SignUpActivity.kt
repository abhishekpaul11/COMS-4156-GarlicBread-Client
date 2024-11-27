package com.garlicbread.includify

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.garlicbread.includify.databinding.ActivitySignUpBinding
import com.garlicbread.includify.models.request.UserRequest
import com.garlicbread.includify.models.response.User
import com.garlicbread.includify.retrofit.RetrofitInstance
import com.garlicbread.includify.util.Constants.Companion.API
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_EMAIL
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_NAME
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_PASSWORD
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_TAG
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_USER_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedCats = mutableListOf<String>()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.getAllUserCategories().execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val cats = response.body()!!
                        if (cats.isNotEmpty()) {
                            binding.noCatsPresent.isVisible = false

                            val inflater = LayoutInflater.from(this@SignUpActivity)
                            cats.forEach {
                                val checkbox = inflater.inflate(R.layout.checkbox, binding.catsContainer, false) as CheckBox
                                checkbox.text = it.title
                                checkbox.setOnCheckedChangeListener { _, isChecked ->
                                    if (isChecked) selectedCats.add(it.id.toString())
                                    else selectedCats.remove(it.id.toString())
                                }
                                binding.catsContainer.addView(checkbox)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(API, "Failure: ${e.message}")
            }
        }

        binding.btnLogon.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val age = binding.etAge.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (name.isNotBlank() && age.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                signUp(name, age.toInt(), selectedCats, email, password)
            }
        }

    }

    private fun signUp(name: String, age: Int, cats: List<String>, email: String, password: String) {
        RetrofitInstance.api.createUser(
            UserRequest(
                name,
                age,
                email,
                password,
                cats
            )
        ).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    response.body()
                        ?.let { saveDetails(it.id, name, email, password) }
                    val newIntent = Intent(this@SignUpActivity, DashboardActivity::class.java)
                    newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(newIntent)
                } else {
                    Toast.makeText(this@SignUpActivity,
                        response.errorBody()?.string()
                            ?: "Unknown error occurred. Please try again.",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e(API, "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(
                    this@SignUpActivity,
                    "Server down, please try again.",
                    Toast.LENGTH_LONG
                ).show()
                Log.e(API, "Failure: ${t.message}")
            }
        })
    }

    private fun saveDetails(
        id: String,
        name: String,
        email: String,
        password: String,
    ) {
        val prefs = this.getSharedPreferences(SHARED_PREF_TAG, MODE_PRIVATE)
        prefs.edit().putString(SHARED_PREF_EMAIL, email).apply()
        prefs.edit().putString(SHARED_PREF_PASSWORD, password).apply()
        prefs.edit().putString(SHARED_PREF_USER_ID, id).apply()
        prefs.edit().putString(SHARED_PREF_NAME, name).apply()
    }
}