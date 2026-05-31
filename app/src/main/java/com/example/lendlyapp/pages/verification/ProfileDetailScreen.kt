package com.example.lendlyapp.pages.verification

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.lendlyapp.components.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDetailScreen(
    onNavigateBack: () -> Unit,
    onNavigateNext: () -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var day by remember { mutableStateOf("") }
    var month by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    
    var showError by remember { mutableStateOf(false) }

    val isFormValid = firstName.isNotBlank() && 
                      lastName.isNotBlank() && 
                      day.isNotBlank() && 
                      month.isNotBlank() && 
                      year.isNotBlank() && 
                      address.isNotBlank() && 
                      city.isNotBlank() && 
                      postalCode.isNotBlank() && 
                      phone.isNotBlank()

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
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Enter your personal\ndetails",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            DetailField(
                label = "Full legal first and middle name(s)", 
                value = firstName, 
                onValueChange = { firstName = it; showError = false },
                placeholder = "John D."
            )
            DetailField(
                label = "Full legal last name", 
                value = lastName, 
                onValueChange = { lastName = it; showError = false },
                placeholder = "Doe"
            )

            Text("Date of birth", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = day, 
                    onValueChange = { if(it.length <= 2) { day = it; showError = false } },
                    label = { Text("Day") }, 
                    placeholder = { Text("08", color = Color.LightGray) },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray)
                )
                OutlinedTextField(
                    value = month, 
                    onValueChange = { if(it.length <= 2) { month = it; showError = false } },
                    label = { Text("Month") }, 
                    placeholder = { Text("12", color = Color.LightGray) },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray)
                )
                OutlinedTextField(
                    value = year, 
                    onValueChange = { if(it.length <= 4) { year = it; showError = false } },
                    label = { Text("Year") }, 
                    placeholder = { Text("1997", color = Color.LightGray) },
                    modifier = Modifier.weight(1.5f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            DetailField(
                label = "Address", 
                value = address, 
                onValueChange = { address = it; showError = false },
                placeholder = "Somewhere IN BLOCK 12"
            )
            DetailField(
                label = "City", 
                value = city, 
                onValueChange = { city = it; showError = false },
                placeholder = "Davao City"
            )
            DetailField(
                label = "Postal Code", 
                value = postalCode, 
                onValueChange = { postalCode = it; showError = false }, 
                keyboardType = KeyboardType.Number,
                placeholder = "8000"
            )
            
            Text("Phone Number", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = "+65", onValueChange = {}, enabled = false,
                    modifier = Modifier.width(70.dp), shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(disabledBorderColor = Color.LightGray)
                )
                OutlinedTextField(
                    value = phone, 
                    onValueChange = { phone = it; showError = false },
                    placeholder = { Text("991251255", color = Color.LightGray) }, 
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray)
                )
            }

            if (showError) {
                Text(
                    text = "Please fill in all fields to continue",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 16.dp).align(Alignment.CenterHorizontally)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            PrimaryButton(
                text = "Next",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 16.dp),
                onClick = {
                    if (isFormValid) {
                        onNavigateNext()
                    } else {
                        showError = true
                    }
                }
            )
        }
    }
}

@Composable
fun DetailField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(text = label, fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.LightGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileDetailScreenPreview() {
    ProfileDetailScreen(onNavigateBack = {}, onNavigateNext = {})
}

