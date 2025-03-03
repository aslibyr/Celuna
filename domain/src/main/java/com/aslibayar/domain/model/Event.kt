package com.aslibayar.domain.model

data class Event(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val startTimeMillis: Long = System.currentTimeMillis(),
    val endTimeMillis: Long = System.currentTimeMillis(),
    val location: String? = null,
    val category: String = "default",
    val color: Long = 0xFF2196F3, // Material Blue as default
    val reminder: Boolean = false,
    val reminderTimeMillis: Long? = null,
    val userId: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) 