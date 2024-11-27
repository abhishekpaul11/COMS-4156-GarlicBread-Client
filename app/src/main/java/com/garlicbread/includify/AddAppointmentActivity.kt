package com.garlicbread.includify

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.garlicbread.includify.databinding.ActivityAddAppointmentBinding
import com.garlicbread.includify.models.request.AppointmentRequest
import com.garlicbread.includify.models.response.Appointment
import com.garlicbread.includify.retrofit.RetrofitInstance
import com.garlicbread.includify.util.Constants.Companion.API
import com.garlicbread.includify.util.Constants.Companion.INTENT_EXTRAS_ORGANISATION_ID
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_TAG
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_USER_ID
import com.garlicbread.includify.util.HelperMethods
import com.garlicbread.includify.util.HelperMethods.Companion.getAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale
import java.util.concurrent.TimeUnit

class AddAppointmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAppointmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val organisationId = intent.getStringExtra(INTENT_EXTRAS_ORGANISATION_ID)
        val sharedPreferences = getSharedPreferences(SHARED_PREF_TAG, MODE_PRIVATE)
        val userId = sharedPreferences.getString(SHARED_PREF_USER_ID, "")
        var accessToken = ""

        if (organisationId.isNullOrBlank() or userId.isNullOrBlank()) {
            Toast.makeText(this, "Some error has occurred. Restart the application.", Toast.LENGTH_LONG).show()
            return
        }

        binding.datePicker.minDate = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)

        val resourceIds = mutableListOf<String>()

        binding.btnSchedule.setOnClickListener {
            val appointmentRequest = AppointmentRequest(
                organisationId!!,
                userId!!,
                resourceIds,
                processTime(binding.startTimePicker.hour, binding.startTimePicker.minute),
                processTime(binding.endTimePicker.hour, binding.endTimePicker.minute),
                String.format(Locale.getDefault(), "%02d%02d%d",
                    binding.datePicker.month + 1, binding.datePicker.dayOfMonth, binding.datePicker.year)
            )

            schedule(accessToken, appointmentRequest)
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                accessToken = getAccessToken(this@AddAppointmentActivity)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@AddAppointmentActivity,
                        "Server down, please try again.",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e(API, "Failure: ${e.message}")
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.fetchOrganisationById(organisationId!!).execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val organisation = response.body()!!
                        if (organisation.resources.isNotEmpty()) {
                            binding.noResourcesPresent.isVisible = false

                            val inflater = LayoutInflater.from(this@AddAppointmentActivity)
                            organisation.resources.forEach {
                                val checkbox = inflater.inflate(R.layout.checkbox, binding.resourcesContainer, false) as CheckBox
                                checkbox.text = it.title
                                checkbox.setOnCheckedChangeListener { _, isChecked ->
                                    if (isChecked) resourceIds.add(it.id)
                                    else resourceIds.remove(it.id)
                                }
                                binding.resourcesContainer.addView(checkbox)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(API, "Failure: ${e.message}")
            }
        }
    }

    private fun schedule(accessToken: String, appointmentRequest: AppointmentRequest) {
        RetrofitInstance.api.createAppointment("Bearer $accessToken", appointmentRequest)
            .enqueue(object : Callback<Appointment> {
                override fun onResponse(call: Call<Appointment>, response: Response<Appointment>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@AddAppointmentActivity,
                            "Appointment Scheduled Successfully !!!",
                            Toast.LENGTH_LONG
                        ).show()

                        val newIntent = Intent(this@AddAppointmentActivity, DashboardActivity::class.java)
                        newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(newIntent)
                    }
                    else if (response.code() == 401) {
                        HelperMethods.signOut(this@AddAppointmentActivity)
                    }
                    else {
                        Toast.makeText(
                            this@AddAppointmentActivity,
                            response.errorBody()?.string() ?: "Unknown error occurred. Please try again.",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.e(API, "Error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Appointment>, t: Throwable) {
                    Toast.makeText(
                        this@AddAppointmentActivity,
                        "Server down, please try again.",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e(API, "Failure: ${t.message}")
                }
            })
    }

    private fun processTime(hour: Int, minute: Int): Long {
        return TimeUnit.HOURS.toMillis(hour.toLong()) + TimeUnit.MINUTES.toMillis(minute.toLong())
    }
}