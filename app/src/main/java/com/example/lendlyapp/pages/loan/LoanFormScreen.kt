package com.example.lendlyapp.pages.loan

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.ui.theme.interFontsSemiBold
import com.example.lendlyapp.ui.theme.interFontsRegular

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanFormScreen(
    onNavigateBack: () -> Unit,
    onNavigateNext: () -> Unit
) {
    var amount by remember { mutableStateOf("2,000.00") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Loan", fontFamily = interFontsSemiBold, fontSize = 16.sp) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Default.Info, contentDescription = "Info")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Button(
                onClick = onNavigateNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7BF179)),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text(
                    text = "Get This Loan",
                    style = TextStyle(
                        fontFamily = interFontsSemiBold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF102000)
                    )
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Text(
                        text = "Please provide your details\nfor your loan",
                        style = TextStyle(
                            fontFamily = interFontsSemiBold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            lineHeight = 32.sp,
                            color = Color.Black
                        )
                    )
                    Text(
                        text = "Please provide your details for your loan",
                        style = TextStyle(
                            fontFamily = interFontsRegular,
                            fontSize = 14.sp,
                            color = Color.Gray
                        ),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            // Step 1
            item {
                Column {
                    StepBadge(step = 1)
                    Text(
                        text = "Enter loan amount",
                        fontFamily = interFontsSemiBold,
                        fontWeight = FontWeight.W600,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("₱", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                        Text(amount, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                    }
                    HorizontalDivider(modifier = Modifier.padding(top = 8.dp), color = Color.LightGray)
                }
            }

            // Step 2
            item {
                Column {
                    StepBadge(step = 2)
                    Text(
                        text = "Select an installment plan",
                        fontFamily = interFontsSemiBold,
                        fontWeight = FontWeight.W600,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color(0xFF7BF179), RoundedCornerShape(12.dp)),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("6 Months", fontFamily = interFontsSemiBold, fontSize = 16.sp)
                                Text("2.99% Interest", color = Color.Gray, fontSize = 12.sp)
                            }
                            Text("₱ 982.12/mo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                }
            }

            // Step 3
            item {
                Column {
                    StepBadge(step = 3)
                    Text(
                        text = "Select your loan purpose",
                        fontFamily = interFontsSemiBold,
                        fontWeight = FontWeight.W600,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Educational", fontFamily = interFontsRegular)
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
                        }
                    }
                }
            }

            // Summary
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(
                        text = "Summary",
                        fontFamily = interFontsSemiBold,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    SummaryRow("Loan Amount", "PHP 2,000.00")
                    SummaryRow("3% Processing Fee", "-150.00")
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                    SummaryRow("Total amount to Receive", "₱ 2,000.00", isBold = true)
                    SummaryRow("Lender", "null")
                    Text(
                        "What is this?",
                        style = TextStyle(
                            fontFamily = interFontsSemiBold,
                            fontSize = 12.sp,
                            textDecoration = TextDecoration.Underline,
                            color = Color.Black
                        ),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
            
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
fun StepBadge(step: Int) {
    Surface(
        color = Color(0xFFE5F5EA),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.height(24.dp)
    ) {
        Text(
            text = "Step $step",
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            fontSize = 12.sp,
            color = Color(0xFF1F3701),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun SummaryRow(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray, fontSize = 14.sp)
        Text(
            value, 
            fontSize = 14.sp, 
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            fontFamily = if (isBold) interFontsSemiBold else interFontsRegular
        )
    }
}
