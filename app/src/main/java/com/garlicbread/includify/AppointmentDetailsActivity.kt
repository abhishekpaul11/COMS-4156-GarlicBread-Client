package com.garlicbread.includify

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.garlicbread.includify.databinding.ActivityAppointmentDetailsBinding
import com.garlicbread.includify.retrofit.RetrofitInstance
import com.garlicbread.includify.util.Constants.Companion.API
import com.garlicbread.includify.util.Constants.Companion.INTENT_EXTRAS_APPOINTMENT_ID
import com.garlicbread.includify.util.HelperMethods
import com.garlicbread.includify.util.HelperMethods.Companion.getAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppointmentDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppointmentDetailsBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAppointmentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(INTENT_EXTRAS_APPOINTMENT_ID)

        if (id.isNullOrBlank()) {
            Toast.makeText(this, "Some error has occurred. Restart the application.", Toast.LENGTH_LONG).show()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val accessToken = getAccessToken(this@AppointmentDetailsActivity)
                val response = RetrofitInstance.api.fetchAllAppointments("Bearer $accessToken").execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && !response.body().isNullOrEmpty()) {

                        val appointment = response.body()!!.find { it.id == id }
                        appointment?.let {
                            binding.organisation.text = it.organisation.name
                            binding.resDesc.text = it.resources.joinToString("\n") { res -> res.title }
                                .ifBlank { this@AppointmentDetailsActivity.resources.getText(R.string.no_resources_booked) }

                            binding.dateDesc.text = HelperMethods.formatDateString(it.date)
                            binding.startTimeDesc.text = HelperMethods.formatTime(it.timeStart)
                            binding.endTimeDesc.text = HelperMethods.formatTime(it.timeEnd)

                            binding.volunteerDesc.text = it.volunteer?.let { volunteer ->
                                "${volunteer.name}\n${volunteer.email}\n${volunteer.phone}"
                            } ?: this@AppointmentDetailsActivity.resources.getText(R.string.no_volunteer_assigned)
                        }
                    }
                    else if (response.code() == 401) {
                        HelperMethods.signOut(this@AppointmentDetailsActivity)
                    }
                    else {
                        Toast.makeText(
                            this@AppointmentDetailsActivity,
                            "Unknown error occurred. Please try again.",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.e(API, "Failure: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@AppointmentDetailsActivity,
                        "Server down, please try again.",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e(API, "Failure: ${e.message}")
                }
            }
        }
    }
}