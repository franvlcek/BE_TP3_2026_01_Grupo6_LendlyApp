package com.example.lendlyapp

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object History : Screen("history")
    object TransactionDetail : Screen("transaction_detail")
    
    // Flujo de Cash In
    object CashInOptions : Screen("cash_in_options")
    object OnlineCashIn : Screen("online_cash_in")
    object OverTheCounter : Screen("over_the_counter")
    object CashInAmount : Screen("cash_in_amount")
    object TransactionSuccess : Screen("transaction_success")
    
    // Notificaciones
    object Notifications : Screen("notifications")
    object Manage : Screen("manage")
    object CreditScore : Screen("credit_score")
    object Shop : Screen("shop")
    object Search : Screen("search")
    object Filter : Screen("filter")
    object Product : Screen("product/{productId}") {
        const val ARG_PRODUCT_ID = "productId"
        fun createRoute(productId: String) = "product/$productId"
    }

    // Verificación
    object VerifyPhone : Screen("verify_phone")
    object SmsVerification : Screen("sms_verification")
    object FaceRecognition : Screen("face_recognition")
    object IdVerification : Screen("id_verification")
    object VerifiedSuccess : Screen("verified_success")
    object ProfileDetail : Screen("profile_detail")
    object Signature : Screen("signature")
    object CreatePassword : Screen("create_password")
    object RegistrationDone : Screen("registration_done")
    object ProfileDetails : Screen("profile_details")
}
