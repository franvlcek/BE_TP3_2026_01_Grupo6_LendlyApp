package com.example.lendlyapp.pages.notification

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class NotificationViewModelTest {

    private lateinit var viewModel: NotificationViewModel

    @Before
    fun setup() {
        // Inicializamos el ViewModel antes de cada test
        viewModel = NotificationViewModel()
    }

    @Test
    fun `loadNotifications should populate notifications list on init`() {
        // Verificamos que al crearse, ya cargó las notificaciones (mockeadas)
        assertTrue(viewModel.notifications.isNotEmpty())
        assertEquals(6, viewModel.notifications.size)
    }

    @Test
    fun `onCalendarIconClick should show calendar dialog`() {
        // Inicialmente el diálogo debe estar oculto
        assertFalse(viewModel.showCalendarDialog)

        // Ejecutamos la acción
        viewModel.onCalendarIconClick()

        // Verificamos que el estado cambió a true
        assertTrue(viewModel.showCalendarDialog)
    }

    @Test
    fun `onDismissCalendarDialog should hide calendar dialog`() {
        // Forzamos el estado a visible
        viewModel.onCalendarIconClick()
        assertTrue(viewModel.showCalendarDialog)

        // Ejecutamos la acción de ocultar
        viewModel.onDismissCalendarDialog()

        // Verificamos que el estado cambió a false
        assertFalse(viewModel.showCalendarDialog)
    }
}
