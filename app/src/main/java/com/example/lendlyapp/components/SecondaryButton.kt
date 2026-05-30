package com.example.lendlyapp.components

import androidx.compose.foundation.BorderStroke
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
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick=onClick, colors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = Color(0xFFffffff),
    ),
        shape = RoundedCornerShape(100.dp),border = BorderStroke(0.dp, Color.White),
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
fun SecondaryButtonPreview(){
    SecondaryButton("Log In",onClick = {
        println("Click en Log In")
    })
}