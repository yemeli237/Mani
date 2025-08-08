package com.mani_group.mani.ui.theme.page.chat_interface

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mani_group.mani.GlobalNav
import com.mani_group.mani.data.Pharmacie
import com.mani_group.mani.data.postdata

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Commentaire(post: String?) {
    var article = remember {
        mutableSetOf(postdata())
    }

    var pharmacie by remember {
        mutableStateOf(Pharmacie())
    }
    Scaffold(
        topBar = {

            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        GlobalNav.navctl.popBackStack()
                        /*TODO*/ }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "", tint = MaterialTheme.colorScheme.inverseOnSurface)
                    }
                },
                title = {
                    Text("Serction commentaire")
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {

                        Icon(imageVector = Icons.Outlined.Info, contentDescription = "", tint = MaterialTheme.colorScheme.inverseOnSurface)

                    }
                },
                colors =  TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00bf63),
                    titleContentColor = MaterialTheme.colorScheme.inverseOnSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.inverseOnSurface
                )

            )
        }
    ) {paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Text("$post")

        }

    }
}