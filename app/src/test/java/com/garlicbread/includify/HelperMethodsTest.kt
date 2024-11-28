package com.garlicbread.includify.util

import android.content.Context
import android.content.SharedPreferences
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class HelperMethodsTest {

    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    @Before
    fun setUp() {
        context = mock(Context::class.java)
        sharedPreferences = mock(SharedPreferences::class.java)
        sharedPreferencesEditor = mock(SharedPreferences.Editor::class.java)
        `when`(context.getSharedPreferences(Constants.SHARED_PREF_TAG, Context.MODE_PRIVATE)).thenReturn(sharedPreferences)
        `when`(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)
    }

    @Test
    fun `getAccessToken should return access token if it exists`() {
        val accessToken = "mockAccessToken"
        `when`(sharedPreferences.getString(Constants.SHARED_PREF_ACCESS_TOKEN, null)).thenReturn(accessToken)

        val result = HelperMethods.getAccessToken(context)

        assertEquals(accessToken, result)
        verify(sharedPreferences).getString(Constants.SHARED_PREF_ACCESS_TOKEN, null)
    }

    @Test
    fun `convertMillisToTimeString should return correct time string`() {
        val timeString = HelperMethods.convertMillisToTimeString(3600000L)  // 1 hour in milliseconds
        assertEquals("1:00 am", timeString)
    }

    @Test
    fun `formatDateString should return correctly formatted date string`() {
        val dateString = HelperMethods.formatDateString("12252024")
        assertEquals("25th December, 2024", dateString)
    }

    @Test
    fun `getDaysFromBinary should return correct days list`() {
        val days = HelperMethods.getDaysFromBinary("1101000")
        assertEquals(listOf("Sunday", "Monday", "Wednesday"), days)
    }

    @Test
    fun `formatTime should return formatted time string`() {
        val formattedTime = HelperMethods.formatTime(4500000L)  // 1 hour, 15 minutes in milliseconds
        assertEquals("1:15 am", formattedTime)
    }
}
