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
import com.example.lendlyapp.ui.theme.LendlyAppTheme
import dagger.hilt.android.AndroidEntryPoint

import com.example.lendlyapp.pages.home.HomeScreen
import com.example.lendlyapp.pages.history.HistoryScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lendlyapp.components.BottomNavigationBar

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LendlyAppTheme {
                // El NavController es el que maneja el historial de pantallas
                val navController = rememberNavController()
                
                // Observamos la ruta actual para saber qué item resaltar en la barra
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry.value?.destination?.route

                // Scaffold nos da la estructura base (Barra de abajo + contenido)
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        // Solo mostramos la barra si NO estamos en Splash ni en Login
                        if (currentRoute != Screen.Splash.route && currentRoute != Screen.Login.route) {
                            BottomNavigationBar(
                                currentRoute = currentRoute,
                                onNavigate = { route ->
                                    navController.navigate(route) {
                                        // Evita crear múltiples copias de la misma pantalla
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    // El NavHost ahora vive dentro del Scaffold y respeta el padding de la barra
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // 1. Definimos la pantalla de Splash
                        composable(Screen.Splash.route) {
                            SplashScreen(
                                onNavigateToLogin = {
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(Screen.Splash.route) { inclusive = true }
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
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Login.route) { inclusive = true }
                                    }
                                }
                            )
                        }

                        // 3. Pantalla de Home
                        composable(Screen.Home.route) {
                            HomeScreen()
                        }

                        // 4. Pantalla de Historial
                        composable(Screen.History.route) {
                            HistoryScreen()
                        }
                    }
                }
            }
        }
    }
}