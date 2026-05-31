package com.example.lendlyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lendlyapp.pages.SplashScreen
import com.example.lendlyapp.pages.login.LoginScreen
import com.example.lendlyapp.pages.onboarding.OnboardingContainer
import com.example.lendlyapp.pages.home.HomeScreen
import com.example.lendlyapp.pages.home.CashInOptionsScreen
import com.example.lendlyapp.pages.home.OnlineCashInScreen
import com.example.lendlyapp.pages.home.OverTheCounterScreen
import com.example.lendlyapp.pages.home.CashInAmountScreen
import com.example.lendlyapp.pages.home.TransactionSuccessScreen
import com.example.lendlyapp.pages.history.HistoryScreen
import com.example.lendlyapp.pages.history.TransactionDetailScreen
import com.example.lendlyapp.components.BottomNavigationBar
import com.example.lendlyapp.data.session.SessionManager
import com.example.lendlyapp.pages.manage.ManageScreen
import com.example.lendlyapp.ui.theme.LendlyAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LendlyAppTheme {
                val navController = rememberNavController()
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry.value?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        // Lista de pantallas principales donde se muestra la BottomBar
                        val mainScreens = listOf(Screen.Home.route, "loan", "shop", Screen.History.route, "manage")
                        if (currentRoute in mainScreens) {
                            BottomNavigationBar(
                                currentRoute = currentRoute,
                                onNavigate = { route ->
                                    navController.navigate(route) {
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
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Splash.route) {
                            SplashScreen(
                                sessionManager = sessionManager,
                                onNavigate = { route ->
                                    navController.navigate(route){
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

                        composable(Screen.Login.route) {
                            val loginViewModel: com.example.lendlyapp.pages.login.LoginViewModel = hiltViewModel()
                            LoginScreen(viewModel = loginViewModel, onNavigateToHome = {
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(Screen.Login.route) { inclusive = true }
                                }
                            })
                        }

                        composable(Screen.Home.route) {
                            HomeScreen(onNavigateToCashIn = {
                                navController.navigate(Screen.CashInOptions.route)
                            })
                        }

                        composable(Screen.History.route) {
                            HistoryScreen(onNavigateToDetail = {
                                navController.navigate(Screen.TransactionDetail.route)
                            })
                        }

                        composable(Screen.TransactionDetail.route) {
                            TransactionDetailScreen(onNavigateBack = {
                                navController.popBackStack()
                            })
                        }

                        composable(Screen.CashInOptions.route) {
                            CashInOptionsScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onOptionSelected = { option ->
                                    if (option == "online") {
                                        navController.navigate(Screen.OnlineCashIn.route)
                                    } else {
                                        navController.navigate(Screen.OverTheCounter.route)
                                    }
                                }
                            )
                        }

                        composable(Screen.OnlineCashIn.route) {
                            OnlineCashInScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onBankSelected = { bank ->
                                    navController.navigate(Screen.CashInAmount.route)
                                }
                            )
                        }

                        composable(Screen.OverTheCounter.route) {
                            OverTheCounterScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onPartnerSelected = { partner ->
                                    navController.navigate(Screen.CashInAmount.route)
                                }
                            )
                        }

                        composable(Screen.CashInAmount.route) {
                            CashInAmountScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onNextClick = { amount ->
                                    navController.navigate(Screen.TransactionSuccess.route)
                                }
                            )
                        }

                        composable(Screen.TransactionSuccess.route) {
                            TransactionSuccessScreen(
                                amount = "2,500.00",
                                onDoneClick = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Home.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.Manage.route) {
                            ManageScreen(
                                sessionManager = sessionManager,
                                onLogout = {
                                    navController.navigate(Screen.Onboarding.route) {
                                        popUpTo(0)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
