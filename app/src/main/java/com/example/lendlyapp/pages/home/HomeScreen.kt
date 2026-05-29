package com.example.lendlyapp.pages.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.components.AccountBalanceCard
import com.example.lendlyapp.components.LoanItem
import com.example.lendlyapp.ui.theme.interFontsSemiBold

@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        // 1. Tarjeta de Saldo
        item {
            AccountBalanceCard(
                balance = "₱ 2,500.00",
                onCashInClick = { /* TODO */ }
            )
        }
        
        // 2. Sección Unpaid Loans
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Unpaid Loans",
                    fontFamily = interFontsSemiBold,
                    fontSize = 18.sp
                )
                TextButton(onClick = { /* TODO */ }) {
                    Text(text = "See All", color = Color.Gray)
                }
            }
        }
        
        item {
            LoanItem(
                companyName = "Nike Inc.",
                amount = "₱400.00",
                dueDate = "Fees of February",
                color = Color.Black
            )
        }
        
        item {
            LoanItem(
                companyName = "Apple Inc.",
                amount = "₱1,500.00",
                dueDate = "Fees of March",
                color = Color.LightGray
            )
        }
    }
}
