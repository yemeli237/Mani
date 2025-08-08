package com.mani_group.mani.ui.theme.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.mani_group.mani.Route
import com.mani_group.mani.data.AppUtil
import com.mani_group.mani.ui.theme.navbar.Bottombarnav
import com.mani_group.mani.viewmodell.AuthViewModesl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(navctl: NavHostController, AuthViewModesl: AuthViewModesl = viewModel()) {

    Scaffold(
        bottomBar = {
            Bottombarnav(navctl)
        },
        topBar = {

            TopAppBar(

                title = { Text("Menu")  },
                navigationIcon = {
                    androidx.compose.material3.IconButton(onClick = {
                        navctl.popBackStack()
                    }) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "",
                            tint = Color.White
                        )
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
        ) {
            var isloading by rememberSaveable {
                mutableStateOf(false)
            }

            TextButton(
                enabled = !isloading,
                onClick = {

                    isloading = true
                    AuthViewModesl.anonyme(){ success ->
                        if (success){
                            isloading = false
//                            navctl.navigate(Route.Home)
                            navctl.navigate(Route.Home){
                                popUpTo(Route.Login){inclusive = true}
                            }
                        }else{
                            isloading = false
                        }
                    }

                },
                modifier = Modifier.fillMaxWidth(),
//                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00bf63), contentColor = Color.White)
            ) { Text(text = if(!isloading)"Se deconnecter" else "Deconnexion en cours...", fontWeight = FontWeight.Bold, fontSize = 20.sp)}
        }

    }
}