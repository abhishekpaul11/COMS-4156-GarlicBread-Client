package com.garlicbread.includify

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ResourceDetailsActivityTest {

    @Test
    fun testBasicUI() {
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, ResourceDetailsActivity::class.java)
        intent.putExtra("res_id", "780b876f-6d03-4f03-8b69-60f57dc05d7c")
        ActivityScenario.launch<ResourceDetailsActivity>(intent)
        Thread.sleep(2000)
    }

}
