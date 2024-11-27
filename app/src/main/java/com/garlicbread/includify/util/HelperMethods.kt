package com.garlicbread.includify.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.garlicbread.includify.LoginActivity
import com.garlicbread.includify.models.request.LoginRequest
import com.garlicbread.includify.notification.NotificationReceiver
import com.garlicbread.includify.retrofit.RetrofitInstance
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
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

        fun signOut(context: Context, sessionExpired: Boolean = true) {
            val sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_TAG, MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()
            Toast.makeText(
                context,
                if (sessionExpired) "Session expired. Please login again." else "Signed out successfully",
                Toast.LENGTH_LONG
            ).show()
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

        fun scheduleDailyNotification(context: Context, hour: Int, minute: Int, medicine: String) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, NotificationReceiver::class.java)
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
            intent.putExtra(Constants.INTENT_EXTRAS_MEDICINE, medicine)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
            }

            if (calendar.timeInMillis < System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_YEAR, 1)
            }

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }

        fun formatTime(milliseconds: Long): String {
            val totalSeconds = maxOf(0, milliseconds) / 1000
            val hours = totalSeconds / 3600
            val minutes = (totalSeconds % 3600) / 60

            val period = if (hours < 12) "am" else "pm"
            val displayHours = when (hours % 12) {
                0L -> 12
                else -> hours % 12
            }

            return String.format(Locale.getDefault(), "%d:%02d %s", displayHours, minutes, period)
        }

    }
}