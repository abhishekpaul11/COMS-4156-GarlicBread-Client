package com.garlicbread.includify.models.response

data class ResourceType(
    val id: Int,
    val title: String,
    val description: String,
    val resourceId: List<String>
)
