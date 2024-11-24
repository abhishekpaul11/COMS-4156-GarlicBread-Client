package com.garlicbread.includify

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.garlicbread.includify.databinding.ActivityDashboardBinding
import com.garlicbread.includify.models.response.User
import com.garlicbread.includify.retrofit.RetrofitInstance
import com.garlicbread.includify.util.Constants.Companion.API
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_NAME
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_TAG
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_USER_ID
import com.garlicbread.includify.util.HelperMethods.Companion.getAccessToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(SHARED_PREF_NAME)
        val accessToken = getAccessToken(this)

        if (name.isNullOrEmpty()) {
            fetchDetails(accessToken)
        }
        else {
            binding.name.text = name
        }
    }

    private fun fetchDetails(accessToken: String) {
        RetrofitInstance.api.fetchUserDetails("Bearer $accessToken").enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    response.body()?.let { saveDetails(it.name, it.id) }
                    binding.name.text = response.body()?.name ?: "Includify User"
                } else {
                    Log.e(API, "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(API, "Failure: ${t.message}")
            }
        })
    }

    private fun saveDetails(
        name: String,
        userId: String
    ) {
        val prefs = this.getSharedPreferences(SHARED_PREF_TAG, MODE_PRIVATE)
        prefs.edit().putString(SHARED_PREF_USER_ID, userId).apply()
        prefs.edit().putString(SHARED_PREF_NAME, name).apply()
    }
}