package com.example.lendlyapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.example.lendlyapp.ui.theme.interFontsRegular

@Composable
fun LoanItem(
    companyName: String,
    amount: String,
    dueDate: String,
    color: Color = Color.Black
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Placeholder para el logo de la empresa (Nike/Apple)
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    // Aquí irá el ícono real cuando lo tengamos
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column {
                    Text(
                        text = companyName,
                        fontFamily = interFontsSemiBold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = dueDate,
                        fontFamily = interFontsRegular,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            
            Text(
                text = amount,
                fontFamily = interFontsSemiBold,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
