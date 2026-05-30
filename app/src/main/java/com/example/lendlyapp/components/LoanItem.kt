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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.ui.theme.interFontsSemiBold
import com.example.lendlyapp.ui.theme.interFontsRegular

@Composable
fun LoanItem(
    companyName: String,
    amount: String,
    dueDate: String,
    logoResId: Int? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFCF8F8)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Logo Circle (40x40)
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    if (logoResId != null) {
                        Image(
                            painter = painterResource(id = logoResId),
                            contentDescription = "Logo $companyName",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(10.dp))
                
                // Nombre de la empresa (Uso wrapContentWidth para no empujar lo de la derecha)
                Text(
                    text = companyName,
                    style = TextStyle(
                        fontFamily = interFontsSemiBold,
                        fontWeight = FontWeight.W600,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        letterSpacing = 0.15.sp,
                        color = Color(0xFF171D1E)
                    )
                )
            }
            
            // Bloque de Monto y Fecha (Aumentamos el ancho o usamos weight para que no se corte)
            Column(
                modifier = Modifier.wrapContentWidth(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = amount,
                    style = TextStyle(
                        fontFamily = interFontsSemiBold,
                        fontWeight = FontWeight.W600,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        letterSpacing = 0.1.sp,
                        textAlign = TextAlign.Right,
                        color = Color(0xFF171D1E)
                    )
                )
                Text(
                    text = dueDate,
                    style = TextStyle(
                        fontFamily = interFontsRegular,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 0.5.sp,
                        textAlign = TextAlign.Right,
                        color = Color(0xFF6A6C6A)
                    )
                )
            }
        }
    }
}
