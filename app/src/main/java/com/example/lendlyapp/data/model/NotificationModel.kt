package com.example.lendlyapp.data.model

data class NotificationModel(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val isRead: Boolean,
    val category: NotificationCategory
)

enum class NotificationCategory {
    TODAY,
    ANNOUNCEMENT
}
