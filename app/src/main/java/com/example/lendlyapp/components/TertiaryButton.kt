package com.example.lendlyapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.ui.theme.interFontsSemiBold

@Composable
fun TertiaryButton(text: String){
    Button(onClick={}, colors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = Color(0xFF6a6c6a),
    ), shape = RoundedCornerShape(100.dp),border = BorderStroke(0.dp, Color(0xFF6a6c6a)),
    ) {
        Text(
            text=text,
            fontFamily = interFontsSemiBold,
            fontSize = 14.sp,
        )
    }
}

@Preview
@Composable
fun TertiaryButtonPreview(){
    TertiaryButton("Log In")
}