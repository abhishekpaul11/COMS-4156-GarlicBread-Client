package com.garlicbread.includify.models.response

data class ResourceService(
    val id: String,
    val timeStart: Long,
    val timeEnd: Long,
    val days: String?,
    val date: String?
)