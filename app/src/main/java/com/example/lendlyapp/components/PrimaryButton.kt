package com.example.lendlyapp.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.ui.theme.interFontsSemiBold

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
    ){
    Button(
        onClick=onClick, colors = ButtonDefaults.buttonColors(
        containerColor = Color(0xFF7BF179),
        contentColor = Color(0xFF102000),
    ),
        shape = RoundedCornerShape(100.dp),
        modifier = modifier
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
fun PrimaryButtonPreview(){
    PrimaryButton("Log In",onClick = {
        println("Click en Log In")
    })
}