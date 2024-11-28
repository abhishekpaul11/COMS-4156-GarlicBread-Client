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
class DashboardActivityTest {

    @Test
    fun testEmergencyContacts() {
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, DashboardActivity::class.java)
        ActivityScenario.launch<DashboardActivity>(intent)
        Thread.sleep(2000)

        onView(withId(R.id.emergency_contacts)).perform(ViewActions.click())
    }

    @Test
    fun testMedicines() {
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, DashboardActivity::class.java)
        ActivityScenario.launch<DashboardActivity>(intent)
        Thread.sleep(2000)

        onView(withId(R.id.medicines)).perform(ViewActions.click())
    }

    @Test
    fun testSignOut() {
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, DashboardActivity::class.java)
        ActivityScenario.launch<DashboardActivity>(intent)
        Thread.sleep(2000)

        onView(withId(R.id.signOutBtn)).perform(ViewActions.click())
    }

    @Test
    fun testExplore() {
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, DashboardActivity::class.java)
        ActivityScenario.launch<DashboardActivity>(intent)
        Thread.sleep(2000)

        onView(withId(R.id.exploreButton)).perform(ViewActions.click())
    }

    @Test
    fun testAppointments() {
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, DashboardActivity::class.java)
        ActivityScenario.launch<DashboardActivity>(intent)
        Thread.sleep(2000)

        onView(withId(R.id.appointmentsButton)).perform(ViewActions.click())
    }

}
