package com.garlicbread.includify

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddContactActivityTest {

    @Test
    fun testBasicUI() {
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, AddContactActivity::class.java)
        ActivityScenario.launch<AddContactActivity>(intent)
        Thread.sleep(2000)

        onView(withId(R.id.name)).perform(ViewActions.typeText("Abhishek"))
        onView(withId(R.id.relation)).perform(ViewActions.typeText("Friend"))
        onView(withId(R.id.phone)).perform(ViewActions.typeText("1234"))

        onView(withId(R.id.btnAdd)).perform(ViewActions.click())
        Thread.sleep(2000)
    }

}
