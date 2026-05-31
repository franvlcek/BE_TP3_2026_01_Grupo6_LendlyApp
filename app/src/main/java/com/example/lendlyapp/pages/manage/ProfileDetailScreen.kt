package com.example.lendlyapp.pages.manage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.R
import com.example.lendlyapp.components.Divider
import com.example.lendlyapp.components.PrimaryButton
import com.example.lendlyapp.data.session.SessionManager
import com.example.lendlyapp.ui.theme.interFontsMedium
import com.example.lendlyapp.ui.theme.montserratFontsSemiBold

@Composable
fun ProfileDetailScreen(
    sessionManager: SessionManager,
    onBack: () -> Unit
){
    var fullName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var day by remember { mutableStateOf("") }
    var month by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var phonePrefix by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
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
                    contentDescription = "BackArrow",
                    modifier = Modifier.size(width = 40.dp, height = 40.dp).padding(start = 8.dp)
                )
            }
        }
        Text(
            text = "Enter your personal details",
            fontFamily = montserratFontsSemiBold,
            fontSize = 28.sp,
            modifier = Modifier.padding(start = 16.dp)
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
            value = fullName,
            onValueChange = { fullName = it},
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp).fillMaxWidth(),
            placeholder = {Text("John D.") },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6A6C6A),
                unfocusedBorderColor = Color(0xFF6A6C6A),
                focusedLabelColor = Color(0xFF6A6C6A),
                unfocusedLabelColor = Color(0xFF6A6C6A),
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
            value = lastName,
            onValueChange = { lastName = it},
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp).fillMaxWidth(),
            placeholder = {Text("Doe") },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6A6C6A),
                unfocusedBorderColor = Color(0xFF6A6C6A),
                focusedLabelColor = Color(0xFF6A6C6A),
                unfocusedLabelColor = Color(0xFF6A6C6A),
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
        Row() {
            Column(){
                Text(
                    text = "Day",
                    fontFamily = interFontsMedium,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp),
                    textAlign = TextAlign.Start,
                    color = Color(0xff6A6C6A)
                )

                OutlinedTextField(
                    value = day,
                    onValueChange = { day = it},
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp).width(84.5.dp),
                    placeholder = { Text("08")},
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF6A6C6A),
                        unfocusedBorderColor = Color(0xFF6A6C6A),
                        focusedLabelColor = Color(0xFF6A6C6A),
                        unfocusedLabelColor = Color(0xFF6A6C6A),
                    )
                )
            }
            Column(){
                Text(
                    text = "Month",
                    fontFamily = interFontsMedium,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp),
                    textAlign = TextAlign.Start,
                    color = Color(0xff6A6C6A)
                )

                OutlinedTextField(
                    value = month,
                    onValueChange = { month = it},
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp).width(84.5.dp),
                    placeholder = { Text("12")},
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF6A6C6A),
                        unfocusedBorderColor = Color(0xFF6A6C6A),
                        focusedLabelColor = Color(0xFF6A6C6A),
                        unfocusedLabelColor = Color(0xFF6A6C6A),
                    )
                )
            }
            Column(){
                Text(
                    text = "Year",
                    fontFamily = interFontsMedium,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp),
                    textAlign = TextAlign.Start,
                    color = Color(0xff6A6C6A)
                )

                OutlinedTextField(
                    value = year,
                    onValueChange = { year = it},
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp).width(160.dp),
                    placeholder = {Text("1997") },
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF6A6C6A),
                        unfocusedBorderColor = Color(0xFF6A6C6A),
                        focusedLabelColor = Color(0xFF6A6C6A),
                        unfocusedLabelColor = Color(0xFF6A6C6A),
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
            value = address,
            onValueChange = { address = it},
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp).fillMaxWidth(),
            placeholder = {Text("Somewhere IN BLOCK 12") },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6A6C6A),
                unfocusedBorderColor = Color(0xFF6A6C6A),
                focusedLabelColor = Color(0xFF6A6C6A),
                unfocusedLabelColor = Color(0xFF6A6C6A),
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
            value = city,
            onValueChange = { city = it},
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp).fillMaxWidth(),
            placeholder = {Text("Davao City") },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6A6C6A),
                unfocusedBorderColor = Color(0xFF6A6C6A),
                focusedLabelColor = Color(0xFF6A6C6A),
                unfocusedLabelColor = Color(0xFF6A6C6A),
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
            value = postalCode,
            onValueChange = { postalCode = it},
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp).fillMaxWidth(),
            placeholder = {Text("8000") },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6A6C6A),
                unfocusedBorderColor = Color(0xFF6A6C6A),
                focusedLabelColor = Color(0xFF6A6C6A),
                unfocusedLabelColor = Color(0xFF6A6C6A),
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
        Row() {
            OutlinedTextField(
                value = phonePrefix,
                onValueChange = { phonePrefix = it},
                modifier = Modifier.padding(top = 8.dp).width(84.5.dp),
                placeholder = { Text("+65")},
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6A6C6A),
                    unfocusedBorderColor = Color(0xFF6A6C6A),
                    focusedLabelColor = Color(0xFF6A6C6A),
                    unfocusedLabelColor = Color(0xFF6A6C6A),
                )
            )
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it},
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp).width(265.dp),
                placeholder = { Text("991251255")},
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6A6C6A),
                    unfocusedBorderColor = Color(0xFF6A6C6A),
                    focusedLabelColor = Color(0xFF6A6C6A),
                    unfocusedLabelColor = Color(0xFF6A6C6A),
                )
            )
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color(0xFF6A6C6A),
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
        )
        PrimaryButton("Save", onClick = {
            onBack()
            },
            modifier = Modifier.padding(top = 8.dp))
    }
}

/*
@Preview(showBackground = true)
@Composable
fun ProfileDetailScreenPreview() {
    ProfileDetailScreen()
}*/