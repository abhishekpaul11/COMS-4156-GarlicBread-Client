package com.garlicbread.includify

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddMedicineActivityTest {

    @Test
    fun testBasicUI() {
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, AddMedicineActivity::class.java)
        ActivityScenario.launch<AddMedicineActivity>(intent)
        Thread.sleep(2000)

        onView(withId(R.id.name)).perform(ViewActions.typeText("Medicine"))
        onView(withId(R.id.desc)).perform(ViewActions.typeText("Test Medicine"))

        onView(withId(R.id.btnAdd)).perform(ViewActions.click())
        Thread.sleep(2000)
    }

}
