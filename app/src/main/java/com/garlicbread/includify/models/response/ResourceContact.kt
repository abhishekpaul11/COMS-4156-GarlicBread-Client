package com.garlicbread.includify.models.response

data class ResourceContact(
    val id: String,
    val name: String,
    val latitude: String,
    val longitude: String,
    val address: String,
    val phone: String?,
    val distance: Double
)