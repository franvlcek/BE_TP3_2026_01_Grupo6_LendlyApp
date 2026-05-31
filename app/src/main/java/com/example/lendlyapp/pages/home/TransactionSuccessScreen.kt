package com.example.lendlyapp.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.ui.theme.interFontsSemiBold
import com.example.lendlyapp.ui.theme.interFontsRegular

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionSuccessScreen(
    amount: String,
    onDoneClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = onDoneClick) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Default.Info, contentDescription = "Info")
                    }
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Button(
                onClick = onDoneClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853)),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text(
                    text = "Done",
                    fontFamily = interFontsSemiBold,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold, // NEGRITA
                    color = Color(0xFF102000) // Color oscuro de Figma
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Icono de éxito (Círculo verde con + en negrita)
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF00C853)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold, // NEGRITA
                    color = Color(0xFF102000) // El color oscuro que pediste
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Added to your account",
                fontFamily = interFontsRegular,
                fontSize = 14.sp,
                color = Color.Gray
            )

            Text(
                text = "$amount PHP",
                fontFamily = interFontsSemiBold,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "from GCash",
                fontFamily = interFontsRegular,
                fontSize = 12.sp,
                color = Color.Gray
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Surface(
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Cash-In",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    fontSize = 12.sp,
                    fontFamily = interFontsSemiBold,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Detalles de la transacción
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Transaction Details",
                    fontFamily = interFontsSemiBold,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                DetailRowItem(label = "Transfer Fee", value = "-₱15.00")
                DetailRowItem(label = "Date & Time", value = "Jul 15, 2024 9:12 AM")
                DetailRowItem(label = "Transaction Number", value = "#200412312551", isHighlighted = true)
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Need help?",
                fontFamily = interFontsRegular,
                fontSize = 12.sp,
                color = Color.Gray
            )
            Text(
                text = "Go to Help Center",
                fontFamily = interFontsSemiBold,
                fontSize = 12.sp,
                color = Color(0xFF00C853),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }
    }
}

@Composable
fun DetailRowItem(label: String, value: String, isHighlighted: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontFamily = interFontsRegular,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontFamily = interFontsSemiBold,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = if (isHighlighted) Color(0xFF00C853) else Color.Black
        )
    }
}
