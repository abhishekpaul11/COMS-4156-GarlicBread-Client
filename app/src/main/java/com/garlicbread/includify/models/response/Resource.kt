package com.garlicbread.includify.models.response

data class Resource(
    val id: String,
    val resourceType: List<ResourceType>,
    val targetUserCategory: List<UserCategory>,
    val title: String,
    val description: String?,
    val usageInstructions: String?,
    val organisationId: String,
    val resourceContact: ResourceContact?,
    val resourceService: ResourceService?,
    val resourceTool: ResourceTool?,
    val resourceInfra: ResourceInfra?
)

