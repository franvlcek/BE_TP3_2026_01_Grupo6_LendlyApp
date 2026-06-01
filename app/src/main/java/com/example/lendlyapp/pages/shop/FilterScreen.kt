package com.example.lendlyapp.pages.shop

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class FilterState(
    val selectedBrand: Set<String> = emptySet(),
    val selectedGender: Set<String> = emptySet(),
    val selectedSort: String = "",
    val selectedPriceRange: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    onNavigateBack: () -> Unit = {},
    onApplyFilters: (FilterState) -> Unit = {}
) {
    var filterState by remember {
        mutableStateOf(
            FilterState(
                selectedBrand = setOf("All"),
                selectedGender = setOf("All"),
                selectedSort = "Most Recent",
                selectedPriceRange = "All"
            )
        )
    }

    val brands = listOf("All", "Nike", "Adidas", "Puma", "Jordan")
    val genders = listOf("All", "Men", "Women")
    val sortOptions = listOf("Most Recent", "Popular", "Low Interest")
    val priceOptions = listOf("All", "$500 - $1000", "$1000 - $5000")

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Filter",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Text(text = "←", fontSize = 20.sp, color = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    navigationIconContentColor = Color.Black,
                    titleContentColor = Color.Black
                ),
                modifier = Modifier.height(56.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 8.dp, vertical = 12.dp)
            ) {
                // Brand Filter
                FilterSectionSimple(title = "Brands") {
                    SimpleChipsRow(
                        items = brands,
                        selected = filterState.selectedBrand,
                        onSelectionChange = { selection ->
                            filterState = filterState.copy(selectedBrand = selection)
                        }
                    )
                }

                // Gender Filter
                FilterSectionSimple(title = "Gender") {
                    SimpleChipsRow(
                        items = genders,
                        selected = filterState.selectedGender,
                        onSelectionChange = { selection ->
                            filterState = filterState.copy(selectedGender = selection)
                        }
                    )
                }

                // Sort By Filter
                FilterSectionSimple(title = "Sort by") {
                    val sortSelection = if (filterState.selectedSort.isEmpty()) emptySet() else setOf(filterState.selectedSort)
                    SimpleChipsRow(
                        items = sortOptions,
                        selected = sortSelection,
                        onSelectionChange = { selection ->
                            filterState = filterState.copy(selectedSort = selection.firstOrNull() ?: "")
                        },
                        isSingleSelection = true
                    )
                }

                FilterSectionSimple(title = "Price Range") {
                    val priceSelection = if (filterState.selectedPriceRange.isEmpty()) emptySet() else setOf(filterState.selectedPriceRange)
                    SimpleChipsRow(
                        items = priceOptions,
                        selected = priceSelection,
                        onSelectionChange = { selection ->
                            filterState = filterState.copy(selectedPriceRange = selection.firstOrNull() ?: "")
                        },
                        isSingleSelection = true
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        filterState = FilterState()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF9A9A9A)
                    ),
                    border = BorderStroke(1.dp, Color(0xFFD0D0D0)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "Reset Filter",
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                }

                Button(
                    onClick = {
                        onApplyFilters(filterState)
                        onNavigateBack()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7BF179)
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "Apply",
                        color = Color(0xFF102000),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun FilterSectionSimple(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 10.dp),
            color = Color.Black
        )
        content()
    }
}

@Composable
fun SimpleChipsRow(
    modifier: Modifier = Modifier,
    items: List<String>,
    selected: Set<String>,
    onSelectionChange: (Set<String>) -> Unit,
    isSingleSelection: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items.forEach { item ->
                val isSelected = item in selected
                FilterChipSimple(
                    text = item,
                    isSelected = isSelected,
                    onClick = {
                        val newSelection = if (isSingleSelection) {
                            if (isSelected) emptySet() else setOf(item)
                        } else {
                            if (isSelected) selected - item else selected + item
                        }
                        onSelectionChange(newSelection)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun FilterChipSimple(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(32.dp)
            .background(
                color = if (isSelected) Color(0xFF7BF179) else Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .border(1.dp, Color(0xFFC7C7C7), RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = if (isSelected) Color(0xFF102000) else Color(0xFF7A7A7A)
        )
    }
}



















