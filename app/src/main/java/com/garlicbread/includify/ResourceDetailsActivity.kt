package com.garlicbread.includify

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.garlicbread.includify.components.DisplayAttribute
import com.garlicbread.includify.databinding.ActivityResourceDetailsBinding
import com.garlicbread.includify.models.response.ResourceContact
import com.garlicbread.includify.models.response.ResourceInfra
import com.garlicbread.includify.models.response.ResourceService
import com.garlicbread.includify.models.response.ResourceTool
import com.garlicbread.includify.retrofit.RetrofitInstance
import com.garlicbread.includify.util.Constants.Companion.API
import com.garlicbread.includify.util.Constants.Companion.INTENT_EXTRAS_ORGANISATION_ADDRESS
import com.garlicbread.includify.util.Constants.Companion.INTENT_EXTRAS_ORGANISATION_DESC
import com.garlicbread.includify.util.Constants.Companion.INTENT_EXTRAS_ORGANISATION_EMAIL
import com.garlicbread.includify.util.Constants.Companion.INTENT_EXTRAS_ORGANISATION_NAME
import com.garlicbread.includify.util.Constants.Companion.INTENT_EXTRAS_RESOURCE_ID
import com.garlicbread.includify.util.HelperMethods
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResourceDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResourceDetailsBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResourceDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.title.text = intent.getStringExtra(INTENT_EXTRAS_ORGANISATION_NAME)
        binding.email.text = intent.getStringExtra(INTENT_EXTRAS_ORGANISATION_EMAIL)
        binding.address.text = intent.getStringExtra(INTENT_EXTRAS_ORGANISATION_ADDRESS)
        binding.desc.text = if (intent.getStringExtra(INTENT_EXTRAS_ORGANISATION_DESC).isNullOrEmpty()) {
            this.resources.getText(R.string.no_desc)
        }
        else {
            intent.getStringExtra(INTENT_EXTRAS_ORGANISATION_DESC)
        }

        val id = intent.getStringExtra(INTENT_EXTRAS_RESOURCE_ID)

        if (id.isNullOrBlank()) {
            Toast.makeText(this, "Some error has occurred. Restart the application.", Toast.LENGTH_LONG).show()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.fetchResourceById(id).execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val resource = response.body()!!

                        binding.resTitle.text = resource.title
                        binding.usageDesc.text = resource.usageInstructions ?: this@ResourceDetailsActivity.resources.getText(R.string.no_usage_instr)
                        binding.resTypeDesc.text = resource.resourceType.joinToString(", ") { it.title }.ifBlank { this@ResourceDetailsActivity.resources.getText(R.string.no_res_type) }
                        binding.userCatDesc.text = resource.targetUserCategory.joinToString(", ") { it.title }.ifBlank { this@ResourceDetailsActivity.resources.getText(R.string.no_target_user_cat) }
                        binding.description.text = resource.description ?: this@ResourceDetailsActivity.resources.getText(R.string.includify)

                        if (resource.resourceTool != null) addResourceTool(resource.resourceTool, binding.attributeContainer)
                        if (resource.resourceContact != null) addResourceContact(resource.resourceContact, binding.attributeContainer)
                        if (resource.resourceService != null) addResourceService(resource.resourceService, binding.attributeContainer)
                        if (resource.resourceInfra != null) addResourceInfra(resource.resourceInfra, resource.title, binding.attributeContainer)
                    } else {
                        Toast.makeText(
                            this@ResourceDetailsActivity,
                            "Network call failed. Please try after some time.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@ResourceDetailsActivity,
                        "Server down, please try again.",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e(API, "Failure: ${e.message}")
                }
            }
        }

    }

    private fun addResourceTool(resourceTool: ResourceTool, linearLayout: LinearLayout) {
        val displayAttribute = DisplayAttribute(this)
        displayAttribute.setKey("Resource Availability")
        displayAttribute.setValue(resourceTool.count.toString())
        linearLayout.addView(displayAttribute)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addResourceService(resourceService: ResourceService, linearLayout: LinearLayout) {
        var displayAttribute = DisplayAttribute(this)
        displayAttribute.setKey("Start Time")
        displayAttribute.setValue(HelperMethods.convertMillisToTimeString(resourceService.timeStart))
        linearLayout.addView(displayAttribute)

        displayAttribute = DisplayAttribute(this)
        displayAttribute.setKey("End Time")
        displayAttribute.setValue(HelperMethods.convertMillisToTimeString(resourceService.timeEnd))
        linearLayout.addView(displayAttribute)

        if (resourceService.date != null) {
            displayAttribute = DisplayAttribute(this)
            displayAttribute.setKey("Date")
            displayAttribute.setValue(HelperMethods.formatDateString(resourceService.date))
            linearLayout.addView(displayAttribute)
        }
        else {
            displayAttribute = DisplayAttribute(this)
            displayAttribute.setKey("Days")
            displayAttribute.setValue(HelperMethods.getDaysFromBinary(resourceService.days!!).joinToString(", "))
            linearLayout.addView(displayAttribute)
        }
    }

    private fun addResourceInfra(resourceInfra: ResourceInfra, resourceName: String, linearLayout: LinearLayout) {
        var displayAttribute = DisplayAttribute(this)
        displayAttribute.setKey("Address")
        displayAttribute.setValue(resourceInfra.address)
        linearLayout.addView(displayAttribute)

        displayAttribute = DisplayAttribute(this)
        displayAttribute.setKey("Location")
        displayAttribute.setValue("View on Map", location = true, latitude = resourceInfra.latitude, longitude = resourceInfra.longitude, markerName = resourceName, context = this)
        linearLayout.addView(displayAttribute)
    }

    private fun addResourceContact(resourceContact: ResourceContact, linearLayout: LinearLayout) {
        var displayAttribute = DisplayAttribute(this)
        displayAttribute.setKey("Name")
        displayAttribute.setValue(resourceContact.name)
        linearLayout.addView(displayAttribute)

        displayAttribute = DisplayAttribute(this)
        displayAttribute.setKey("Address")
        displayAttribute.setValue(resourceContact.address)
        linearLayout.addView(displayAttribute)

        displayAttribute = DisplayAttribute(this)
        displayAttribute.setKey("Phone Number")
        displayAttribute.setValue(resourceContact.phone?.let { it.ifEmpty { "No phone number available" }} ?: "No phone number available", true)
        linearLayout.addView(displayAttribute)

        displayAttribute = DisplayAttribute(this)
        displayAttribute.setKey("Distance From Organisation")
        displayAttribute.setValue(resourceContact.distance.toString() + " miles")
        linearLayout.addView(displayAttribute)

        displayAttribute = DisplayAttribute(this)
        displayAttribute.setKey("Location")
        displayAttribute.setValue("View on Map", location = true, latitude = resourceContact.latitude, longitude = resourceContact.longitude, markerName = resourceContact.name, context = this)
        linearLayout.addView(displayAttribute)
    }
}