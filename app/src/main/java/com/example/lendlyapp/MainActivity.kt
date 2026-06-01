package com.example.lendlyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.NavType
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
import com.example.lendlyapp.pages.loan.LoanScreen
import com.example.lendlyapp.pages.loan.LoanFormScreen
import com.example.lendlyapp.pages.loan.LoanSuccessScreen
import com.example.lendlyapp.pages.loan.ActiveLoansScreen
import com.example.lendlyapp.components.BottomNavigationBar
import com.example.lendlyapp.data.session.SessionManager
import com.example.lendlyapp.pages.manage.CreditScoreScreen
import com.example.lendlyapp.pages.manage.ManageScreen
import com.example.lendlyapp.pages.verification.FaceRecognitionScreen
import com.example.lendlyapp.pages.verification.IdVerificationScreen
import com.example.lendlyapp.pages.verification.SmsVerificationScreen
import com.example.lendlyapp.pages.verification.VerifiedSuccessScreen
import com.example.lendlyapp.pages.verification.VerifyPhoneScreen
import com.example.lendlyapp.pages.verification.ProfileDetailScreen
import com.example.lendlyapp.pages.verification.SignatureScreen
import com.example.lendlyapp.pages.verification.CreatePasswordScreen
import com.example.lendlyapp.pages.verification.RegistrationDoneScreen
import com.example.lendlyapp.pages.manage.ProfileDetailScreen as ManageProfileDetailScreen
import com.example.lendlyapp.pages.shop.ShopScreen
import com.example.lendlyapp.pages.shop.SearchScreen
import com.example.lendlyapp.pages.shop.FilterScreen
import com.example.lendlyapp.pages.shop.ProductScreen
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
                        val mainScreens = listOf(
                            Screen.Home.route, 
                            Screen.Loan.route, 
                            Screen.Shop.route, 
                            Screen.History.route, 
                            Screen.Manage.route
                        )
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
                            LoginScreen(
                                viewModel = loginViewModel,
                                onNavigateToHome = {
                                    // Después del login, vamos a verificar el teléfono
                                    navController.navigate(Screen.VerifyPhone.route)
                                }
                            )
                        }

                        // 3. Verificación de Teléfono
                        composable(Screen.VerifyPhone.route) {
                            VerifyPhoneScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateToSms = { navController.navigate(Screen.SmsVerification.route) }
                            )
                        }

                        // 4. Verificación por SMS
                        composable(Screen.SmsVerification.route) {
                            SmsVerificationScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateNext = {
                                    navController.navigate(Screen.FaceRecognition.route)
                                }
                            )
                        }

                        // 5. Face Recognition
                        composable(Screen.FaceRecognition.route) {
                            FaceRecognitionScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateNext = {
                                    navController.navigate(Screen.IdVerification.route)
                                }
                            )
                        }

                        // 6. ID Verification
                        composable(Screen.IdVerification.route) {
                            IdVerificationScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateNext = {
                                    navController.navigate(Screen.VerifiedSuccess.route)
                                }
                            )
                        }

                        // 7. Success Page
                        composable(Screen.VerifiedSuccess.route) {
                            VerifiedSuccessScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateNext = {
                                    navController.navigate(Screen.ProfileDetail.route)
                                }
                            )
                        }

                        // 8. Profile Detail
                        composable(Screen.ProfileDetail.route) {
                            ProfileDetailScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateNext = {
                                    navController.navigate(Screen.Signature.route)
                                }
                            )
                        }

                        // 9. Signature
                        composable(Screen.Signature.route) {
                            SignatureScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateNext = {
                                    navController.navigate(Screen.CreatePassword.route)
                                }
                            )
                        }

                        // 10. Create Password
                        composable(Screen.CreatePassword.route) {
                            CreatePasswordScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateNext = {
                                    navController.navigate(Screen.RegistrationDone.route)
                                }
                            )
                        }

                        // 11. Registration Done
                        composable(Screen.RegistrationDone.route) {
                            RegistrationDoneScreen(
                                onDone = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable(Screen.Home.route) {
                            HomeScreen(
                                onNavigateToCashIn = {
                                    navController.navigate(Screen.CashInOptions.route)
                                },
                                onNavigateToProduct = { productId ->
                                    navController.navigate(Screen.Product.createRoute(productId))
                                }
                            )
                        }

                        composable(Screen.Shop.route) {
                            ShopScreen(
                                onNavigateToProduct = { productId ->
                                    navController.navigate(Screen.Product.createRoute(productId))
                                },
                                onNavigateToSearch = {
                                    navController.navigate(Screen.Search.route)
                                },
                                onNavigateToFilter = {
                                    navController.navigate(Screen.Filter.route)
                                }
                            )
                        }

                        composable(Screen.Search.route) {
                            SearchScreen(
                                onNavigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable(Screen.Filter.route) {
                            FilterScreen(
                                onNavigateBack = {
                                    navController.popBackStack()
                                },
                                onApplyFilters = { filterState ->
                                    // Aquí puedes procesar los filtros aplicados
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable(
                            route = Screen.Product.route,
                            arguments = listOf(navArgument(Screen.Product.ARG_PRODUCT_ID) { type = NavType.StringType })
                        ) { backStackEntry ->
                            val productId = backStackEntry.arguments?.getString(Screen.Product.ARG_PRODUCT_ID) ?: "iphone"
                            ProductScreen(
                                productId = productId,
                                onNavigateBack = { navController.popBackStack() },
                                onContinue = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable(Screen.History.route) {
                            HistoryScreen(onNavigateToDetail = {
                                navController.navigate(Screen.TransactionDetail.route)
                            })
                        }

                        composable(Screen.Loan.route) {
                            LoanScreen(onNavigateToForm = {
                                navController.navigate(Screen.LoanForm.route)
                            })
                        }

                        composable(Screen.LoanForm.route) {
                            LoanFormScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateNext = {
                                    navController.navigate(Screen.LoanSuccess.route)
                                }
                            )
                        }

                        composable(Screen.LoanSuccess.route) {
                            LoanSuccessScreen(
                                onDoneClick = {
                                    navController.navigate(Screen.ActiveLoans.route) {
                                        popUpTo(Screen.Home.route)
                                    }
                                }
                            )
                        }

                        composable(Screen.ActiveLoans.route) {
                            ActiveLoansScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
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
                                onBankSelected = { _ ->
                                    navController.navigate(Screen.CashInAmount.route)
                                }
                            )
                        }

                        composable(Screen.OverTheCounter.route) {
                            OverTheCounterScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onPartnerSelected = { _ ->
                                    navController.navigate(Screen.CashInAmount.route)
                                }
                            )
                        }

                        composable(Screen.CashInAmount.route) {
                            CashInAmountScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onNextClick = { _ ->
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
                                },
                                onEditProfile = {
                                    navController.navigate(Screen.ProfileDetails.route)
                                },
                                onCreditScore = {
                                    navController.navigate(Screen.CreditScore.route)
                                }
                            )
                        }
                        composable(Screen.ProfileDetails.route) {
                            ManageProfileDetailScreen(
                                sessionManager = sessionManager,
                                onBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(Screen.CreditScore.route) {
                            CreditScoreScreen(
                                sessionManager = sessionManager,
                                onBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
