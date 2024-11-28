package com.garlicbread.includify

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppointmentDetailsActivityTest {

    @Test
    fun testBasicUI() {
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, AppointmentDetailsActivity::class.java)
        intent.putExtra("appointment_id", "0a857e4b-2041-40ce-8242-3a7d9f0b0780")
        ActivityScenario.launch<AppointmentDetailsActivity>(intent)
        Thread.sleep(2000)
    }

}
