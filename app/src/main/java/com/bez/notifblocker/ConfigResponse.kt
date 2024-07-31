package com.bez.notifblocker

data class ConfigResponse(
    val record: List<String>,
    val metadata: Metadata
)

data class Metadata(
    val id: String,
    val private: Boolean,
    val createdAt: String,
    val name: String
)
