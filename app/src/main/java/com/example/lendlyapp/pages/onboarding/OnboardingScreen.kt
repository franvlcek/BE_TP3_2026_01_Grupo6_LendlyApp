package com.example.lendlyapp.pages.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.R
import com.example.lendlyapp.ui.theme.interFontsRegular
import com.example.lendlyapp.ui.theme.interFontsSemiBold
import com.example.lendlyapp.ui.theme.montserratFontsExtraBold

@Composable
fun OnboardingScreen(

    page: OnboardingPage
) {

    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF102000)),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
            Spacer(modifier = Modifier.height(40.dp))
            Image(
                painter = painterResource(R.drawable.frame_134),
                contentDescription = "SplashScreen",
                modifier = Modifier.size(width = 116.52.dp, height = 40.dp)
            )

            Image(
                painter = painterResource(page.img),
                contentDescription = "rectangle",

                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(433.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text=page.title,
                fontFamily = montserratFontsExtraBold,
                fontSize = 32.sp,
                color = Color(0xFFB1D18A),
                textAlign = TextAlign.Center,
                letterSpacing = 0.sp,
                lineHeight = 40.sp

            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text=page.description,
                fontFamily = interFontsRegular,
                fontSize = 22.sp,
                color = Color(0xFFE5F5EA),
                letterSpacing = 0.sp,
                lineHeight = 28.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(
        page = OnboardingPage(
        title = "Test",
        description = "Test description",
        img = R.drawable.frame_134
        )
    )
}