package com.garlicbread.includify.util

import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.garlicbread.includify.LoginActivity
import com.garlicbread.includify.models.request.LoginRequest
import com.garlicbread.includify.retrofit.RetrofitInstance
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

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
                signOut(context)
                ""
            }
        }

        fun signOut(context: Context) {
            val sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_TAG, MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()
            Toast.makeText(context, "Session expired. Please login again.", Toast.LENGTH_LONG).show()
            val newIntent = Intent(context, LoginActivity::class.java)
            newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(newIntent)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun convertMillisToTimeString(millis: Long): String {
            val time = LocalTime.ofSecondOfDay(millis / 1000)
            val formatter = DateTimeFormatter.ofPattern("h:mm a")
            return time.format(formatter).lowercase()
        }

        fun getDaysFromBinary(binary: String): List<String> {
            val daysOfWeek = listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
            return binary.mapIndexedNotNull { index, char ->
                if (char == '1') daysOfWeek[index] else null
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun formatDateString(dateString: String): String {
            val formatterInput = DateTimeFormatter.ofPattern("MMddyyyy", Locale.ENGLISH)
            val date = LocalDate.parse(dateString, formatterInput)
            val day = date.dayOfMonth
            val dayWithSuffix = "$day${getDaySuffix(day)}"

            val monthYearFormatter = DateTimeFormatter.ofPattern("MMMM, yyyy", Locale.ENGLISH)
            val monthYear = date.format(monthYearFormatter)

            return "$dayWithSuffix $monthYear"
        }

        private fun getDaySuffix(day: Int): String {
            return when {
                day in 11..13 -> "th"
                day % 10 == 1 -> "st"
                day % 10 == 2 -> "nd"
                day % 10 == 3 -> "rd"
                else -> "th"
            }
        }

    }
}