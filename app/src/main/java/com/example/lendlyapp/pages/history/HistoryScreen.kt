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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lendlyapp.R
import com.example.lendlyapp.components.HistoryItem
import com.example.lendlyapp.components.TransactionType
import com.example.lendlyapp.ui.theme.interFontsSemiBold
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class FilterData(val name: String, val width: Dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onNavigateToDetail: (String) -> Unit,
    onNavigateToProfile: () -> Unit = {},
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
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
                    IconButton(onClick = onNavigateToProfile) {
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

            // 3. Filtros Horizontales
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

            // 4. Lista de Transacciones Dinámica
            when (val state = uiState) {
                is HistoryUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFF00C853))
                    }
                }
                is HistoryUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Error: ${state.message}", color = Color.Red)
                            Button(onClick = { viewModel.loadTransactions() }) {
                                Text("Retry")
                            }
                        }
                    }
                }
                is HistoryUiState.Success -> {
                    val filteredTransactions = state.transactions.filter {
                        it.title.contains(searchQuery, ignoreCase = true) || 
                        it.description.contains(searchQuery, ignoreCase = true)
                    }

                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(filteredTransactions) { transaction ->
                            val formattedTime = try {
                                val zdt = ZonedDateTime.parse(transaction.date)
                                zdt.format(DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH))
                            } catch (e: Exception) {
                                transaction.date
                            }

                            val transactionType = when (transaction.type) {
                                "CASH_IN", "LOAN_DISBURSEMENT" -> TransactionType.ADDED
                                else -> TransactionType.PAYMENT
                            }

                            Box(modifier = Modifier.clickable { onNavigateToDetail(transaction.id) }) {
                                HistoryItem(
                                    title = transaction.title,
                                    time = formattedTime,
                                    amount = "${String.format(Locale.US, "%.2f", transaction.amount)} ${transaction.currency}",
                                    company = transaction.description,
                                    type = transactionType
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
