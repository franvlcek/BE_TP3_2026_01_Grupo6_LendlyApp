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
import com.example.lendlyapp.ui.theme.interFontsSemiBold
import com.example.lendlyapp.ui.theme.interFontsRegular

@Composable
fun LoanItem(
    companyName: String,
    amount: String,
    dueDate: String,
    logoResId: Int? = null,
    backgroundColor: Color = Color(0xFFF9F9F9)
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Contenedor del logo más grande como en Figma
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(backgroundColor, shape = CircleShape)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (logoResId != null) {
                        Image(
                            painter = painterResource(id = logoResId),
                            contentDescription = "Logo $companyName",
                            modifier = Modifier.size(28.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(14.dp))
                
                Column {
                    Text(
                        text = companyName,
                        fontFamily = interFontsSemiBold,
                        fontSize = 15.sp
                    )
                    Text(
                        text = dueDate,
                        fontFamily = interFontsRegular,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = amount,
                    fontFamily = interFontsSemiBold,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
