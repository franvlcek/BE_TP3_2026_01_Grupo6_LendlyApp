package com.example.lendlyapp.pages.verification

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.lendlyapp.components.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePasswordScreen(
    viewModel: CreatePasswordViewModel,
    onNavigateBack: () -> Unit,
    onNavigateNext: () -> Unit
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
                text = "Create your password",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Choose a password",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = viewModel.password,
                onValueChange = viewModel::onPasswordChanged,
                placeholder = { Text("********") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (viewModel.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (viewModel.passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = viewModel::togglePasswordVisibility) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                isError = viewModel.passwordError != null,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray
                )
            )

            Text(
                text = viewModel.passwordError ?: "At least 9 characters, containing a letter and a number",
                fontSize = 12.sp,
                color = if (viewModel.passwordError != null) MaterialTheme.colorScheme.error else Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(
                text = "Next",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 16.dp),
                onClick = {
                    viewModel.validate(onSuccess = onNavigateNext)
                }
            )
        }
    }
}
