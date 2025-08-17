package com.mani_group.mani.ui.theme.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mani_group.mani.Route
import com.mani_group.mani.data.BottomNavItem


val item = listOf(
    BottomNavItem(
        title = "Aceuil",
        icon = Icons.Default.Home,
        route = Route.Home
    ),
    BottomNavItem(
        title = "Pharmacie",
        icon = Icons.Default.DateRange,
        route = Route.Pharmacie
    ),
    BottomNavItem(
        title = "Message",
        icon = Icons.Default.Message,
        route = Route.Chat
    ),
    BottomNavItem(
        title = "Actus",
        icon = Icons.Default.Info,
        route = Route.Actus
    ),
//    BottomNavItem(
//        title = "Menu",
//        icon = Icons.Default.Menu,
//        route = Route.Menu
//    ),


)


@Composable
fun Bottombarnav(navController: NavController){
    NavigationBar {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            item.forEachIndexed{index, item ->
                NavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = { navController.navigate(item.route) },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = Color(0xFF00bf63)
                        )

                    },
                    label = {
                        Text(item.title, color = Color(0xFF00bf63))
                    }
                    )
            }
        }
    }
}