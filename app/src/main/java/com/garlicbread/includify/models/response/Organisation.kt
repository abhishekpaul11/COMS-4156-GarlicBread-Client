package com.garlicbread.includify.models.response

data class Organisation(
    val id: String,
    val name: String,
    val email: String,
    val description: String?,
    val latitude: String,
    val longitude: String,
    val address: String,
    val resources: List<Resource>
)
