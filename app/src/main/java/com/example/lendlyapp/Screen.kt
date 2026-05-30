package com.example.lendlyapp

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Onboarding: Screen("onboarding")
}