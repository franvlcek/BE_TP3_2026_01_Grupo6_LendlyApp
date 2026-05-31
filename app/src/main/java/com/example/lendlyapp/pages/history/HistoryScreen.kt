package com.example.lendlyapp.pages.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.R
import com.example.lendlyapp.components.HistoryItem
import com.example.lendlyapp.components.TransactionType
import com.example.lendlyapp.ui.theme.interFontsSemiBold
import com.example.lendlyapp.ui.theme.interFontsRegular

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onNavigateToDetail: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filters = listOf(
        FilterData("All", 50.dp),
        FilterData("Type", 70.dp),
        FilterData("Balance", 85.dp),
        FilterData("Paid Bills", 94.dp),
        FilterData("Added", 78.dp)
    )
    var selectedFilter by remember { mutableStateOf("All") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.icono_tres_capas), 
                        contentDescription = "Lendly Logo",
                        modifier = Modifier.size(width = 58.dp, height = 20.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* TODO Profile */ }) {
                        Icon(
                            imageVector = Icons.Default.Person, 
                            contentDescription = "Profile",
                            modifier = Modifier.size(32.dp),
                            tint = Color.Black
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO Notifications */ }) {
                        Icon(
                            imageVector = Icons.Default.Notifications, 
                            contentDescription = "Notifications",
                            modifier = Modifier.size(32.dp),
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            // 1. Título "History"
            Text(
                text = "History",
                fontFamily = interFontsSemiBold,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
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

            // 3. Filtros Horizontales con medidas de Figma
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filters) { filter ->
                    FilterChip(
                        modifier = Modifier
                            .height(32.dp)
                            .widthIn(min = filter.width),
                        selected = selectedFilter == filter.name,
                        onClick = { selectedFilter = filter.name },
                        label = { 
                            Text(
                                text = filter.name, 
                                style = TextStyle(
                                    fontFamily = interFontsSemiBold,
                                    fontWeight = FontWeight.W600,
                                    fontSize = 14.sp,
                                    letterSpacing = 0.1.sp,
                                    lineHeight = 20.sp
                                )
                            ) 
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF00C853),
                            selectedLabelColor = Color.White,
                            labelColor = Color.Black
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = selectedFilter == filter.name,
                            borderColor = Color(0xFFF0F0F0),
                            borderWidth = 1.dp,
                            selectedBorderColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(8.dp)
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
                    Box(modifier = Modifier.clickable { onNavigateToDetail() }) {
                        HistoryItem(
                            title = "Paid this month",
                            time = "9:07 AM",
                            amount = "1,255.00 PHP",
                            company = "Apple Inc.",
                            type = TransactionType.PAYMENT
                        )
                    }
                }

                item {
                    HistoryItem(
                        title = "Paid this month",
                        time = "9:07 AM",
                        amount = "1,255.00 PHP",
                        company = "Apple Inc.",
                        type = TransactionType.PAYMENT
                    )
                }

                item {
                    HistoryItem(
                        title = "Added",
                        time = "9:07 AM",
                        amount = "1,200.00 PHP",
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
}

data class FilterData(val name: String, val width: androidx.compose.ui.unit.Dp)
