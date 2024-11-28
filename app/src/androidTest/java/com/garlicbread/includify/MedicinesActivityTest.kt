package com.garlicbread.includify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_TAG
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_USER_ID
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MedicinesActivityTest {

    @Test
    fun testBasicUI() {
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, OrganisationDetailsActivity::class.java)
        val prefs = InstrumentationRegistry.getInstrumentation().targetContext.getSharedPreferences(SHARED_PREF_TAG, MODE_PRIVATE)
        prefs.edit().putString("medicine", "A%B%C&D%E%F").apply()
        ActivityScenario.launch<OrganisationDetailsActivity>(intent)
        Thread.sleep(2000)
    }

}
