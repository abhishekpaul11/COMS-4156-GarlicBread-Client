package com.garlicbread.includify

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.garlicbread.includify.adapters.ResourceAdapter
import com.garlicbread.includify.databinding.ActivityOrganisationDetailsBinding
import com.garlicbread.includify.retrofit.RetrofitInstance
import com.garlicbread.includify.util.Constants.Companion.API
import com.garlicbread.includify.util.Constants.Companion.INTENT_EXTRAS_ORGANISATION_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrganisationDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrganisationDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrganisationDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(INTENT_EXTRAS_ORGANISATION_ID)

        if (id.isNullOrBlank()) {
            Toast.makeText(this, "Some error has occurred. Restart the application.", Toast.LENGTH_LONG).show()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.fetchOrganisationById(id).execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val organisation = response.body()!!

                        binding.title.text = organisation.name
                        binding.address.text = organisation.address
                        binding.email.text = organisation.email
                        binding.desc.text = organisation.description ?: "No description provided"

                        if (organisation.resources.isNotEmpty()) {
                            binding.noResPresent.isVisible = false
                            binding.recyclerView.isVisible = true
                            binding.recyclerView.layoutManager =
                                LinearLayoutManager(this@OrganisationDetailsActivity)
                            binding.recyclerView.adapter = ResourceAdapter(organisation.resources)
                        }
                        else {
                            binding.noResPresent.isVisible = true
                            binding.recyclerView.isVisible = false
                        }
                    }
                    else {
                        binding.noResPresent.isVisible = false
                        binding.recyclerView.isVisible = false
                        Toast.makeText(this@OrganisationDetailsActivity, "Some error has occurred. Restart the application.", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@OrganisationDetailsActivity,
                        "Server down, please try again.",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e(API, "Failure: ${e.message}")
                }
            }
        }
    }
}