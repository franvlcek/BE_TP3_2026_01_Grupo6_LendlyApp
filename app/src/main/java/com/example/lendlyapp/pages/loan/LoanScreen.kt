package com.example.lendlyapp.pages.loan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.R
import com.example.lendlyapp.ui.theme.interFontsSemiBold
import com.example.lendlyapp.ui.theme.interFontsRegular

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanScreen(
    onNavigateToForm: () -> Unit,
    onNavigateToProfile: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {}
) {
    val montserratSemiBold = FontFamily(Font(R.font.montserrat_extra_bold, FontWeight.SemiBold))

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
                    IconButton(onClick = onNavigateToNotifications) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            modifier = Modifier.size(32.dp),
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp)
        ) {
            // 1. Promo Card
            item {
                LoanPromoCard(montserratSemiBold)
            }

            // 2. Loan Limit Card
            item {
                Spacer(modifier = Modifier.height(16.dp))
                LoanLimitCard(montserratSemiBold)
            }

            // 3. How it works section
            item {
                Spacer(modifier = Modifier.height(24.dp))
                HowItWorksSection()
            }

            // 4. CTA Button
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onNavigateToForm,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7BF179)),
                    shape = RoundedCornerShape(100.dp)
                ) {
                    Text(
                        text = "Get This Loan",
                        style = TextStyle(
                            fontFamily = interFontsSemiBold,
                            fontWeight = FontWeight.W600,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            letterSpacing = 0.1.sp,
                            textAlign = TextAlign.Center,
                            color = Color(0xFF102000)
                        )
                    )
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun LoanPromoCard(montserratSemiBold: FontFamily) {
    Box(
        modifier = Modifier
            .width(361.dp)
            .height(196.dp)
            .clip(RoundedCornerShape(28.dp)) // Corner/Large
            .background(Color(0xFF7BF179))
    ) {
        // Imagen de la chica con Zoom (ContentScale.Crop para que no se vea el cuerpo completo)
        Image(
            painter = painterResource(id = R.drawable.chica_con_celular),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .width(220.dp), // Ajustamos ancho para controlar el "zoom"
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            // Suggestion chip / Limited Time Offer
            Surface(
                color = Color(0xFFE5F5EA), // Fondo claro según tus nuevas specs
                shape = RoundedCornerShape(8.dp), // Corner/Small
                modifier = Modifier
                    .width(166.dp)
                    .height(32.dp)
            ) {
                Row(
                    modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Contenedor del icono reloj
                    Box(
                        modifier = Modifier
                            .size(width = 18.dp, height = 18.dp)
                            .background(Color(0xFFD9D9D9), shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.alarm),
                            contentDescription = null,
                            modifier = Modifier.size(width = 15.98.dp, height = 14.74.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Limited Time Offer",
                        color = Color(0xFF102000), // Texto oscuro sobre fondo claro
                        fontSize = 12.sp,
                        fontFamily = interFontsRegular,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Texto Safe and secure loans
            Text(
                text = "Safe and\nsecure loans",
                style = TextStyle(
                    fontFamily = montserratSemiBold,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 28.sp,
                    lineHeight = 36.sp,
                    color = Color(0xFF102000)
                ),
                modifier = Modifier.width(188.dp)
            )
            
            Text(
                text = "All here in Rayland",
                style = TextStyle(
                    fontFamily = interFontsRegular,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = Color(0xFF102000).copy(alpha = 0.8f)
                )
            )
        }
    }
}

@Composable
fun LoanLimitCard(montserratSemiBold: FontFamily) {
    Card(
        modifier = Modifier
            .width(361.dp)
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFCF8F8)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "You can borrow up to",
                    fontFamily = interFontsSemiBold,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    color = Color.Black
                )
                Text(
                    text = "₱ 30,000.00",
                    fontFamily = montserratSemiBold,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F3701)
                )
                Text(
                    text = "*Subject to evaluation",
                    fontFamily = interFontsRegular,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF6A6C6A)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Loan Details",
                    fontFamily = interFontsSemiBold,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F3701)
                )
                Text(
                    text = "What is this?",
                    fontFamily = interFontsSemiBold,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4C662B),
                    textDecoration = TextDecoration.Underline
                )
            }

            HorizontalDivider(color = Color(0xFFE5E2E1), thickness = 1.dp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LoanStatItem("Payable in", "6 - 12", "months", montserratSemiBold)
                LoanStatItem("Interest Rate", "1.99%", "ave per mo.", montserratSemiBold)
                LoanStatItem("Process Fee", "3%", "as low as", montserratSemiBold)
            }
        }
    }
}

@Composable
fun LoanStatItem(label: String, value: String, subLabel: String, montserratSemiBold: FontFamily) {
    Column(
        modifier = Modifier.width(87.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = label, fontSize = 12.sp, fontFamily = interFontsRegular, fontWeight = FontWeight.Medium, color = Color.Black)
        Text(text = value, fontSize = 28.sp, fontFamily = montserratSemiBold, fontWeight = FontWeight.SemiBold, color = Color(0xFF1F3701))
        Text(text = subLabel, fontSize = 11.sp, fontFamily = interFontsRegular, fontWeight = FontWeight.Medium, color = Color(0xFF6A6C6A))
    }
}

@Composable
fun HowItWorksSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "How it works",
            fontFamily = interFontsSemiBold,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF171D1E)
        )

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                // Card 1: Keep your credit score high
                HowItWorksCard(
                    title = "Keep your credit score high",
                    description = "The offered loan amount is based on your credit score.",
                    modifier = Modifier.weight(1f),
                    content = {
                        Image(
                            painter = painterResource(id = R.drawable.avatar_con_tarjeta),
                            contentDescription = null,
                            modifier = Modifier.size(width = 146.5.dp, height = 104.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                )
                // Card 2: Get instant approval
                HowItWorksCard(
                    title = "Get instant approval",
                    description = "Everything we need to process si already in the application",
                    modifier = Modifier.weight(1f),
                    content = {
                        Image(
                            painter = painterResource(id = R.drawable.avatar_segundo_bloque),
                            contentDescription = null,
                            modifier = Modifier.size(width = 146.5.dp, height = 104.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                // Card 3: Easy payments option available
                HowItWorksCard(
                    title = "Easy payments option available",
                    description = "Skip the queue and pay your due on the application",
                    modifier = Modifier.weight(1f),
                    content = {
                        Image(
                            painter = painterResource(id = R.drawable.avatar_tercer_bloque_con_tarjeta),
                            contentDescription = null,
                            modifier = Modifier.size(width = 146.5.dp, height = 104.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                )
                // Card 4: Safe and secure
                HowItWorksCard(
                    title = "Safe and secure",
                    description = "Rayland is working with trusted partners to provide this services",
                    modifier = Modifier.weight(1f),
                    content = {
                        Image(
                            painter = painterResource(id = R.drawable.avatar__tildegrande_mas_avatar),
                            contentDescription = null,
                            modifier = Modifier.size(width = 146.5.dp, height = 80.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun HowItWorksCard(title: String, description: String, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Card(
        modifier = modifier
            .height(260.dp)
            .border(1.dp, Color(0xFFEAEAEA), RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier.height(104.dp), contentAlignment = Alignment.Center) {
                content()
            }
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = interFontsSemiBold,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    letterSpacing = 0.15.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                ),
                modifier = Modifier.height(48.dp)
            )
            Text(
                text = description,
                style = TextStyle(
                    fontFamily = interFontsRegular,
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 0.4.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF454745)
                ),
                modifier = Modifier.height(48.dp)
            )
        }
    }
}
