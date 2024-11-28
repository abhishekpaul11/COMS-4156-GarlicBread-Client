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
class LoginActivityTest {

    @Test
    fun testBasicUI() {
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, LoginActivity::class.java)
        ActivityScenario.launch<LoginActivity>(intent)
        Thread.sleep(2000)

        onView(withId(R.id.etEmail)).perform(ViewActions.typeText("Friend"))
        onView(withId(R.id.etPassword)).perform(ViewActions.typeText("Password"))

        onView(withId(R.id.btnLogin)).perform(ViewActions.click())
        Thread.sleep(2000)
    }

}
