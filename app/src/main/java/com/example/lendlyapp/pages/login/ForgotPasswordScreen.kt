package com.example.lendlyapp.pages.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.R
import com.example.lendlyapp.components.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel,
    onNavigateBack: () -> Unit = {}
) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text("Forgot Password", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.frame_134),
                contentDescription = "Logo",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (!viewModel.emailSent) {
                Text(
                    text = "Enter your email address to reset your password.",
                    color = Color.Gray,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = viewModel.email,
                    onValueChange = viewModel::onEmailChanged,
                    label = { Text("Email Address") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(32.dp))

                PrimaryButton(
                    text = "Send Reset Link",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    onClick = viewModel::sendResetLink
                )
            } else {
                Text(
                    text = "Check your email!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4C662B)
                )
                Text(
                    text = "We've sent a password reset link to ${viewModel.email}",
                    color = Color.Gray,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.padding(top = 16.dp)
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                PrimaryButton(
                    text = "Back to Login",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    onClick = onNavigateBack
                )
            }
        }
    }
}
