package com.example.lendlyapp.pages.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.R
import com.example.lendlyapp.ui.theme.interFontsSemiBold
import com.example.lendlyapp.ui.theme.interFontsRegular

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverTheCounterScreen(
    onNavigateBack: () -> Unit,
    onPartnerSelected: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("", fontFamily = interFontsSemiBold) },
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
                .background(Color.White)
        ) {
            Text(
                text = "Over-The-Counter Partners",
                fontFamily = interFontsSemiBold,
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF9F9F9))
            ) {
                val partners = listOf(
                    PartnerItemData("7-Eleven", R.drawable.eleven_consumo),
                    PartnerItemData("Cebuana Lhuillier", R.drawable.cebuana_lhuillier_consumo),
                    PartnerItemData("LBC", R.drawable.lbc_consumo),
                    PartnerItemData("M Lhuillier", R.drawable.ml_consumo)
                )

                items(partners) { partner ->
                    PartnerRow(partner, onPartnerSelected)
                }
                
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

data class PartnerItemData(val name: String, val logoRes: Int)

@Composable
fun PartnerRow(partner: PartnerItemData, onPartnerSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPartnerSelected(partner.name) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = partner.logoRes),
                    contentDescription = partner.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = partner.name,
                    fontFamily = interFontsSemiBold,
                    fontSize = 15.sp,
                    color = Color.Black
                )
                Text(
                    text = "Max. Transaction amount $5,000",
                    fontFamily = interFontsRegular,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier.size(20.dp)
        )
    }
}
