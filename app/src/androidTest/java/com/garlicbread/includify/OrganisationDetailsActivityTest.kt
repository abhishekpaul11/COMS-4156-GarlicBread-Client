package com.garlicbread.includify

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OrganisationDetailsActivityTest {

    @Test
    fun testBasicUI() {
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, OrganisationDetailsActivity::class.java)
        intent.putExtra("org_id", "e9c11b49-d2b1-4740-a2f8-843c52ab27f2")
        ActivityScenario.launch<OrganisationDetailsActivity>(intent)
        Thread.sleep(2000)
    }

}
