package com.garlicbread.includify

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.garlicbread.includify.databinding.ActivityAddContactBinding
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_CONTACTS
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_TAG

class AddContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val relation = binding.relation.text.toString().trim()
            val number = binding.phone.text.toString().trim()

            if (name.isNotBlank() && relation.isNotBlank() && number.isNotBlank()) {
                val sharedPreferences = getSharedPreferences(SHARED_PREF_TAG, MODE_PRIVATE)
                var contacts = sharedPreferences.getString(SHARED_PREF_CONTACTS, "")
                if (contacts?.isNotEmpty() == true) {
                    contacts += "&$name%$relation%$number"
                }
                else {
                    contacts = "$name%$relation%$number"
                }
                sharedPreferences.edit().putString(SHARED_PREF_CONTACTS, contacts).apply()

                Toast.makeText(this, "New contact added", Toast.LENGTH_LONG).show()

                val newIntent = Intent(this, DashboardActivity::class.java)
                newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                this.startActivity(newIntent)
            }
        }
    }
}