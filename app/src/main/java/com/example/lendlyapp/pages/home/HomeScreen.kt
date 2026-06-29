package com.example.lendlyapp.pages.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.R
import java.util.Locale
import com.example.lendlyapp.components.AccountBalanceCard
import com.example.lendlyapp.components.LoanItem
import com.example.lendlyapp.components.ProductCard
import com.example.lendlyapp.ui.theme.interFontsSemiBold

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToCashIn: () -> Unit,
    onNavigateToProduct: (String) -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {}
) {
    // Usamos el balance del ViewModel que tiene el fallback de la sesión
    val balanceText = "₱ ${String.format(Locale.US, "%,.2f", viewModel.balance)}"
    
    Scaffold(
        topBar = {
            HomeTopBar(
                onNavigateToProfile = onNavigateToProfile,
                onNavigateToNotifications = onNavigateToNotifications
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 8.dp)
        ) {
            // 1. Tarjeta de Saldo
            item {
                AccountBalanceCard(
                    balance = balanceText,
                    onCashInClick = onNavigateToCashIn
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
                Spacer(modifier = Modifier.height(16.dp)) 
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
                    contentPadding = PaddingValues(horizontal = 10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        ProductCard(
                            name = "iPhone 12 Pro Max",
                            price = "₱1,200 x 24 mo",
                            imageResId = R.drawable.img_iphone,
                            onClick = { onNavigateToProduct("iphone") }
                        )
                    }
                    item {
                        ProductCard(
                            name = "Headphones",
                            price = "₱500 x 6 mo",
                            imageResId = R.drawable.img_headphones,
                            onClick = { onNavigateToProduct("headphones") }
                        )
                    }
                    item {
                        ProductCard(
                            name = "Nike Sneakers",
                            price = "₱800 x 12 mo",
                            imageResId = R.drawable.img_sneakers,
                            onClick = { onNavigateToProduct("sneakers") }
                        )
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    onNavigateToProfile: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.icono_tres_capas), 
                contentDescription = "Lendly Logo",
                modifier = Modifier.size(width = 58.dp, height = 20.dp)
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateToProfile) {
                Icon(
                    imageVector = Icons.Default.Person, 
                    contentDescription = "Profile",
                    modifier = Modifier.size(32.dp),
                    tint = Color.Black
                )
            }
        },
        actions = {
            IconButton(onClick = onNavigateToNotifications) {
                Icon(
                    imageVector = Icons.Default.Notifications, 
                    contentDescription = "Notifications",
                    modifier = Modifier.size(32.dp),
                    tint = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White
        )
    )
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
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        TextButton(
            onClick = onSeeAllClick,
            contentPadding = PaddingValues(0.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "See All", 
                    color = Color.Black, // Bold y Negro
                    fontSize = 14.sp,
                    fontFamily = interFontsSemiBold,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.Black // Negro para combinar con el texto bold
                )
            }
        }
    }
}
