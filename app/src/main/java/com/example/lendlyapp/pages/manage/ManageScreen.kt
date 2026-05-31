package com.example.lendlyapp.pages.manage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.R
import com.example.lendlyapp.components.AccountBalanceCard
import com.example.lendlyapp.components.Divider
import com.example.lendlyapp.components.TopBar
import com.example.lendlyapp.data.session.SessionManager
import com.example.lendlyapp.pages.home.BankItemData
import com.example.lendlyapp.pages.home.BankRow
import com.example.lendlyapp.pages.onboarding.OnboardingPage
import com.example.lendlyapp.pages.onboarding.OnboardingScreen
import com.example.lendlyapp.ui.theme.interFontsMedium
import com.example.lendlyapp.ui.theme.interFontsRegular
import com.example.lendlyapp.ui.theme.interFontsSemiBold
import com.example.lendlyapp.ui.theme.montserratFontsSemiBold

@Composable
fun ManageScreen(
    sessionManager: SessionManager,
    onLogout: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        TopBar()
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
            Divider("General")

            LazyColumn{
                items(1){ card ->
                    DataCardItem("Account Details", R.drawable.account_details)
                }
                items(1){ card ->
                    DataCardItem("Receiving by email of phone", R.drawable.mailbox)
                }
                items(1){ card ->
                    DataCardItem("Scheduled pay", R.drawable.calendar)
                }
                items(1){ card ->
                    DataCardItem("Credit Score", R.drawable.score)
                }
                items(1){ card ->
                    DataCardItem("Settings", R.drawable.gear)
                }
                items(1){ card ->
                    DataCardItem("Terms and Conditions", R.drawable.document)
                }
                items(1){ card ->
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
                        sessionManager.clearSession()
                        onLogout()
                    }
            ) {
                DataCardItem("Log Out", R.drawable.logout)
            }
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
            contentDescription = "ManageScreen",
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
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(end = 20.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = ">"
            )
        }

    }
}

/*
@Preview(showBackground = true)
@Composable
fun ManageScreenPreview() {
    ManageScreen()
}*/