package com.example.lendlyapp.pages.manage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.lendlyapp.R
import com.example.lendlyapp.components.Divider
import com.example.lendlyapp.components.TopBar
import com.example.lendlyapp.ui.theme.interFontsRegular
import com.example.lendlyapp.ui.theme.interFontsSemiBold
import com.example.lendlyapp.ui.theme.montserratFontsSemiBold

@Composable
fun ManageScreen(
    viewModel: ManageViewModel,
    onLogout: () -> Unit,
    onEditProfile: () -> Unit,
    onCreditScore: () -> Unit,
    onNotifications: () -> Unit = {}
){
    val profile = viewModel.userProfile

    LaunchedEffect(Unit) {
        viewModel.loadUserProfile()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        TopBar(
            onProfileClick = onEditProfile,
            onNotificationsClick = onNotifications
        )
        Spacer(modifier = Modifier.padding(top = 16.dp))
        
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ){
            Text(
                text = "Manage",
                fontFamily = montserratFontsSemiBold,
                fontSize = 28.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
            
            Divider("Currently using as")
            
            Spacer(modifier = Modifier.padding(top = 16.dp))
            
            UserDataCard(
                title = profile?.fullName ?: "Loading...",
                description = profile?.email ?: "account@example.com", 
                avatarUrl = profile?.avatar,
                onClick = onEditProfile
            )
            
            Divider("General")

            LazyColumn(modifier = Modifier.weight(1f)) {
                item {
                    Box(modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)){
                        DataCardItem("Account Details", R.drawable.account_details)
                    }
                }
                item {
                    Box(modifier = Modifier.padding(bottom = 16.dp)) {
                        DataCardItem("Receiving by email of phone", R.drawable.mailbox)
                    }
                }
                item {
                    Box(modifier = Modifier.padding(bottom = 16.dp)) {
                        DataCardItem("Scheduled pay", R.drawable.calendar)
                    }
                }
                item {
                    Box(
                        modifier = Modifier.padding(bottom = 16.dp)
                            .fillMaxWidth()
                            .clickable { onCreditScore() }
                    ){
                        DataCardItem("Credit Score", R.drawable.score)
                    }
                }
                item {
                    Box(modifier = Modifier.padding(bottom = 16.dp)) {
                        DataCardItem("Settings", R.drawable.gear)
                    }
                }
                item {
                    Box(modifier = Modifier.padding(bottom = 16.dp)) {
                        DataCardItem("Terms and Conditions", R.drawable.document)
                    }
                }
                item {
                    DataCardItem("Help", R.drawable.help)
                }
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFF6A6C6A),
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.logout(onLogout)
                    }
            ) {
                DataCardItem("Log Out", R.drawable.logout)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun UserDataCard(title: String, description: String, avatarUrl: String? = null, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .padding(start = 16.dp)
                .size(40.dp),
            shape = androidx.compose.foundation.shape.CircleShape,
            color = Color(0xFFF5F5F5)
        ) {
            if (!avatarUrl.isNullOrBlank()) {
                AsyncImage(
                    model = avatarUrl,
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = title.take(2).uppercase(),
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text= title,
                fontFamily = interFontsRegular,
                fontSize = 16.sp
            )
            Text(
                text= description,
                fontFamily = interFontsRegular,
                fontSize = 14.sp,
                color = Color(0xff6A6C6A)
            )
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = onClick, 
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7BF179),
                contentColor = Color(0xFF102000),
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(end = 20.dp)
        ) {
            Text(
                text="Edit",
                fontFamily = interFontsSemiBold,
                fontSize = 14.sp,
            )
        }
    }
}

@Composable
fun DataCardItem(text: String, id: Int){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(id),
            contentDescription = null,
            modifier = Modifier
                .size(width = 40.dp, height = 40.dp)
                .padding(start = 16.dp)
        )
        Text(
            text= text,
            modifier = Modifier.padding(start = 16.dp),
            fontFamily = interFontsRegular,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = ">",
            modifier = Modifier.padding(end = 20.dp),
            color = Color.Gray
        )
    }
}
