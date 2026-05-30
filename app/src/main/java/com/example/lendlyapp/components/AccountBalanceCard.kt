package com.example.lendlyapp.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.ui.theme.interFontsSemiBold

@Composable
fun AccountBalanceCard(
    balance: String,
    onCashInClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Account",
            fontFamily = interFontsSemiBold,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "AVAILABLE BALANCE",
                        fontFamily = interFontsSemiBold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = balance,
                        fontFamily = interFontsSemiBold,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                // Botón Cash In con medidas exactas de Figma
                Button(
                    onClick = onCashInClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00C853) // Verde Lendly
                    ),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .width(120.dp)
                        .height(48.dp),
                    contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 24.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "+ ",
                            fontSize = 16.sp,
                            color = Color(0xFF102000), // Color Negro/Dark Green de Figma
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Cash In",
                            fontFamily = interFontsSemiBold,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W600, // Semi Bold
                            letterSpacing = 0.5.sp,
                            color = Color(0xFF102000) // Color Negro/Dark Green de Figma
                        )
                    }
                }
            }
        }
    }
}
