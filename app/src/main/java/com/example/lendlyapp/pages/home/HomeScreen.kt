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
import androidx.compose.foundation.lazy.LazyRow
import com.example.lendlyapp.components.AccountBalanceCard
import com.example.lendlyapp.components.LoanItem
import com.example.lendlyapp.R
import com.example.lendlyapp.components.ProductCard
import com.example.lendlyapp.ui.theme.interFontsSemiBold
import com.example.lendlyapp.ui.theme.interFontsRegular

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
            SectionHeader(title = "Unpaid Loans", onSeeAllClick = { /* TODO */ })
        }
        
        item {
            LoanItem(
                companyName = "Nike Inc.",
                amount = "₱400.00",
                dueDate = "Fees of February",
                logoResId = R.drawable.logo_nike
            )
        }
        
        item {
            LoanItem(
                companyName = "Apple Inc.",
                amount = "₱1,500.00",
                dueDate = "Fees of March",
                logoResId = R.drawable.logo_apple
            )
        }

        // 3. Sección Recommended For You
        item {
            SectionHeader(title = "Recommended For You", onSeeAllClick = { /* TODO */ })
        }

        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(5) {
                    ProductCard(
                        name = "iPhone 12 Pro Max",
                        price = "₱1,200 x 24 mo"
                    )
                }
            }
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    onSeeAllClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontFamily = interFontsSemiBold,
            fontSize = 18.sp
        )
        TextButton(onClick = onSeeAllClick) {
            Text(text = "See All", color = Color.Gray)
        }
    }
}
