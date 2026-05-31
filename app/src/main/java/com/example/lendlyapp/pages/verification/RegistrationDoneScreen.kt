package com.example.lendlyapp.pages.verification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.lendlyapp.R
import com.example.lendlyapp.components.PrimaryButton

@Composable
fun RegistrationDoneScreen(
    onDone: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A2000)) // Dark green background
    ) {
        // Close button at top left
        IconButton(
            onClick = onDone,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
                .background(Color(0x22FFFFFF), CircleShape)
        ) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
        }

        // Logo/Image at top center (aligned with the close button)
        Image(
            painter = painterResource(id = R.drawable.frame_134),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 24.dp)
                .size(60.dp)
                .align(Alignment.TopCenter)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Large Checkmark Image
            Image(
                painter = painterResource(id = R.drawable.ic_check_done),
                contentDescription = null,
                modifier = Modifier.size(240.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "ALL DONE!",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "You're ready to start a loan.",
                color = Color.LightGray,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }

        // Bottom Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(24.dp)
        ) {
            PrimaryButton(
                text = "Done",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                onClick = onDone
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationDoneScreenPreview() {
    RegistrationDoneScreen(onDone = {})
}

