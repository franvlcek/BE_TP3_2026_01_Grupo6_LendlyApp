package com.example.lendlyapp.pages.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.R
import com.example.lendlyapp.data.model.Product

data class PromoData(
    val title: String,
    val subtitle: String,
    val imageRes: Int,
    val bgColor: Color = Color(0xFF102000),
    val showGreenBlock: Boolean = true
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(
    viewModel: ShopViewModel,
    onNavigateToProduct: (String) -> Unit = {},
    onNavigateToSearch: () -> Unit = {},
    onNavigateToFilter: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val promoItems = listOf(
        PromoData(
            title = "The New Shoes",
            subtitle = "Shop this season's Top Silhouette",
            imageRes = R.drawable.promotional_shoes
        ),
        PromoData(
            title = "New iPhone 15",
            subtitle = "Discover the best\ntechnology",
            imageRes = R.drawable.img_iphone
        ),
        PromoData(
            title = "Premium Sound",
            subtitle = "Experience deep\nbass anywhere",
            imageRes = R.drawable.img_headphones
        )
    )

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
                    IconButton(onClick = onNavigateToProfile) {
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
            // Search Bar & Filter Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .height(56.dp)
                    .border(1.dp, Color(0xFFF0F0F0), RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF9F9F9)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { onNavigateToSearch() }
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Search for product", color = Color.Gray, fontSize = 14.sp)
                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(56.dp)
                        .background(Color(0xFF7BF179))
                        .clickable { onNavigateToFilter() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Tune, contentDescription = "Filter", tint = Color(0xFF102000))
                }
            }

            // Promotional Carousel
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                pageSpacing = 12.dp
            ) { page ->
                val data = promoItems[page]
                Box(
                    modifier = Modifier
                        .width(361.dp)
                        .height(220.dp)
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(24.dp),
                            ambientColor = Color.Black.copy(alpha = 0.15f),
                            spotColor = Color.Black.copy(alpha = 0.25f)
                        )
                        .clip(RoundedCornerShape(24.dp))
                        .background(data.bgColor)
                ) {
                    // Bloque verde inferior derecho (Cuadrado con sombra)
                    if (data.showGreenBlock) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .width(160.dp)
                                .height(115.dp)
                                .shadow(
                                    elevation = 8.dp,
                                    shape = RectangleShape,
                                    clip = false
                                )
                                .background(color = Color(0xFF7BF179))
                        )
                    }

// Zapatilla
                    Image(
                        painter = painterResource(id = data.imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .offset(
                                x = (-5).dp,
                                y = 10.dp
                            )
                            .size(220.dp),
                        contentScale = ContentScale.Fit
                    )

                    // Columna izquierda: texto + botón + dots
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.58f)
                            .padding(start = 24.dp, top = 24.dp, bottom = 20.dp, end = 8.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = data.title,
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 32.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = data.subtitle,
                                color = Color(0xFFCCCCCC),
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Button(
                                onClick = { onNavigateToProduct("promo_$page") },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF7BF179)
                                ),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp)
                            ) {
                                Text(
                                    "Shop Now",
                                    color = Color(0xFF102000),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                repeat(promoItems.size) { index ->
                                    Box(
                                        modifier = Modifier

                                            .size(8.dp)

                                            .background(

                                                color = if (pagerState.currentPage == index)

                                                    Color.White

                                                else

                                                    Color.Gray.copy(alpha = 0.5f),

                                                shape = CircleShape

                                            )
                                            .background(
                                                color = if (pagerState.currentPage == index)
                                                    Color.White
                                                else
                                                    Color.Gray.copy(alpha = 0.5f),
                                                shape = RoundedCornerShape(50)
                                            )
                                    )
                                }
                            }
                        }
                    }
                }
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
                item { BrandItem("Jordan", R.drawable.logo_nike) }
                item { BrandItem("Adidas", R.drawable.logo_nike) }
            }

            // Recommended For You
            SectionHeader(title = "Recommended For You")
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (viewModel.recommendedProducts.isEmpty()) {
                    item { ProductItem("iPhone 12 Pro...", "₱1,200 × 24 mo", R.drawable.img_iphone) { onNavigateToProduct("iphone") } }
                    item { ProductItem("Headphones", "₱1,200 × 24 mo", R.drawable.img_headphones) { onNavigateToProduct("headphones") } }
                    item { ProductItem("Sneakers", "₱1,200 × 24 mo", R.drawable.img_sneakers) { onNavigateToProduct("sneakers") } }
                } else {
                    items(viewModel.recommendedProducts.size) { index ->
                        val product = viewModel.recommendedProducts[index]
                        ProductItem(product.name, product.price, product.imageResId) { onNavigateToProduct(product.id) }
                    }
                }
            }

            // Best Sellers
            SectionHeader(title = "Best Sellers")
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (viewModel.bestSellers.isEmpty()) {
                    item { ProductItem("Surface Laptop", "₱1,200 × 24 mo", R.drawable.img_iphone) { onNavigateToProduct("surface_laptop") } }
                    item { ProductItem("iPhone 12 Pro...", "₱1,200 × 24 mo", R.drawable.img_iphone) { onNavigateToProduct("iphone") } }
                    item { ProductItem("PS4 Play Stat...", "₱1,200 × 24 mo", R.drawable.img_headphones) { onNavigateToProduct("ps4") } }
                } else {
                    items(viewModel.bestSellers.size) { index ->
                        val product = viewModel.bestSellers[index]
                        ProductItem(product.name, product.price, product.imageResId) { onNavigateToProduct(product.id) }
                    }
                }
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
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp),
                contentScale = ContentScale.Fit
            )
            Text(text = name, fontSize = 12.sp, color = Color.Gray)
        }
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.BottomEnd)
                .padding(end = 8.dp, bottom = 8.dp),
            alpha = 0.5f
        )
    }
}

@Composable
fun ProductItem(name: String, price: String, imageRes: Int, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .clickable(onClick = onClick)
    ) {
        // Imagen con fondo gris claro — sin bloque verde
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF5F5F5)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = price,
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
        )
    }
}

