package com.example.lendlyapp.pages.manage

import android.widget.Space
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.R
import com.example.lendlyapp.components.Divider
import com.example.lendlyapp.data.session.SessionManager
import com.example.lendlyapp.ui.theme.interFontsRegular
import com.example.lendlyapp.ui.theme.interFontsSemiBold
import com.example.lendlyapp.ui.theme.montserratFontsSemiBold

@Composable
fun CreditScoreScreen(
    viewModel: CreditScoreViewModel,
    onBack: () -> Unit
){
    val profile = viewModel.userProfile
    val score = profile?.creditScore ?: 720
    val level = profile?.creditLevel ?: "Good"

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Image(
                    painter = painterResource(R.drawable.back_arrow),
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Text(
            text = "Credit Score",
            fontFamily = montserratFontsSemiBold,
            fontSize = 28.sp,
            modifier = Modifier.padding(start = 16.dp),
            textAlign = TextAlign.Start
        )
        
        if (viewModel.isLoading) {
            Box(Modifier.fillMaxWidth().height(475.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF7BF179))
            }
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth().padding(top = 32.dp).height(475.dp)
                    .background(Color(0xffFCF8F8), shape = RoundedCornerShape(16.dp)),
            ) {
                CreditScoreWidget(score)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = score.toString(),
                        fontSize = 45.sp,
                        fontFamily = montserratFontsSemiBold,
                        modifier = Modifier.padding(top = 250.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color(0xff6A6C6A))) {
                                append("Your Score is ")
                            }
                            withStyle(style = SpanStyle()) {
                                append(level)
                            }
                        },
                        fontSize = 22.sp,
                        fontFamily = interFontsSemiBold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color(0xFFE5E2E1),
                        modifier = Modifier.padding(start = 16.dp, top = 32.dp, end = 16.dp)
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "What is Credit Score?",
                            textAlign = TextAlign.Start,
                            fontSize = 14.sp,
                            fontFamily = interFontsSemiBold,
                            modifier = Modifier.padding(start = 16.dp),
                            color = Color(0xff6A6C6A)
                        )
                    }
                    Text(
                        text = "This is your trust score, used as a bases to determine the various activities you do on Credit Score.",
                        fontSize = 12.sp,
                        fontFamily = interFontsRegular,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                        color = Color(0xff6A6C6A)
                    )
                }
            }
        }
        
        Divider("General")
        LazyColumn(modifier = Modifier.weight(1f)) {
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
                DataCardItem("Settings", R.drawable.gear)
            }
        }
    }
}
@Composable
fun CreditScoreWidget(score: Int){
    val progress = (score / 850f).coerceIn(0f, 1f)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(282.dp)
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawArc(
                color = Color.LightGray,
                startAngle = -180f,
                sweepAngle = 180f,
                useCenter = false,
                style = Stroke(
                    width = 10.dp.toPx(),
                    cap = StrokeCap.Round
                )
            )
            drawArc(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        Color.Red,
                        Color.Red,
                        Color(0xFFFF9800),
                        Color.Green
                    )
                ),
                startAngle = -180f,
                sweepAngle = 180f * progress,
                useCenter = false,
                style = Stroke(
                    width = 10.dp.toPx(),
                    cap = StrokeCap.Round
                )
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 44.dp),
        ) {
            Text(
                text = "300",
                fontSize = 22.sp,
                fontFamily = interFontsSemiBold,
                color = Color(0xff6A6C6A)
            )
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "850",
                    fontSize = 22.sp,
                    fontFamily = interFontsSemiBold,
                    color = Color(0xff6A6C6A)
                )
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun CreditScoreScreenPreview() {
    CreditScoreScreen()
}
*/