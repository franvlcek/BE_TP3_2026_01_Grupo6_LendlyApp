package com.example.lendlyapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lendlyapp.pages.SplashScreen
import com.example.lendlyapp.pages.login.LoginScreen
import com.example.lendlyapp.pages.onboarding.OnboardingContainer
import com.example.lendlyapp.ui.theme.LendlyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LendlyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // El NavController es el que maneja el historial de pantallas
                    val navController = rememberNavController()

                    // Configuramos el contenedor de navegación. Arranca en el Splash!
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route

                    ) {
                        // 1. Definimos la pantalla de Splash
                        composable(Screen.Splash.route) {
                            SplashScreen(
                                onNavigateToLogin = {
                                    // Al presionar el botón "Get Started", saltamos al Login
                                    // y removemos el Splash del historial para que no se pueda volver atrás
                                    navController.navigate(Screen.Onboarding.route) {
                                        popUpTo(Screen.Splash.route) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable(Screen.Onboarding.route) {
                            OnboardingContainer(
                                onFinish = {
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                                    }
                                }
                            )
                        }

                        // 2. Definimos la pantalla de Login
                        composable(Screen.Login.route) {
                            val loginViewModel: com.example.lendlyapp.pages.login.LoginViewModel = hiltViewModel()

                            LoginScreen(
                                viewModel = loginViewModel,
                                onNavigateToHome = {
                                    Log.d("MainActivity", "LOGIN EXITOSO")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}