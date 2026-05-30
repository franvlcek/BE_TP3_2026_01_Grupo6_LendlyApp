package com.example.lendlyapp.pages.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.R
import com.example.lendlyapp.ui.theme.interFontsSemiBold
import com.example.lendlyapp.ui.theme.interFontsRegular

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnlineCashInScreen(
    onNavigateBack: () -> Unit,
    onBankSelected: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("", fontFamily = interFontsSemiBold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            Text(
                text = "Online Cash-In Options",
                fontFamily = interFontsSemiBold,
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Buscador
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search...", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFF0F0F0),
                    focusedBorderColor = Color(0xFF00C853),
                    unfocusedContainerColor = Color(0xFFF9F9F9),
                    focusedContainerColor = Color(0xFFF9F9F9)
                ),
                singleLine = true
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF9F9F9))
            ) {
                item {
                    Text(
                        text = "BANKS",
                        fontFamily = interFontsSemiBold,
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                val banks = listOf(
                    BankItemData("BPI", R.drawable.bpi_logo),
                    BankItemData("Chinabank", R.drawable.logo_china),
                    BankItemData("RCBC", R.drawable.rcbc_comercial),
                    BankItemData("Unionbank", R.drawable.union_bank_logo)
                )

                items(banks) { bank ->
                    BankRow(bank, onBankSelected)
                }

                item {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFEEEEEE))
                    Text(
                        text = "E-WALLETS",
                        fontFamily = interFontsSemiBold,
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                val wallets = listOf(
                    BankItemData("GCash", R.drawable.gcash_logo),
                    BankItemData("Pay Maya", R.drawable.paymaya_logo),
                    BankItemData("PayPal", R.drawable.paypal_logo)
                )

                items(wallets) { wallet ->
                    BankRow(wallet, onBankSelected)
                }
                
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

data class BankItemData(val name: String, val logoRes: Int)

@Composable
fun BankRow(bank: BankItemData, onBankSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onBankSelected(bank.name) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // El círculo ahora se llena con la imagen (ContentScale.Crop)
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = bank.logoRes),
                    contentDescription = bank.name,
                    modifier = Modifier.fillMaxSize(), // Imagen completa en el círculo
                    contentScale = ContentScale.Crop // Asegura que llene el círculo
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = bank.name,
                fontFamily = interFontsRegular,
                fontSize = 15.sp,
                color = Color.Black
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier.size(20.dp)
        )
    }
}
