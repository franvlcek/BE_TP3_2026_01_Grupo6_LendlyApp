package com.example.lendlyapp.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.lendlyapp.R
import com.example.lendlyapp.data.session.SessionManager
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    sessionManager: SessionManager,
    onNavigate: (String) -> Unit,

) {
    LaunchedEffect(Unit) {

        delay(1000) // opcional branding

        if (sessionManager.isSessionActive()) {
            // Si tiene sesión activa, intentamos mandarlo al Home. 
            // La verificación la manejaremos dentro de las pantallas si es necesario, 
            // pero no bloqueamos el inicio.
            onNavigate("home")
        } else {
            onNavigate("onboarding")
        }
    }


    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFFE5F5EA)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.frame_134),
            contentDescription = "SplashScreen",

        )
    }
}

/*
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}*/