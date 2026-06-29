package com.example.lendlyapp.pages.notification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lendlyapp.data.model.NotificationCategory
import com.example.lendlyapp.data.model.NotificationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor() : ViewModel() {

    var notifications by mutableStateOf<List<NotificationModel>>(emptyList())
    var showCalendarDialog by mutableStateOf(false)

    init {
        loadNotifications()
    }

    private fun loadNotifications() {
        notifications = listOf(
            NotificationModel(
                1,
                "Your due date is almost here!",
                "We'd like to remind you about your due date this month. Please pay this balance within the date to keep your credit score. Tap to pay.",
                "Mar 8",
                false,
                NotificationCategory.TODAY
            ),
            NotificationModel(
                2,
                "Your due date is almost here!",
                "We'd like to remind you about your due date this month. Please pay this balance within the date to keep your credit score. Tap to pay.",
                "Mar 8",
                false,
                NotificationCategory.TODAY
            ),
            NotificationModel(
                3,
                "Got a minute to help us out?",
                "We'd like to remind you about your due date this month. Please pay this balance within the date to keep your credit score. Tap to pay.",
                "Mar 8",
                true,
                NotificationCategory.TODAY
            ),
            NotificationModel(
                4,
                "Got a minute to help us out?",
                "We'd like to remind you about your due date this month. Please pay this balance within the date to keep your credit score. Tap to pay.",
                "Mar 8",
                true,
                NotificationCategory.TODAY
            ),
            NotificationModel(
                5,
                "Your due date is almost here!",
                "We'd like to remind you about your due date this month. Please pay this balance within the date to keep your credit score. Tap to pay.",
                "Mar 8",
                false,
                NotificationCategory.ANNOUNCEMENT
            ),
            NotificationModel(
                6,
                "Got a minute to help us out?",
                "We'd like to remind you about your due date this month. Please pay this balance within the date to keep your credit score. Tap to pay.",
                "Mar 8",
                true,
                NotificationCategory.ANNOUNCEMENT
            )
        )
    }

    fun onCalendarIconClick() {
        showCalendarDialog = true
    }

    fun onDismissCalendarDialog() {
        showCalendarDialog = false
    }
}
