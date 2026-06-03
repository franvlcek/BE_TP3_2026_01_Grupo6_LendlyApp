package com.example.lendlyapp.pages.verification

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.lendlyapp.components.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmsVerificationScreen(
    viewModel: SmsVerificationViewModel,
    onNavigateBack: () -> Unit,
    onNavigateNext: () -> Unit
) {
    val focusRequesters = remember { List(6) { FocusRequester() } }
    val focusManager = LocalFocusManager.current

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
                text = "Enter the code",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = "Enter the security code we sent to\n******731",
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
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(6) { index ->
                    OutlinedTextField(
                        value = viewModel.code[index],
                        onValueChange = { input ->
                            viewModel.onCodeChanged(
                                index = index,
                                newValue = input,
                                onNextFocus = {
                                    if (index < 5) focusRequesters[index + 1].requestFocus()
                                },
                                onClearFocus = {
                                    focusManager.clearFocus()
                                }
                            )
                            if (input.isEmpty() && index > 0) {
                                focusRequesters[index - 1].requestFocus()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .focusRequester(focusRequesters[index]),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = if (viewModel.showError) Color.Red else Color.LightGray,
                            focusedBorderColor = Color(0xFF7BF179)
                        )
                    )
                }
            }

            if (viewModel.showError) {
                Text(
                    text = "Please enter the full 6-digit code",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            TextButton(
                onClick = { /* Resend code */ },
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 24.dp)
            ) {
                Text(
                    text = "Didn't received a code?",
                    color = Color(0xFF4C662B),
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
            }

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
