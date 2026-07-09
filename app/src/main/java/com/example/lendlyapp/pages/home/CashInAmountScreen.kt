package com.example.lendlyapp.pages.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lendlyapp.ui.theme.interFontsSemiBold
import com.example.lendlyapp.ui.theme.interFontsRegular

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashInAmountScreen(
    onNavigateBack: () -> Unit,
    onNextClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    var amountText by remember { mutableStateOf("2500.00") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(color = Color(0xFF00C853))
                } else {
                    Button(
                        onClick = { 
                            val amount = amountText.replace(",", "").toDoubleOrNull() ?: 0.0
                            viewModel.performCashIn(amount, "Online Bank") {
                                onNextClick(amountText)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853)),
                        shape = RoundedCornerShape(50.dp)
                    ) {
                        Text(
                            text = "Next",
                            fontFamily = interFontsSemiBold,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF102000)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Cash-In Amount",
                fontFamily = interFontsSemiBold,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = "Balance: ₱${String.format("%.2f", viewModel.balance)}",
                fontFamily = interFontsRegular,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            // Contenedor del monto centrado
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "₱",
                        fontFamily = interFontsSemiBold,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                    BasicTextField(
                        value = amountText,
                        onValueChange = { amountText = it },
                        textStyle = TextStyle(
                            fontFamily = interFontsSemiBold,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.width(IntrinsicSize.Min)
                    )
                }
                
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(0.7f).padding(top = 8.dp),
                    thickness = 1.dp,
                    color = Color.LightGray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "BPI’s max limit is ₱10,000.00 per day",
                    fontFamily = interFontsRegular,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
