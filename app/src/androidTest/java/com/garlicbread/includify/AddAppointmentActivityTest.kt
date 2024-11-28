package com.garlicbread.includify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_EMAIL
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_TAG
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_USER_ID
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddAppointmentActivityTest {

    @Test
    fun testBasicUI() {
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, AddAppointmentActivity::class.java)
        val prefs = InstrumentationRegistry.getInstrumentation().targetContext.getSharedPreferences(SHARED_PREF_TAG, MODE_PRIVATE)
        prefs.edit().putString(SHARED_PREF_USER_ID, "0f4a287d-9b41-4837-8ea0-12f9935c2ddc").apply()

        intent.putExtra("org_id", "241a6c4f-119f-492d-85d5-c0373b9cc96b")
        ActivityScenario.launch<AddAppointmentActivity>(intent)
        Thread.sleep(2000)

        onView(withId(R.id.btnSchedule)).perform(ViewActions.click())
        Thread.sleep(2000)
    }

}
