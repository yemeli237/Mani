package com.mani_group.mani.ui.theme.page.geoloc

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.darkColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mani_group.mani.GlobalNav
import com.mani_group.mani.Route
import com.mani_group.mani.data.couleurprincipal
import android.content.SharedPreferences
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllePharmacie(viewModelallpharmacie : AllerpharmacieViewModel = viewModel()){
    var eta by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val store = context.getSharedPreferences("localisation", Context.MODE_PRIVATE)

    Scaffold(
        floatingActionButton = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { GlobalNav.navctl.popBackStack()},
                    colors = ButtonDefaults.buttonColors( backgroundColor = couleurprincipal)) {
                    Text("Tout annuller", color = Color.Red, fontWeight = FontWeight.SemiBold)
                }
                Column {
                    androidx.compose.material3.IconButton(
                        onClick = {
                            GlobalNav.navctl.navigate((Route.AllePharmacie))
                        },
                        modifier = Modifier.size(50.dp),
                        colors = IconButtonDefaults.iconButtonColors(containerColor = couleurprincipal, contentColor = Color.White)
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Filled.LocationSearching,
                            contentDescription = "Action button",
                        )
                    }
                }
            }
        },
        topBar = {
            androidx.compose.material3.TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        GlobalNav.navctl.popBackStack()
                        /*TODO*/
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.inverseOnSurface
                        )
                    }
                },
                title = {

                    Text(
                        "",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        fontSize = 25.sp,
                        modifier = Modifier.padding(10.dp, 0.dp)
                    )
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {

                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.inverseOnSurface
                        )

                    }



                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00bf63),
                    titleContentColor = MaterialTheme.colorScheme.inverseOnSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.inverseOnSurface
                )

            )
            Card(
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Depart : ",)
                    Text("Position actuelle",)
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Societe : ",)
                    store.getString("nom", eta)?.let { Text(it) }
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Distance", )
                        Text("${viewModelallpharmacie.distanceKm}m",)
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Mode", )
                        store.getString("mode", viewModelallpharmacie.mode)?.let { Text(it) }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Duree", )
                        Text("${viewModelallpharmacie.duree}min", )
                    }
                }
            }
        }



    ){paddingValues ->
        Column (modifier = Modifier.padding(paddingValues)){
            viewModelallpharmacie.Livraison()
        }

    }
}



