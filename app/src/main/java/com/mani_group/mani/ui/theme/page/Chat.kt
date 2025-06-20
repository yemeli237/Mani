package com.mani_group.mani.ui.theme.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.mani_group.mani.data.dropmenu
import com.mani_group.mani.ui.theme.navbar.Bottombarnav

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chat(navctl: NavHostController) {

    Scaffold(
        bottomBar = {
            Bottombarnav(navctl)
        },
        topBar = {

            TopAppBar(

                title = { Text("Chat")  },
                actions = {
                    dropmenu()
                },
                colors =  TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00bf63),
                    titleContentColor = MaterialTheme.colorScheme.inverseOnSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.inverseOnSurface
                )

            )
        }
    ) {padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            Text("Home")
        }

    }
}