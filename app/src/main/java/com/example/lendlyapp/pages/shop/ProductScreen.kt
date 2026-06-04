package com.example.lendlyapp.pages.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.lendlyapp.R

private data class BadgeUi(val icon: String, val text: String)
private data class MerchantUi(val name: String)
private data class FeatureUi(val title: String, val text: String)
private data class SpecUi(val label: String, val value: String)

private data class ProductUi(
    val title: String,
    val subtitle: String,
    val price: String,
    val imageRes: Int? = null,
    val imageUrl: String? = null,
    val locationText: String,
    val badges: List<BadgeUi>,
    val merchants: List<MerchantUi>,
    val features: List<FeatureUi>,
    val specs: List<SpecUi>
)

private val productCatalog = mapOf(
    "iphone" to ProductUi(
        title = "Apple iPhone 12 Pro Max",
        subtitle = "Apple iPhone 12 Pro Max 256GB, Rose Gold",
        price = "₱1,200",
        imageRes = R.drawable.img_iphone,
        locationText = "Davao City, Davao del Sur",
        badges = listOf(
            BadgeUi("☺", "Low interest"),
            BadgeUi("🏷", "0% installment"),
            BadgeUi("📦", "Easy pick-up")
        ),
        merchants = listOf(
            MerchantUi("Power Max Center"),
            MerchantUi("The Loop"),
            MerchantUi("iMac Center")
        ),
        features = listOf(
            FeatureUi("How to Apply For A Loan", "Click on Continue to check if you are qualified."),
            FeatureUi("Disclaimer", "Loan approval and terms are subject to eligibility and review.")
        ),
        specs = listOf(
            SpecUi("Chip", "A14 Bionic chip"),
            SpecUi("Camera", "12 MP camera"),
            SpecUi("Display", "6.1-inch Super Retina XDR display"),
            SpecUi("Storage", "256GB")
        )
    ),
    "headphones" to ProductUi(
        title = "Noise Cancelling Headphones",
        subtitle = "Over-ear wireless headphones",
        price = "₱850",
        imageRes = R.drawable.img_headphones,
        locationText = "Cebu City, Cebu",
        badges = listOf(
            BadgeUi("☺", "Low interest"),
            BadgeUi("🏷", "0% installment"),
            BadgeUi("📦", "Easy pick-up")
        ),
        merchants = listOf(MerchantUi("Audio Hub"), MerchantUi("Sound Wave"), MerchantUi("Tech Corner")),
        features = listOf(
            FeatureUi("How to Apply For A Loan", "Click on Continue to check if you are qualified."),
            FeatureUi("Disclaimer", "Loan approval and terms are subject to eligibility and review.")
        ),
        specs = listOf(
            SpecUi("Type", "Over-ear wireless"),
            SpecUi("Battery", "Up to 30 hours"),
            SpecUi("Noise Cancel", "Active noise cancellation"),
            SpecUi("Connectivity", "Bluetooth 5.0")
        )
    ),
    "sneakers" to ProductUi(
        title = "Running Sneakers",
        subtitle = "Lightweight cushioned sneakers",
        price = "₱990",
        imageRes = R.drawable.img_sneakers,
        locationText = "Davao City, Davao del Sur",
        badges = listOf(
            BadgeUi("☺", "Low interest"),
            BadgeUi("🏷", "0% installment"),
            BadgeUi("📦", "Easy pick-up")
        ),
        merchants = listOf(MerchantUi("Sneaker House"), MerchantUi("Foot Lab"), MerchantUi("Run Store")),
        features = listOf(
            FeatureUi("How to Apply For A Loan", "Click on Continue to check if you are qualified."),
            FeatureUi("Disclaimer", "Loan approval and terms are subject to eligibility and review.")
        ),
        specs = listOf(
            SpecUi("Material", "Breathable mesh"),
            SpecUi("Outsole", "Rubber traction sole"),
            SpecUi("Fit", "Regular fit"),
            SpecUi("Weight", "Lightweight")
        )
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    productId: String = "iphone",
    viewModel: ProductViewModel,
    onNavigateBack: () -> Unit = {},
    onContinue: () -> Unit = {}
) {
    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    val apiProduct = viewModel.product
    val localUi = productCatalog[productId] ?: productCatalog.getValue("iphone")

    // Merged data: Prioritize API data for core fields
    val displayTitle = apiProduct?.name ?: localUi.title
    val displayPrice = if (apiProduct != null) "₱${apiProduct.price}" else localUi.price
    val displayImageRes = apiProduct?.imageResId ?: localUi.imageRes
    val displayImageUrl = apiProduct?.imageUrl

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = displayTitle,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Image(
                            painter = painterResource(id = R.drawable.back_arrow),
                            contentDescription = "Back",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        }
    ) { innerPadding ->
        if (viewModel.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF7BF179))
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF8DF07D))
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    localUi.badges.forEach { badge ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(text = badge.icon, fontSize = 14.sp)
                            Text(text = badge.text, fontSize = 12.sp, color = Color(0xFF102000), fontWeight = FontWeight.Medium)
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(220.dp)
                        .background(Color.White, RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (displayImageUrl != null) {
                        AsyncImage(
                            model = displayImageUrl,
                            contentDescription = "Product image",
                            modifier = Modifier.fillMaxSize().padding(24.dp),
                            contentScale = ContentScale.Fit
                        )
                    } else if (displayImageRes != null) {
                        Image(
                            painter = painterResource(id = displayImageRes),
                            contentDescription = "Product image",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White,
                        tonalElevation = 0.dp,
                        shadowElevation = 1.dp
                    ) {
                        Text(
                            text = "1/4",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontSize = 12.sp,
                            color = Color(0xFF666666)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "From as low as",
                        fontSize = 12.sp,
                        color = Color(0xFF8A8A8A)
                    )
                    Text(
                        text = displayPrice,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = localUi.subtitle,
                        fontSize = 13.sp,
                        color = Color(0xFF222222),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                SectionTitle(title = "WHERE DO YOU WANT TO SHOP?")
                ShopLocationCard(locationText = localUi.locationText)

                SectionTitle(title = "MAKE A DEAL? PARTNER MERCHANTS")
                localUi.merchants.forEach { merchant ->
                    MerchantCard(merchantName = merchant.name)
                }

                SectionTitle(title = "FEATURES")
                localUi.features.forEach { feature ->
                    FeatureItem(title = feature.title, text = feature.text)
                }

                SectionTitle(title = "PRODUCT SPECIFICATIONS")
                localUi.specs.forEach { spec ->
                    SpecItem(label = spec.label, value = spec.value)
                }

                Spacer(modifier = Modifier.height(96.dp))
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(18.dp),
                color = Color.White,
                shadowElevation = 3.dp
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "From as low as", fontSize = 11.sp, color = Color(0xFF8A8A8A))
                        Text(text = displayPrice, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text(text = "per month", fontSize = 11.sp, color = Color(0xFF8A8A8A))
                    }
                    Button(
                        onClick = onContinue,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7BF179)),
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(horizontal = 22.dp, vertical = 14.dp)
                    ) {
                        Text(text = "Continue", color = Color(0xFF102000), fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        fontSize = 11.sp,
        color = Color(0xFF8A8A8A),
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun ShopLocationCard(locationText: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "📍", fontSize = 16.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = locationText, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            }
            Text(text = "⌄", fontSize = 18.sp, color = Color(0xFF777777))
        }
    }
}

@Composable
private fun MerchantCard(
    merchantName: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = merchantName, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text(
                        text = "United Availability",
                        fontSize = 11.sp,
                        color = Color(0xFF7BF179),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                Text(text = "⌄", fontSize = 18.sp, color = Color(0xFF777777))
            }
            Text(
                text = "From ₱1,200 | 12 months\n₱6,000 total repayable",
                fontSize = 11.sp,
                color = Color(0xFF666666),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun FeatureItem(
    title: String,
    text: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Text(text = text, fontSize = 11.sp, color = Color(0xFF666666), modifier = Modifier.padding(top = 6.dp))
        }
    }
}

@Composable
private fun SpecItem(
    label: String,
    value: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, fontSize = 12.sp, color = Color(0xFF777777), modifier = Modifier.weight(0.35f))
            Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(0.65f))
        }
    }
}
