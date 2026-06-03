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
    viewModel: ProfileDetailViewModel,
    onNavigateBack: () -> Unit,
    onNavigateNext: () -> Unit
) {
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
                value = viewModel.firstName, 
                onValueChange = viewModel::onFirstNameChanged,
                placeholder = "John D.",
                isError = viewModel.firstNameError != null,
                supportingText = viewModel.firstNameError
            )
            DetailField(
                label = "Full legal last name", 
                value = viewModel.lastName, 
                onValueChange = viewModel::onLastNameChanged,
                placeholder = "Doe",
                isError = viewModel.lastNameError != null,
                supportingText = viewModel.lastNameError
            )

            Text("Date of birth", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = viewModel.day, 
                    onValueChange = viewModel::onDayChanged,
                    label = { Text("Day") }, 
                    placeholder = { Text("08", color = Color.LightGray) },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray),
                    isError = viewModel.dateError != null
                )
                OutlinedTextField(
                    value = viewModel.month, 
                    onValueChange = viewModel::onMonthChanged,
                    label = { Text("Month") }, 
                    placeholder = { Text("12", color = Color.LightGray) },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray),
                    isError = viewModel.dateError != null
                )
                OutlinedTextField(
                    value = viewModel.year, 
                    onValueChange = viewModel::onYearChanged,
                    label = { Text("Year") }, 
                    placeholder = { Text("1997", color = Color.LightGray) },
                    modifier = Modifier.weight(1.5f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray),
                    isError = viewModel.dateError != null
                )
            }
            if (viewModel.dateError != null) {
                Text(text = viewModel.dateError!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            DetailField(
                label = "Address", 
                value = viewModel.address, 
                onValueChange = viewModel::onAddressChanged,
                placeholder = "Somewhere IN BLOCK 12",
                isError = viewModel.addressError != null,
                supportingText = viewModel.addressError
            )
            DetailField(
                label = "City", 
                value = viewModel.city, 
                onValueChange = viewModel::onCityChanged,
                placeholder = "Davao City",
                isError = viewModel.cityError != null,
                supportingText = viewModel.cityError
            )
            DetailField(
                label = "Postal Code", 
                value = viewModel.postalCode, 
                onValueChange = viewModel::onPostalCodeChanged, 
                keyboardType = KeyboardType.Number,
                placeholder = "8000",
                isError = viewModel.postalCodeError != null,
                supportingText = viewModel.postalCodeError
            )
            
            Text("Phone Number", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = "+65", onValueChange = {}, enabled = false,
                    modifier = Modifier.width(70.dp), shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(disabledBorderColor = Color.LightGray)
                )
                OutlinedTextField(
                    value = viewModel.phone, 
                    onValueChange = viewModel::onPhoneChanged,
                    placeholder = { Text("991251255", color = Color.LightGray) }, 
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray),
                    isError = viewModel.phoneError != null
                )
            }
            if (viewModel.phoneError != null) {
                Text(text = viewModel.phoneError!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))

            PrimaryButton(
                text = "Next",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 16.dp),
                onClick = {
                    viewModel.validate(onSuccess = onNavigateNext)
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
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
    supportingText: String? = null
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
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray),
            isError = isError,
            supportingText = {
                if (supportingText != null) {
                    Text(text = supportingText, color = MaterialTheme.colorScheme.error)
                }
            }
        )
    }
}
