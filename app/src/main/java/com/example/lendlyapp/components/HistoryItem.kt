package com.example.lendlyapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.ui.theme.interFontsSemiBold
import com.example.lendlyapp.ui.theme.interFontsRegular

enum class TransactionType {
    PAYMENT, ADDED
}

@Composable
fun HistoryItem(
    title: String,
    time: String,
    amount: String,
    company: String,
    type: TransactionType
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Icono de la transacción
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color(0xFFF9F9F9),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (type == TransactionType.PAYMENT) 
                        Icons.AutoMirrored.Filled.ArrowForward else Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = if (type == TransactionType.PAYMENT) Color.Gray else Color(0xFF00C853)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = time,
                    fontFamily = interFontsRegular,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
                Text(
                    text = title,
                    fontFamily = interFontsSemiBold,
                    fontSize = 14.sp
                )
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = company,
                fontFamily = interFontsRegular,
                fontSize = 11.sp,
                color = Color.Gray
            )
            Text(
                text = amount,
                fontFamily = interFontsSemiBold,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
