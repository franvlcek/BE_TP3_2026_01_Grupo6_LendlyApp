package com.example.lendlyapp.pages.loan

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
    viewModel: LoanViewModel,
    onNavigateBack: () -> Unit,
    onNavigateNext: () -> Unit
) {
    var amountText by remember { mutableStateOf("2000.00") }
    var selectedPlan by remember { mutableStateOf("6 Months") }
    val purposes = listOf("Educational", "Medical", "Business", "Personal", "Other")
    var selectedPurpose by remember { mutableStateOf(purposes[0]) }
    var expanded by remember { mutableStateOf(false) }

    // Si la solicitud fue exitosa, navegamos a la pantalla de éxito
    LaunchedEffect(viewModel.loanAppliedSuccess) {
        if (viewModel.loanAppliedSuccess) {
            onNavigateNext()
            viewModel.loanAppliedSuccess = false // Reset state
        }
    }

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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(80.dp),
                contentAlignment = Alignment.Center
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(color = Color(0xFF00C853))
                } else {
                    Button(
                        onClick = { 
                            val amount = amountText.toDoubleOrNull() ?: 0.0
                            viewModel.applyForLoan(amount, selectedPlan, selectedPurpose) 
                        },
                        modifier = Modifier
                            .fillMaxWidth()
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

            // Paso 1: Monto
            item {
                Column {
                    StepBadge(step = 1)
                    Text(
                        text = "Enter loan amount",
                        fontFamily = interFontsSemiBold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                    OutlinedTextField(
                        value = amountText,
                        onValueChange = { amountText = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(
                            fontSize = 28.sp, 
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        prefix = { Text("₱ ", fontSize = 28.sp, fontWeight = FontWeight.Bold) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF00C853),
                            unfocusedBorderColor = Color.LightGray,
                            cursorColor = Color(0xFF00C853)
                        )
                    )
                }
            }

            // Paso 2: Plan
            item {
                Column {
                    StepBadge(step = 2)
                    Text(
                        text = "Select an installment plan",
                        fontFamily = interFontsSemiBold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                    
                    val plans = listOf("6 Months", "12 Months")
                    plans.forEach { plan ->
                        val isSelected = selectedPlan == plan
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { selectedPlan = plan }
                                .border(
                                    width = 1.dp, 
                                    color = if (isSelected) Color(0xFF7BF179) else Color.Transparent, 
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) Color(0xFFF1FFF1) else Color(0xFFF9F9F9)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(plan, fontFamily = interFontsSemiBold, fontSize = 16.sp)
                                    Text(
                                        text = if(plan == "6 Months") "2.99% Interest" else "4.99% Interest", 
                                        color = Color.Gray, 
                                        fontSize = 12.sp
                                    )
                                }
                                val monthly = if(plan == "6 Months") "₱ 982.12/mo" else "₱ 491.06/mo"
                                Text(
                                    text = monthly, 
                                    fontWeight = FontWeight.Bold, 
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }

            // Paso 3: Propósito
            item {
                Column {
                    StepBadge(step = 3)
                    Text(
                        text = "Select your loan purpose",
                        fontFamily = interFontsSemiBold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                    
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = selectedPurpose,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF00C853),
                                unfocusedBorderColor = Color.LightGray
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            purposes.forEach { purpose ->
                                DropdownMenuItem(
                                    text = { Text(purpose, fontFamily = interFontsRegular) },
                                    onClick = {
                                        selectedPurpose = purpose
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Resumen
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
                    
                    val amount = amountText.toDoubleOrNull() ?: 0.0
                    val processingFee = amount * 0.03
                    val total = amount - processingFee
                    
                    SummaryRow("Loan Amount", "PHP $amountText")
                    SummaryRow("3% Processing Fee", "-${String.format("%.2f", processingFee)}")
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                    SummaryRow("Total amount to Receive", "₱ ${String.format("%.2f", amount)}", isBold = true)
                    SummaryRow("Lender", "Rayland Finance")
                    
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

            // Mostrar error si existe
            viewModel.errorMessage?.let { error ->
                item {
                    Text(
                        text = error, 
                        color = Color.Red, 
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
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
