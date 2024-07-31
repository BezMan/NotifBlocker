package com.bez.notifblocker.data.network

data class PackagesResponse(
    val record: List<String>,
    val metadata: Metadata
)

data class Metadata(
    val id: String,
    val private: Boolean,
    val createdAt: String,
    val name: String
)
