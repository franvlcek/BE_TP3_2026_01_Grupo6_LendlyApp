@file:Suppress("UNUSED_PARAMETER")

package com.example.lendlyapp.pages.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("UNUSED_PARAMETER")
@Composable
fun ShopScreen(
    @Suppress("UNUSED_PARAMETER") onNavigateToProduct: (String) -> Unit = {},
    onNavigateToSearch: () -> Unit = {},
    onNavigateToFilter: () -> Unit = {}
) {
    val navigateToProduct = onNavigateToProduct
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.frame_134),
                        contentDescription = "Logo",
                        modifier = Modifier.size(40.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* Profile */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.user_icon), 
                            contentDescription = "Profile", 
                            modifier = Modifier.size(24.dp),
                            tint = Color.Unspecified
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Notifications */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.notification_icon), 
                            contentDescription = "Notifications", 
                            modifier = Modifier.size(24.dp),
                            tint = Color.Unspecified
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Search Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search for product", color = Color.Gray) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigateToSearch() },
                    enabled = false, // Make it look like a button
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledBorderColor = Color(0xFFF5F5F5),
                        disabledPlaceholderColor = Color.Gray,
                        disabledLeadingIconColor = Color.Gray,
                        disabledContainerColor = Color(0xFFF9F9F9)
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF7BF179))
                        .clickable { onNavigateToFilter() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Tune, contentDescription = "Filter", tint = Color(0xFF102000))
                }
            }

            // Banner - The New Shoes (Promotional Card)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { navigateToProduct("promo") },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0A2000)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.promotional_card_img),
                    contentDescription = "The New Shoes Promotion",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            // Shop By Category
            SectionHeader(title = "Shop By Category")
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { CategoryItem("Phone", R.drawable.img_iphone) }
                item { CategoryItem("Headphones", R.drawable.img_headphones) }
                item { CategoryItem("Apparel", R.drawable.img_sneakers) }
                item { CategoryItem("Phone", R.drawable.img_iphone) }
            }

            // Popular Brands
            SectionHeader(title = "Popular Brands")
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { BrandItem("Apple", R.drawable.logo_apple) }
                item { BrandItem("Jordan", R.drawable.logo_nike) } // Using Nike for Jordan placeholder
                item { BrandItem("Adidas", R.drawable.logo_nike) } // Using Nike for Adidas placeholder
            }

            // Recommended For You
            SectionHeader(title = "Recommended For You")
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { ProductItem("iPhone 12 Pro...", "₱1,200 × 24 mo", R.drawable.img_iphone) { navigateToProduct("iphone") } }
                item { ProductItem("Headphones", "₱1,200 × 24 mo", R.drawable.img_headphones) { navigateToProduct("headphones") } }
                item { ProductItem("Sneakers", "₱1,200 × 24 mo", R.drawable.img_sneakers) { navigateToProduct("sneakers") } }
            }

            // Best Sellers
            SectionHeader(title = "Best Sellers")
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { ProductItem("Surface Laptop", "₱1,200 × 24 mo", R.drawable.img_iphone) { navigateToProduct("surface_laptop") } }
                item { ProductItem("iPhone 12 Pro...", "₱1,200 × 24 mo", R.drawable.img_iphone) { navigateToProduct("iphone") } }
                item { ProductItem("PS4 Play Stat...", "₱1,200 × 24 mo", R.drawable.img_headphones) { navigateToProduct("ps4") } }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = "See All ->", fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun CategoryItem(name: String, imageRes: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF9F9F9)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier.size(50.dp),
                contentScale = ContentScale.Fit
            )
        }
        Text(text = name, modifier = Modifier.padding(top = 8.dp), fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun BrandItem(name: String, imageRes: Int) {
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(80.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF9F9F9)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier.size(40.dp).padding(4.dp),
                contentScale = ContentScale.Fit
            )
            Text(text = name, fontSize = 12.sp, color = Color.Gray)
        }
        // Small brand logo at bottom right as in the picture
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.size(16.dp).align(Alignment.BottomEnd).padding(end = 8.dp, bottom = 8.dp),
            alpha = 0.5f
        )
    }
}

@Composable
fun ProductItem(name: String, price: String, imageRes: Int, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .width(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF9F9F9))
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = name, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1)
            Text(text = price, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopScreenPreview() {
    ShopScreen()
}
