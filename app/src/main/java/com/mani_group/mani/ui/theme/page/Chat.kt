package com.mani_group.mani.ui.theme.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mani_group.mani.data.dropmenu
import com.mani_group.mani.ui.theme.navbar.Bottombarnav
import com.mani_group.mani.ui.theme.page.chat_interface.BanierUtilisateur
//import com.mani_group.mani.ui.theme.page.chat_interface.Discussion
import com.mani_group.mani.ui.theme.page.chat_interface.Discussions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chat(navctl: NavHostController) {

    Scaffold(
        bottomBar = {
            Bottombarnav(navctl)
        },
        topBar = {

            TopAppBar(

                title = { Text(
                    "Message",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    fontSize = 25.sp
                )  },
                actions = {
//                    dropmenu()
                    IconButton(onClick = { /*TODO*/ }) {
                        Column(
                            modifier = Modifier.padding(10.0.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "", tint = MaterialTheme.colorScheme.inverseOnSurface)
                            Text("Trouver", color = MaterialTheme.colorScheme.inverseOnSurface, fontWeight = FontWeight.SemiBold)
                        }
                    }
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
//                .verticalScroll(rememberScrollState())
        ) {
            BanierUtilisateur()
            Discussions()
        }

    }
}