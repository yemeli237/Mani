package com.mani_group.mani

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mani_group.mani.data.AppUtil
import com.mani_group.mani.data.couleurprincipal
import com.mani_group.mani.viewmodell.AuthViewModesl

@Composable
fun Login(navctl: NavHostController, modifier: Modifier = Modifier, AuthViewModesl: AuthViewModesl = viewModel()) {
    var adress by rememberSaveable {
        mutableStateOf("")
    }
    var pass by rememberSaveable {
        mutableStateOf("")
    }
    var context = LocalContext.current

    var isloading by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Image(painter = painterResource(R.drawable.mani), contentDescription = "")
        Text("Se connecter", fontWeight = FontWeight.Bold, fontSize = 20.sp)




        OutlinedTextField(value = adress, onValueChange = {adress = it}, label = {
            Text("Adresse mail",fontWeight = FontWeight.Bold,)
        }, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = couleurprincipal,
            focusedBorderColor = couleurprincipal,
            unfocusedBorderColor = couleurprincipal
        )
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = pass, onValueChange = {pass = it}, label = {
            Text("Mot de passe", fontWeight = FontWeight.Bold,)
        },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = couleurprincipal,
            focusedBorderColor = couleurprincipal,
            unfocusedBorderColor = couleurprincipal
        ),
            visualTransformation = PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            enabled = !isloading,
            onClick = {
                isloading = true
                adress = adress.replace("\\s".toRegex(), "")
                AuthViewModesl.Login(adress, pass) { success, message ->
                    if (success) {
                        isloading = false
                        navctl.navigate(Route.Home){
                            popUpTo(Route.Home){inclusive = true}
                        }
                    } else {
                        isloading = false
                        // Afficher un message d'erreur à l'utilisateur
                        AppUtil.showToast(context, message?: "Une erreur s'est produite")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00bf63), contentColor = Color.White)
        ) { Text( text = if(!isloading) "Connexion" else "Chargement...", fontWeight = FontWeight.Bold, fontSize = 20.sp) }
        Spacer(modifier = Modifier.height(20.dp))
        Text("Ou se connecter avec")
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = {}, modifier = Modifier.size(100.dp), shape = RoundedCornerShape(100.dp)) {
                Image(painter = painterResource(R.drawable.f), contentDescription = "")
            }

            TextButton(onClick = {},modifier = Modifier.size(100.dp),shape = RoundedCornerShape(100.dp)) {
                Image(painter = painterResource(R.drawable.t), contentDescription = "")
            }

            TextButton(onClick = {},modifier = Modifier.size(100.dp),shape = RoundedCornerShape(100.dp)) {
                Image(painter = painterResource(R.drawable.g), contentDescription = "")
            }
        }
        TextButton(onClick = {navctl.navigate(Route.Singin)}) {
            Row {
                Text("Pas de compte?")
                Text("Creer", color = couleurprincipal, fontWeight = FontWeight.Bold)
            }
        }


    }
}