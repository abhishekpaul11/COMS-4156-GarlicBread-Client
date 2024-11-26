package com.garlicbread.includify.models.response

data class UserCategory(
    val id: Int,
    val title: String,
    val description: String,
    val userId: List<String>,
    val resourceId: List<String>
)
