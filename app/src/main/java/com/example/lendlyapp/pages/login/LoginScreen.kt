package com.example.lendlyapp.pages.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.R
import com.example.lendlyapp.components.PrimaryButton
import com.example.lendlyapp.ui.theme.interFontsSemiBold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    onNavigateToHome: () -> Unit,
) {
    var passwordVisible by remember { mutableStateOf(value = false) }

    // Inicializamos el email por defecto ya que en este diseño el usuario ya está "seleccionado" (John Doe)
    LaunchedEffect(Unit) {
        if (viewModel.email.isBlank()) {
            viewModel.onEmailChanged("john.doe@example.com")
        }
    }

    LaunchedEffect(viewModel.loginSuccess) {
        if (viewModel.loginSuccess) {
            onNavigateToHome()
        }
    }

    Scaffold(
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // "Login Page" label at the top (like in the photo)
            Text(
                text = "Login Page",
                fontSize = 14.sp,
                color = Color.LightGray,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 16.dp)
            )

            // Imagen del Splash (Logo)
            Image(
                painter = painterResource(id = R.drawable.frame_134),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(240.dp)
                    .padding(top = 40.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Perfil de usuario (John Doe)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = androidx.compose.foundation.shape.CircleShape,
                    color = Color(0xFFF5F5F5)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "JD",
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "John Doe",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = interFontsSemiBold
                    )
                    Text(
                        text = "+63-923456790",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                Text(
                    text = "Change",
                    color = Color(0xFF4C662B),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Etiqueta de Password
            Text(
                text = "Password",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 8.dp)
            )

            // Campo de Contraseña
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                placeholder = { Text("123123123") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                enabled = !viewModel.isLoading,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = Color(0xFF4C662B)
                )
            )

            // Olvidaste tu contraseña?
            Text(
                text = "Forgot your password?",
                color = Color(0xFF4C662B),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 12.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Cartel de Error
            viewModel.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Botón de Ingreso usando PrimaryButton
            if (viewModel.isLoading) {
                CircularProgressIndicator(color = Color(0xFF7BF179))
            } else {
                PrimaryButton(
                    text = "Log In",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(bottom = 16.dp)
                ) {
                    viewModel.onLoginClicked()
                }
            }
        }
    }
}
