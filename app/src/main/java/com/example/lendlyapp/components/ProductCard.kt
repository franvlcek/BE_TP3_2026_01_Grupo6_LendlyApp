package com.example.lendlyapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.ui.theme.interFontsSemiBold
import com.example.lendlyapp.ui.theme.interFontsRegular

@Composable
fun ProductCard(
    name: String,
    price: String,
    imageUrl: String = "" // Placeholder para cuando usemos Coil
) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            // Placeholder para la imagen del producto
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
            ) {
                // Aquí irá el AsyncImage de Coil después
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = name,
                fontFamily = interFontsSemiBold,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Text(
                text = price,
                fontFamily = interFontsRegular,
                fontSize = 11.sp,
                color = Color.Gray
            )
        }
    }
}
