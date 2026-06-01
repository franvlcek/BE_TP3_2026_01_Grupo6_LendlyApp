package com.example.lendlyapp.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lendlyapp.R
import com.example.lendlyapp.Screen
import com.example.lendlyapp.ui.theme.interFontsSemiBold

data class BottomNavItem(
    val name: String,
    val route: String,
    val iconResId: Int
)

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem("Home", Screen.Home.route, R.drawable.ic_home),
        BottomNavItem("Loan", Screen.Loan.route, R.drawable.loan_container),
        BottomNavItem("Shop", Screen.Shop.route, R.drawable.ic_shop),
        BottomNavItem("History", Screen.History.route, R.drawable.ic_history),
        BottomNavItem("Manage", Screen.Manage.route, R.drawable.ic_manage)
    )

    NavigationBar(
        modifier = Modifier.height(80.dp),
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                icon = { 
                    Icon(
                        painter = painterResource(id = item.iconResId), 
                        contentDescription = item.name,
                        modifier = Modifier.size(24.dp),
                        tint = if (isSelected) Color.Unspecified else Color.Gray
                    ) 
                },
                label = { 
                    Text(
                        text = item.name,
                        fontSize = 12.sp,
                        fontFamily = interFontsSemiBold,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color.Black else Color.Gray
                    ) 
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0xFFE8F5E9),
                    selectedIconColor = Color.Unspecified,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color.Black,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}
