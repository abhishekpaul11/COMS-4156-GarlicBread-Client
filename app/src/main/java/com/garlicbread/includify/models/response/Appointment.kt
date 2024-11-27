package com.garlicbread.includify.models.response

data class Appointment(
    val id: String,
    val organisation: Organisation,
    val user: User,
    val resources: List<Resource>,
    val volunteer: Volunteer?,
    val timeStart: Long,
    val timeEnd: Long,
    val date: String
)
