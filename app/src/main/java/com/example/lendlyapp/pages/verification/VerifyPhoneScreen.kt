package com.example.lendlyapp.pages.verification

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack // 🌟 IMPORTACIÓN MODERNIZADA
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.components.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyPhoneScreen(
    viewModel: VerifyPhoneViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToSms: () -> Unit
) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Info action */ }) {
                        Icon(Icons.Outlined.Info, contentDescription = "Info", tint = Color.Gray)
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
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Verify your phone\nnumber with a code",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = "We will send you a One-Time-Password (OTP) to confirm you number.",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 12.dp, bottom = 32.dp)
            )

            Text(
                text = "Your Phone Number",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = viewModel.countryCode,
                    onValueChange = viewModel::onCountryCodeChanged,
                    modifier = Modifier.width(90.dp),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.LightGray
                    ),
                    isError = viewModel.phoneError != null
                )

                OutlinedTextField(
                    value = viewModel.phoneNumber,
                    onValueChange = viewModel::onPhoneNumberChanged,
                    placeholder = { Text("991251255", color = Color.LightGray) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.LightGray
                    ),
                    isError = viewModel.phoneError != null,
                    supportingText = {
                        viewModel.phoneError?.let {
                            Text(text = it, color = MaterialTheme.colorScheme.error)
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(
                text = "Send Code",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 16.dp),
                onClick = {
                    viewModel.validate(onSuccess = onNavigateToSms)
                }
            )
        }
    }
}