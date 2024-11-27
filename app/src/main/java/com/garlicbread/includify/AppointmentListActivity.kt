package com.garlicbread.includify

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.garlicbread.includify.adapters.AppointmentAdapter
import com.garlicbread.includify.databinding.ActivityAppointmentListBinding
import com.garlicbread.includify.retrofit.RetrofitInstance
import com.garlicbread.includify.util.Constants.Companion.API
import com.garlicbread.includify.util.HelperMethods
import com.garlicbread.includify.util.HelperMethods.Companion.getAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppointmentListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppointmentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAppointmentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val accessToken = getAccessToken(this@AppointmentListActivity)
                val response = RetrofitInstance.api.fetchAllAppointments("Bearer $accessToken").execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                        binding.noAppointmentsPresent.isVisible = false
                        binding.recyclerView.isVisible = true
                        binding.recyclerView.layoutManager = LinearLayoutManager(this@AppointmentListActivity)
                        binding.recyclerView.adapter = AppointmentAdapter(response.body()!!.sortedBy { it.organisation.name }, this@AppointmentListActivity)
                    }
                    else if (response.code() == 401) {
                        HelperMethods.signOut(this@AppointmentListActivity)
                    }
                    else {
                        binding.noAppointmentsPresent.isVisible = true
                        binding.recyclerView.isVisible = false
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@AppointmentListActivity,
                        "Server down, please try again.",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e(API, "Failure: ${e.message}")
                }
            }
        }
    }
}