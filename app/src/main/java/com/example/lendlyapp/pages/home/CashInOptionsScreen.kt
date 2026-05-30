package com.example.lendlyapp.pages.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.ui.theme.interFontsSemiBold
import com.example.lendlyapp.ui.theme.interFontsRegular

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashInOptionsScreen(
    onNavigateBack: () -> Unit,
    onOptionSelected: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cash-In", fontFamily = interFontsSemiBold) },
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
                .padding(16.dp)
        ) {
            Text(
                text = "Cash-In Options",
                fontFamily = interFontsSemiBold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OptionItem(
                title = "Online Banking",
                subtitle = "Pay via other banks or e-wallet",
                iconRes = com.example.lendlyapp.R.drawable.loan_container, // Actualizado
                onClick = { onOptionSelected("online") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OptionItem(
                title = "Over-the-counter",
                subtitle = "Pay in cash",
                iconRes = com.example.lendlyapp.R.drawable.ic_shop, // Usamos uno existente por ahora
                onClick = { onOptionSelected("counter") }
            )
        }
    }
}

@Composable
fun OptionItem(
    title: String,
    subtitle: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = androidx.compose.ui.res.painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = Color(0xFF00C853)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontFamily = interFontsSemiBold, fontSize = 16.sp)
                Text(text = subtitle, fontFamily = interFontsRegular, fontSize = 12.sp, color = Color.Gray)
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.LightGray
            )
        }
    }
}
