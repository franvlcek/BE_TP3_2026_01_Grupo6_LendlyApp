package com.example.lendlyapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lendlyapp.R

@Composable
fun TopBar(
    onProfileClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {}
){
    Spacer(modifier = Modifier.height(40.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.user_icon),
            contentDescription = "Profile",
            modifier = Modifier
                .size(width = 24.dp, height = 24.dp)
                .padding(start = 8.dp)
                .clickable { onProfileClick() }
        )
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(R.drawable.frame_134),
                contentDescription = "Logo",
                modifier = Modifier.size(width = 58.26.dp, height = 20.dp)
            )
        }
        Image(
            painter = painterResource(R.drawable.notification_icon),
            contentDescription = "Notifications",
            modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp)
                .clickable { onNotificationsClick() }
        )
    }
}
@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}