package com.garlicbread.includify

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.garlicbread.includify.adapters.ContactsAdapter
import com.garlicbread.includify.databinding.ActivityEmergencyContactsBinding
import com.garlicbread.includify.models.Contact
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_CONTACTS
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_TAG

class EmergencyContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmergencyContactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEmergencyContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val newIntent = Intent(this, AddContactActivity::class.java)
            startActivity(newIntent)
        }

        val sharedPreferences = getSharedPreferences(SHARED_PREF_TAG, MODE_PRIVATE)
        val contactsData = sharedPreferences.getString(SHARED_PREF_CONTACTS, "")

        if (contactsData?.isNotBlank() == true) {
            binding.noContactsPresent.isVisible = false
            binding.recyclerView.isVisible = true
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.adapter =
                ContactsAdapter(fetchContacts(contactsData), this)
        }
        else {
            binding.noContactsPresent.isVisible = false
            binding.recyclerView.isVisible = false
        }
    }

    private fun fetchContacts(contacts: String?): List<Contact> {
        return contacts?.split("&")?.map {
            val contactData = it.split("%")
            Contact(
                contactData[0],
                contactData[1],
                contactData[2]
            )
        } ?: listOf()
    }
}