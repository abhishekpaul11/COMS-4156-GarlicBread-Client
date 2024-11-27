package com.garlicbread.includify.models.request

data class AppointmentRequest(
    val organisationId: String,
    val userId: String,
    val resourceIds: List<String>,
    val timeStart: Long,
    val timeEnd: Long,
    val date: String
)