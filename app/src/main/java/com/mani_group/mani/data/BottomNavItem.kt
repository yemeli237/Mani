package com.mani_group.mani.data

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val title : String,
    val icon : ImageVector,
    val route : String,
)