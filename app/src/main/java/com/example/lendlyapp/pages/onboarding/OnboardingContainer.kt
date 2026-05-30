package com.example.lendlyapp.pages.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.R
import com.example.lendlyapp.components.PrimaryButton
import com.example.lendlyapp.components.SecondaryButton
import com.example.lendlyapp.ui.theme.interFontsSemiBold
import kotlinx.coroutines.launch

@Composable
fun OnboardingContainer(
    onFinish: ()-> Unit
){
    val pages = listOf(
        OnboardingPage(
            title = "QUICK LOANS",
            description = "Trusted for easy, \n" +
                    "fast loan approvals.",
            img = R.drawable.onboarding_one
        ),
        OnboardingPage(
            title = "LOAN PRODUCT \n"+ "IN-APP",
            description = "Many products to loan.",
            img = R.drawable.onboarding_two
        ),
        OnboardingPage(
            title = "TRACK & PAY \n"+"EASILY",
            description = "",
            img = R.drawable.onboarding_three
        )
    )

    val pagerState = rememberPagerState(
        pageCount = { pages.size }
    )
    val scope = rememberCoroutineScope()
    val isLastPage = pagerState.currentPage == pages.lastIndex
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF102000)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->

            OnboardingScreen(
                page = pages[page]
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            repeat(pages.size) { index ->

                val color =
                    if (pagerState.currentPage == index)
                        Color(0xFF7BF179)
                    else
                        Color(0xFFEADDFF).copy(alpha = 0.1f)

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(10.dp)
                        .background(
                            color,
                            CircleShape
                        )
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if (isLastPage){
                SecondaryButton(
                    text = "Log In",
                    onClick = {
                        scope.launch {
                            onFinish()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
                )
                Spacer(modifier = Modifier.size(12.dp))
            }
            PrimaryButton(
                text = if (pagerState.currentPage == pages.lastIndex) "Sign up for free" else "Get Started",
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage == pages.lastIndex) {
                            onFinish()
                        } else {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(bottom = 24.dp)
            )
        }
    }
}
/*
@Preview(showBackground = true)
@Composable
fun OnboardingContainerPreview(){
    OnboardingContainer()
}*/