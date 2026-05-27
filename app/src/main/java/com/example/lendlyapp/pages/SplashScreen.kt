package com.example.lendlyapp.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.lendlyapp.R

@Composable

fun SplashScreen(){
    Column() {
        Image(
            painter = painterResource(id = R.drawable.frame_134),
            contentDescription = "SplashScreen"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}