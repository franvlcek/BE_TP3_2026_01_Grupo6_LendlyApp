package com.example.lendlyapp.pages.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.components.HistoryItem
import com.example.lendlyapp.components.TransactionType
import com.example.lendlyapp.ui.theme.interFontsSemiBold
import com.example.lendlyapp.ui.theme.interFontsRegular

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen() {
    var searchQuery by remember { mutableStateOf("") }
    val filters = listOf("All", "Type", "Balance", "Paid Bills", "Added")
    var selectedFilter by remember { mutableStateOf("All") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 1. Título "History"
        Text(
            text = "History",
            fontFamily = interFontsSemiBold,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )

        // 2. Buscador
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = { Text("Search...", color = Color.Gray) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFF0F0F0),
                focusedBorderColor = Color(0xFF00C853),
                unfocusedContainerColor = Color(0xFFF9F9F9),
                focusedContainerColor = Color(0xFFF9F9F9)
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Filtros Horizontales
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filters) { filter ->
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { selectedFilter = filter },
                    label = { Text(filter, fontFamily = interFontsRegular) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFFE8F5E9),
                        selectedLabelColor = Color(0xFF00C853)
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = selectedFilter == filter,
                        borderColor = Color(0xFFF0F0F0),
                        selectedBorderColor = Color.Transparent
                    )
                )
            }
        }

        HorizontalDivider(modifier = Modifier.padding(top = 16.dp), color = Color(0xFFF0F0F0))

        // 4. Lista de Transacciones
        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                Text(
                    text = "Today",
                    fontFamily = interFontsRegular,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(16.dp)
                )
            }

            item {
                HistoryItem(
                    title = "Paid this month",
                    time = "9:07 AM",
                    amount = "1,255 PHP",
                    company = "Apple Inc.",
                    type = TransactionType.PAYMENT
                )
            }

            item {
                HistoryItem(
                    title = "Paid this month",
                    time = "9:07 AM",
                    amount = "1,255 PHP",
                    company = "Apple Inc.",
                    type = TransactionType.PAYMENT
                )
            }

            item {
                HistoryItem(
                    title = "Added",
                    time = "9:07 AM",
                    amount = "1,200 PHP",
                    company = "Apple Inc.",
                    type = TransactionType.ADDED
                )
            }

            item {
                Text(
                    text = "Recent Loans",
                    fontFamily = interFontsRegular,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(16.dp)
                )
            }
            
            // Reutilizamos el estilo para la sección de préstamos recientes
            item {
                HistoryItem(
                    title = "iPhone 15 Pro Max",
                    time = "02/08/2024",
                    amount = "Paid",
                    company = "Apple Inc.",
                    type = TransactionType.PAYMENT
                )
            }
        }
    }
}
