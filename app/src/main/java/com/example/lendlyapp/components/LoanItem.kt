package com.example.lendlyapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.R
import com.example.lendlyapp.ui.theme.interFontsSemiBold
import com.example.lendlyapp.ui.theme.interFontsRegular

@Composable
fun LoanItem(
    companyName: String,
    amount: String,
    dueDate: String,
    logoResId: Int? = null,
    backgroundColor: Color = Color(0xFFF5F5F5)
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
                // Logo de la empresa
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(backgroundColor, shape = CircleShape)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (logoResId != null) {
                        Image(
                            painter = painterResource(id = logoResId),
                            contentDescription = "Logo $companyName",
                            modifier = Modifier.size(24.dp), // Ajustamos el tamaño del logo dentro del círculo
                            contentScale = ContentScale.Fit
                        )
                    }
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
