package com.garlicbread.includify

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.garlicbread.includify.adapters.MedicinesAdapter
import com.garlicbread.includify.databinding.ActivityMedicinesBinding
import com.garlicbread.includify.models.Medicine
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_MEDICINES
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_TAG

class MedicinesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMedicinesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMedicinesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(SHARED_PREF_TAG, MODE_PRIVATE)
        val medicinesData = sharedPreferences.getString(SHARED_PREF_MEDICINES, "")

        if (medicinesData?.isNotBlank() == true) {
            binding.noMedicinesPresent.isVisible = false
            binding.recyclerView.isVisible = true
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.adapter =
                MedicinesAdapter(fetchMedicines(medicinesData), this)
        }
        else {
            binding.noMedicinesPresent.isVisible = false
            binding.recyclerView.isVisible = false
        }

        binding.button.setOnClickListener {
            val newIntent = Intent(this, AddMedicineActivity::class.java)
            startActivity(newIntent)
        }
    }

    private fun fetchMedicines(medicines: String?): List<Medicine> {
        return medicines?.split("&")?.map {
            val medicineData = it.split("%")
            Medicine(
                medicineData[0],
                medicineData[1],
                medicineData[2]
            )
        } ?: listOf()
    }
}