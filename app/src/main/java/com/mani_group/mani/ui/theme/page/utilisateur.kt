package com.mani_group.mani.ui.theme.page

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.sharp.ExitToApp
import androidx.compose.material.icons.sharp.KeyboardArrowRight
import androidx.compose.material.icons.sharp.Lock
import androidx.compose.material.icons.sharp.Notifications
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material.icons.sharp.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.mani_group.mani.R

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*

import androidx.compose.ui.graphics.asImageBitmap
import android.graphics.Bitmap
import android.util.Base64
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mani_group.mani.GlobalNav
import com.mani_group.mani.Route
import com.mani_group.mani.viewmodell.AuthViewModesl
import java.io.ByteArrayOutputStream

@Composable
fun UtilisateurInfo(navctl: NavHostController, AuthViewModesl: AuthViewModesl = viewModel()) {
    var isloading by rememberSaveable {
        mutableStateOf(false)
    }

    var name by remember {
        mutableStateOf("")
    }
    var img by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    /////recuper le nom
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener(){
                name = it.result.get("nom").toString()
//                    .split(" ")[0]
            }
    }
    ////recupere l'image
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener(){
                img = it.result.get("img").toString()

            }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row (
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){

            IconButton(
                onClick = {

                    GlobalNav.navctl.navigate(Route.Home)

                }
            ) {
                androidx.compose.material3.Icon(imageVector = Icons.Default.Close, contentDescription = "close post page")
            }
            Spacer(modifier = Modifier.width(10.dp))
            androidx.compose.material3.Text(
                "Aporterz des modifiaction",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
        //titre
        Text("Profil", fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00bf63))
        Spacer(modifier = Modifier.height(20.dp))
        //photo de profil
        Card(
            modifier = Modifier.size(150.dp)
                .border(width = 2.dp, color = Color(0xFF00bf63), shape = RoundedCornerShape(100.dp)),
            shape = RoundedCornerShape(100.dp),
            elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            val inputStream = Base64.decode(img, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(inputStream, 0, inputStream.size)
            if (bitmap == null){
                Text("")
            }else{
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

        }

        Spacer(modifier = Modifier.height(10.dp))
        //nom utili
        Text(name,fontWeight = FontWeight.Bold, fontSize = 20.sp,)
        Spacer(modifier = Modifier.height(10.dp))
        //edition profilisateur
        TextButton(
            onClick = {navctl.navigate(Route.EditUser)},
            modifier = Modifier.padding(20.dp, 0.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Icon(imageVector = Icons.Sharp.Person, contentDescription = "", tint = MaterialTheme.colorScheme.onBackground)
                    Text("Editer le profil",
                        color = Color(0xFF00bf63),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(10.dp, 0.dp)
                        )
                }
                Icon(imageVector = Icons.Sharp.KeyboardArrowRight, contentDescription = "", tint = MaterialTheme.colorScheme.onBackground)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        //voir les notification
        TextButton(
            onClick = {},
            modifier = Modifier.padding(20.dp, 0.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Icon(imageVector = Icons.Sharp.Notifications, contentDescription = "", tint = MaterialTheme.colorScheme.onBackground)
                    Text("Notifications",
                        color = Color(0xFF00bf63),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(10.dp, 0.dp)
                    )
                }
                Icon(imageVector = Icons.Sharp.KeyboardArrowRight, contentDescription = "", tint = MaterialTheme.colorScheme.onBackground)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        //voir les produit
        TextButton(
            onClick = {},
            modifier = Modifier.padding(20.dp, 0.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Icon(imageVector = Icons.Sharp.ShoppingCart, contentDescription = "", tint = MaterialTheme.colorScheme.onBackground)
                    Text("Produits/Shopping",
                        color = Color(0xFF00bf63),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(10.dp, 0.dp)
                    )
                }
                Icon(imageVector = Icons.Sharp.KeyboardArrowRight, contentDescription = "", tint = MaterialTheme.colorScheme.onBackground)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        //changer de mot de pass
        TextButton(
            onClick = {},
            modifier = Modifier.padding(20.dp, 0.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Icon(imageVector = Icons.Sharp.Lock, contentDescription = "", tint = MaterialTheme.colorScheme.onBackground)
                    Text("Changer de mot de passe",
                        color = Color(0xFF00bf63),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(10.dp, 0.dp)
                    )
                }
                Icon(imageVector = Icons.Sharp.KeyboardArrowRight, contentDescription = "", tint = MaterialTheme.colorScheme.onBackground)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
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
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00bf63), contentColor = Color.White)
        ) {
            Icon(imageVector = Icons.Sharp.ExitToApp, contentDescription = "", tint = MaterialTheme.colorScheme.onBackground)
            Text(text = if(!isloading)"Se deconnecter" else "Deconnexion en cours...", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}