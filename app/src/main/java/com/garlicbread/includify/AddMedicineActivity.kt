package com.garlicbread.includify

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.garlicbread.includify.databinding.ActivityAddMedicineBinding
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_MEDICINES
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_TAG
import com.garlicbread.includify.util.HelperMethods
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddMedicineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMedicineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddMedicineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var formattedTime: String
        val calendar = Calendar.getInstance()
        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        formattedTime = timeFormat.format(calendar.time).lowercase()

        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            formattedTime = timeFormat.format(calendar.time).lowercase()
        }

        binding.btnAdd.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val desc = binding.desc.text.toString().trim()

            if (name.isNotBlank() && desc.isNotBlank() && formattedTime.isNotBlank()) {
                val sharedPreferences = getSharedPreferences(SHARED_PREF_TAG, MODE_PRIVATE)
                var medicines = sharedPreferences.getString(SHARED_PREF_MEDICINES, "")
                if (medicines?.isNotEmpty() == true){
                    medicines += "&$name%$desc%$formattedTime"
                }
                else {
                    medicines = "$name%$desc%$formattedTime"
                }
                sharedPreferences.edit().putString(SHARED_PREF_MEDICINES, medicines).apply()

                HelperMethods.scheduleDailyNotification(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), name)

                Toast.makeText(this, "New medicine added", Toast.LENGTH_LONG).show()

                val newIntent = Intent(this, DashboardActivity::class.java)
                newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                this.startActivity(newIntent)
            }
        }
    }
}