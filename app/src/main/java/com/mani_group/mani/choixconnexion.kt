package com.mani_group.mani

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mani_group.mani.viewmodell.AuthViewModesl

@Composable
fun Choixconnexion(navctl: NavHostController, AuthViewModesl: AuthViewModesl = viewModel()) {
    var isloading by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) { Text("Choisir une option",fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF00bf63))
        Image(painter = painterResource(R.drawable.mani), contentDescription = "")
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            enabled = !isloading,
            onClick = {
                isloading = true
                AuthViewModesl.anonyme(){success ->
                    if (success){
                        isloading = false
                        navctl.navigate(Route.Home)
                    }else{
                        isloading = false
                    }
                }

            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().padding(20.dp, 5.dp).height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00bf63), contentColor = Color.White)
        ) {
            Text(text = if(!isloading) "Continuer sans compte" else "Chargement...", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(
            onClick = {
                navctl.navigate(Route.Login)
            },
            modifier = Modifier.fillMaxWidth().padding(20.dp, 5.dp).height(50.dp),
            shape = RoundedCornerShape(10.dp),
        ) {
            Text("Se connecter",fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }





    }
}