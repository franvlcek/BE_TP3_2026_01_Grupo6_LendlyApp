package com.example.lendlyapp.pages.verification

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.lendlyapp.components.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignatureScreen(
    onNavigateBack: () -> Unit,
    onNavigateNext: () -> Unit,
) {
    // Guardamos una lista de puntos históricos. Usamos Offset.Unspecified para saber cuándo levantó el dedo
    val points = remember { mutableStateListOf<Offset>() }

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
                text = "Let's seal the deal!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = "You can use your finger or a compatible stylus to write you signature",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 12.dp, bottom = 32.dp)
            )

            // Area de Firma Funcional Continua
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color(0xFFF9F9F9), RoundedCornerShape(12.dp))
                    .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { startOffset ->
                                points.add(startOffset)
                            },
                            onDrag = { change, _ ->
                                change.consume()
                                points.add(change.position) // Registramos cada punto del camino
                            },
                            onDragEnd = {
                                points.add(Offset.Unspecified) // Separador para saber que levantó el dedo
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    if (points.isNotEmpty()) {
                        val signaturePath = Path().apply {
                            var isFirst = true
                            points.forEach { point ->
                                if (point == Offset.Unspecified) {
                                    isFirst = true // El siguiente punto iniciará un trazo nuevo libre
                                } else {
                                    if (isFirst) {
                                        moveTo(point.x, point.y)
                                        isFirst = false
                                    } else {
                                        lineTo(point.x, point.y)
                                    }
                                }
                            }
                        }

                        drawPath(
                            path = signaturePath,
                            color = Color.Black,
                            style = Stroke(width = 6f, cap = StrokeCap.Round)
                        )
                    }
                }

                if (points.isEmpty()) {
                    Text(
                        text = "Sign here\n(same signature as with the\ndocument you provided)",
                        color = Color.LightGray,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    )
                }

                Text(
                    text = "X___",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            // Botón para limpiar el lienzo
            if (points.isNotEmpty()) {
                TextButton(
                    onClick = {
                        points.clear() // Vaciamos los trazos viejos y el motor queda listo de nuevo
                    },
                    modifier = Modifier.align(Alignment.End).padding(top = 8.dp)
                ) {
                    Text("Clear Signature", color = Color.Red)
                }
            } else {
                Spacer(modifier = Modifier.height(48.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "By tapping \"Next\", you confirm that the information you provided is true and correct.",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth()
            )

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
fun SignatureScreenPreview() {
    SignatureScreen(onNavigateBack = {}, onNavigateNext = {})
}
