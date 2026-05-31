package com.example.lendlyapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.pages.manage.ManageScreen
import com.example.lendlyapp.ui.theme.interFontsMedium

@Composable
fun Divider(text: String){
    Column(){
        Text(
            text = text,
            fontFamily = interFontsMedium,
            fontSize = 14.sp,
            color = Color(0xFF6A6C6A),
            modifier = Modifier.padding(start = 16.dp, top = 36.dp)
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = Color(0xFF6A6C6A),
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DividerPreview() {
    Divider("test")
}