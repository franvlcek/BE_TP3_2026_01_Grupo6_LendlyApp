package com.example.lendlyapp.pages.manage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.R
import com.example.lendlyapp.components.PrimaryButton
import com.example.lendlyapp.ui.theme.interFontsMedium
import com.example.lendlyapp.ui.theme.montserratFontsSemiBold

@Composable
fun ProfileDetailScreen(
    viewModel: ManageProfileViewModel,
    onBack: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onBack()
                    }
            ){
                Image(
                    painter = painterResource(R.drawable.back_arrow),
                    contentDescription = "Back",
                    modifier = Modifier.size(width = 40.dp, height = 40.dp).padding(start = 8.dp)
                )
            }
        }
        Text(
            text = "Edit Profile",
            fontFamily = montserratFontsSemiBold,
            fontSize = 28.sp,
            modifier = Modifier.padding(start = 16.dp).fillMaxWidth()
        )
        Text(
            text = "Full legal first and middle name(s)",
            fontFamily = interFontsMedium,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp).fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = Color(0xff454745)
        )
        OutlinedTextField(
            value = viewModel.firstName,
            onValueChange = { viewModel.firstName = it},
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp).fillMaxWidth(),
            placeholder = {Text("John D.") },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6A6C6A),
                unfocusedBorderColor = Color(0xFF6A6C6A)
            )
        )
        Text(
            text = "Full legal last name",
            fontFamily = interFontsMedium,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp).fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = Color(0xff454745)
        )
        OutlinedTextField(
            value = viewModel.lastName,
            onValueChange = { viewModel.lastName = it},
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp).fillMaxWidth(),
            placeholder = {Text("Doe") },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6A6C6A),
                unfocusedBorderColor = Color(0xFF6A6C6A)
            )
        )
        Text(
            text = "Date of Birth",
            fontFamily = interFontsMedium,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp).fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = Color(0xff454745)
        )
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(modifier = Modifier.weight(1f)){
                Text(
                    text = "Day",
                    fontFamily = interFontsMedium,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color(0xff6A6C6A)
                )

                OutlinedTextField(
                    value = viewModel.day,
                    onValueChange = { if (it.all { c -> c.isDigit() } && it.length <= 2) viewModel.day = it},
                    placeholder = { Text("08")},
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF6A6C6A),
                        unfocusedBorderColor = Color(0xFF6A6C6A)
                    )
                )
            }
            Column(modifier = Modifier.weight(1f)){
                Text(
                    text = "Month",
                    fontFamily = interFontsMedium,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color(0xff6A6C6A)
                )

                OutlinedTextField(
                    value = viewModel.month,
                    onValueChange = { if (it.all { c -> c.isDigit() } && it.length <= 2) viewModel.month = it},
                    placeholder = { Text("12")},
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF6A6C6A),
                        unfocusedBorderColor = Color(0xFF6A6C6A)
                    )
                )
            }
            Column(modifier = Modifier.weight(1.5f)){
                Text(
                    text = "Year",
                    fontFamily = interFontsMedium,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color(0xff6A6C6A)
                )

                OutlinedTextField(
                    value = viewModel.year,
                    onValueChange = { if (it.all { c -> c.isDigit() } && it.length <= 4) viewModel.year = it},
                    placeholder = {Text("1997") },
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF6A6C6A),
                        unfocusedBorderColor = Color(0xFF6A6C6A)
                    )
                )
            }
        }
        Text(
            text = "Address",
            fontFamily = interFontsMedium,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp).fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = Color(0xFF6A6C6A)
        )
        OutlinedTextField(
            value = viewModel.address,
            onValueChange = { viewModel.address = it},
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp).fillMaxWidth(),
            placeholder = {Text("Somewhere IN BLOCK 12") },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6A6C6A),
                unfocusedBorderColor = Color(0xFF6A6C6A)
            )
        )
        Text(
            text = "City",
            fontFamily = interFontsMedium,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp).fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = Color(0xFF6A6C6A)
        )
        OutlinedTextField(
            value = viewModel.city,
            onValueChange = { viewModel.city = it},
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp).fillMaxWidth(),
            placeholder = {Text("Davao City") },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6A6C6A),
                unfocusedBorderColor = Color(0xFF6A6C6A)
            )
        )
        Text(
            text = "Postal Code",
            fontFamily = interFontsMedium,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp).fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = Color(0xFF6A6C6A)
        )
        OutlinedTextField(
            value = viewModel.postalCode,
            onValueChange = { if (it.all { c -> c.isDigit() }) viewModel.postalCode = it},
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp).fillMaxWidth(),
            placeholder = {Text("8000") },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6A6C6A),
                unfocusedBorderColor = Color(0xFF6A6C6A)
            )
        )
        Text(
            text = "Phone Number",
            fontFamily = interFontsMedium,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp).fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = Color(0xFF454745)
        )
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = viewModel.phonePrefix,
                onValueChange = { viewModel.phonePrefix = it},
                modifier = Modifier.padding(top = 8.dp).width(84.5.dp),
                placeholder = { Text("+65")},
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6A6C6A),
                    unfocusedBorderColor = Color(0xFF6A6C6A)
                )
            )
            OutlinedTextField(
                value = viewModel.phoneNumber,
                onValueChange = { if (it.all { c -> c.isDigit() }) viewModel.phoneNumber = it},
                modifier = Modifier.padding(top = 8.dp).weight(1f),
                placeholder = { Text("991251255")},
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6A6C6A),
                    unfocusedBorderColor = Color(0xFF6A6C6A)
                )
            )
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color(0xFF6A6C6A),
            modifier = Modifier.padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 16.dp)
        )
        PrimaryButton("Save", onClick = {
                viewModel.saveChanges(onSuccess = onBack)
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(bottom = 32.dp))
    }
}
