package com.garlicbread.includify.models.request

data class UserRequest(
    val name: String,
    val age: Int,
    val email: String,
    val password: String,
    val categoryIds: List<String> = emptyList()
)
