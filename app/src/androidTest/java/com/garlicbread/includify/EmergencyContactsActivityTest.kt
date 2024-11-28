package com.garlicbread.includify

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.garlicbread.includify.adapters.ContactsAdapter
import com.garlicbread.includify.models.Contact
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_CONTACTS
import com.garlicbread.includify.util.Constants.Companion.SHARED_PREF_TAG
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmergencyContactsActivityTest {

    private lateinit var sharedPreferences: SharedPreferences

    @Before
    fun setup() {
        sharedPreferences = androidx.test.core.app.ApplicationProvider.getApplicationContext<Context>()
            .getSharedPreferences(SHARED_PREF_TAG, Context.MODE_PRIVATE)
    }

    @Test
    fun testPressAddButton() {
        ActivityScenario.launch(EmergencyContactsActivity::class.java)
        onView(withId(R.id.button)).perform(click())
    }

    @Test
    fun testNoContacts() {
        ActivityScenario.launch(EmergencyContactsActivity::class.java)
        sharedPreferences.edit().clear().commit()
    }

    @Test
    fun testContactsListVisibilityWhenContactsArePresent() {
        val contacts = listOf(
            Contact("John Doe", "Friend", "+91 1234567890"),
            Contact("Jane Smith", "Family", "+91 0987654321")
        )

        val contactsString = contacts.joinToString("&") {
            "${it.name}%${it.relation}%${it.number}"
        }
        sharedPreferences.edit().putString(SHARED_PREF_CONTACTS, contactsString).apply()

        ActivityScenario.launch(EmergencyContactsActivity::class.java)

        onView(withId(R.id.recycler_view)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.no_contacts_present)).check(ViewAssertions.matches(not(isDisplayed())))

        onView(withText("John Doe")).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun testPhoneButtonDialing() {
        val contacts = listOf(
            Contact("John Doe", "Friend", "+91 1234567890")
        )

        val contactsString = contacts.joinToString("&") {
            "${it.name}%${it.relation}%${it.number}"
        }
        sharedPreferences.edit().putString(SHARED_PREF_CONTACTS, contactsString).apply()

        ActivityScenario.launch(EmergencyContactsActivity::class.java)

        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ContactsAdapter.ViewHolder>(0, click()))

        onView(withId(R.id.phone)).perform(click())
    }
}
