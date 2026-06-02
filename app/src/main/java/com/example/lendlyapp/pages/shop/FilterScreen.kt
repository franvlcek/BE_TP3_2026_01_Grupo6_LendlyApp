package com.example.lendlyapp.pages.shop

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterScreen(
    onNavigateBack: () -> Unit = {},
    onApplyFilters: (String) -> Unit = {}
) {
    var selectedBrand by remember { mutableStateOf("All") }
    var selectedGender by remember { mutableStateOf("All") }
    var selectedSort by remember { mutableStateOf("Most Recent") }
    var selectedPriceRange by remember { mutableStateOf("All") }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { 
                    Text("Filter", 
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        selectedBrand = "All"
                        selectedGender = "All"
                        selectedSort = "Most Recent"
                        selectedPriceRange = "All"
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(100.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray)
                ) {
                    Text("Reset Filter", color = Color.Gray, fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = { onApplyFilters("applied") },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7BF179),
                        contentColor = Color(0xFF102000)
                    ),
                    shape = RoundedCornerShape(100.dp)
                ) {
                    Text("Apply", fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            FilterSection(
                title = "Brands",
                options = listOf("All", "Nike", "Adidas", "Puma", "Jordan"),
                selectedOption = selectedBrand,
                onOptionSelected = { selectedBrand = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            FilterSection(
                title = "Gender",
                options = listOf("All", "Men", "Women"),
                selectedOption = selectedGender,
                onOptionSelected = { selectedGender = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            FilterSection(
                title = "Sort by",
                options = listOf("Most Recent", "Popular", "Low Interest"),
                selectedOption = selectedSort,
                onOptionSelected = { selectedSort = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            FilterSection(
                title = "Price Range",
                options = listOf("All", "$500 - $1000", "$1000 - $5000"),
                selectedOption = selectedPriceRange,
                onOptionSelected = { selectedPriceRange = it }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterSection(
    title: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { option ->
                val isSelected = option == selectedOption
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) Color(0xFF7BF179) else Color.White)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) Color.Transparent else Color.LightGray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { onOptionSelected(option) }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = option,
                        color = if (isSelected) Color(0xFF102000) else Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilterScreenPreview() {
    FilterScreen()
}
