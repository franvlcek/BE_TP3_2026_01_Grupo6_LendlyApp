package com.example.lendlyapp.pages.verification

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.lendlyapp.R
import com.example.lendlyapp.components.CameraPreview
import com.example.lendlyapp.components.PrimaryButton
import android.Manifest
import android.content.pm.PackageManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaceRecognitionScreen(
    onNavigateBack: () -> Unit,
    onNavigateNext: () -> Unit,
) {
    val context = LocalContext.current
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA,
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Info action */ }) {
                        Icon(Icons.Outlined.Info, contentDescription = "Info", tint = Color.Gray)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Put your face in the\nframe",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = "Follow these instruction, and let us get you onboarded.",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 12.dp, bottom = 32.dp)
            )

            // Camera Frame
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                if (hasCameraPermission) {
                    // Usamos una lógica de fallback por si la frontal no carga
                    CameraPreview(
                        modifier = Modifier.fillMaxSize(),
                        cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
                    )
                } else {
                    Text("Camera permission required", color = Color.White)
                }

                // The "Frame" circle
                Box(
                    modifier = Modifier
                        .size(240.dp)
                        .border(4.dp, Color(0xFF7BF179), CircleShape)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(
                text = "Next",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 16.dp),
                onClick = onNavigateNext
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FaceRecognitionScreenPreview() {
    FaceRecognitionScreen(onNavigateBack = {}, onNavigateNext = {})
}
