package com.example.lendlyapp.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
    val montserratSemiBold = FontFamily(Font(com.example.lendlyapp.R.font.montserrat_extra_bold, FontWeight.SemiBold))

    Scaffold(
        topBar = {
            // Título centrado como en Figma
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "Cash-In", 
                        style = TextStyle(
                            fontFamily = interFontsSemiBold,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO Info */ }) {
                        Icon(
                            imageVector = Icons.Default.Info, 
                            contentDescription = "Info",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                modifier = Modifier.height(64.dp),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
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
                style = TextStyle(
                    fontFamily = montserratSemiBold,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    lineHeight = 32.sp,
                    letterSpacing = 0.sp,
                    color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OptionItem(
                        title = "Online Banking",
                        subtitle = "Pay via other banks or e-wallet",
                        icon = Icons.Default.Person, // Placeholder para Wallet
                        onClick = { onOptionSelected("online") }
                    )

                    OptionItem(
                        title = "Over-the-counter",
                        subtitle = "Pay in cash",
                        icon = Icons.Default.Notifications, // Placeholder para Counter
                        onClick = { onOptionSelected("counter") }
                    )
                }
            }
        }
    }
}

@Composable
fun OptionItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon Container con specs de Figma
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFE5F5EA), shape = CircleShape)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(width = 18.dp, height = 17.dp),
                tint = Color(0xFF002203)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title, 
                fontFamily = interFontsSemiBold, 
                fontSize = 16.sp,
                lineHeight = 24.sp
            )
            Text(
                text = subtitle, 
                style = TextStyle(
                    fontFamily = interFontsRegular,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 0.5.sp,
                    color = Color(0xFF6A6C6A)
                )
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
