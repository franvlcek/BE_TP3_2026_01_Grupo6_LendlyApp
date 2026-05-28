package com.example.lendlyapp.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.lendlyapp.R

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit
) {
    // Usamos un Box en lugar de Column para que el contenedor ocupe la pantalla absoluta de fondo
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable { onNavigateToLogin() }, // Al tocar cualquier lado de la pantalla salta al login
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.frame_134),
            contentDescription = "SplashScreen",
            modifier = Modifier.fillMaxSize(),
            // FillBounds obliga a la imagen a encajar de arriba a abajo y de izquierda a derecha
            // adaptándose a la resolución exacta del emulador sin hacer zoom.
            contentScale = ContentScale.FillBounds
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(onNavigateToLogin = {})
}