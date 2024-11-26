package com.garlicbread.includify

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.garlicbread.includify.adapters.OrganisationAdapter
import com.garlicbread.includify.databinding.ActivityOrganisationListBinding
import com.garlicbread.includify.retrofit.RetrofitInstance
import com.garlicbread.includify.util.Constants.Companion.API
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrganisationListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrganisationListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrganisationListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.fetchAllOrganisations().execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                        binding.noOrganisationsPresent.isVisible = false
                        binding.recyclerView.isVisible = true
                        binding.recyclerView.layoutManager = LinearLayoutManager(this@OrganisationListActivity)
                        binding.recyclerView.adapter =
                            OrganisationAdapter(response.body()!!, this@OrganisationListActivity)
                    }
                    else {
                        binding.noOrganisationsPresent.isVisible = true
                        binding.recyclerView.isVisible = false
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@OrganisationListActivity,
                        "Server down, please try again.",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e(API, "Failure: ${e.message}")
                }
            }
        }

    }
}