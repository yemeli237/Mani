package com.mani_group.mani.ui.theme.page

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.sharp.ArrowDropDown
import androidx.compose.material.icons.sharp.KeyboardArrowLeft
import androidx.compose.material.icons.sharp.Notifications
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.mani_group.mani.Route
import com.mani_group.mani.data.couleurprincipal
import com.mani_group.mani.data.dropmenu
import com.mani_group.mani.ui.theme.navbar.Bottombarnav

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Donsang(navctl: NavHostController) {
    var name by remember {
        mutableStateOf("")
    }
    val isConnected = Firebase.auth.currentUser != null
    val connect by remember {
        mutableStateOf(Firebase.auth.currentUser)
    }
    var sexe by remember {
        mutableStateOf("Homme")
    }
    var homme by remember {
        mutableStateOf(true)
    }
    var femme by remember {
        mutableStateOf(false)
    }
    var nom by remember {
        mutableStateOf("")
    }
    var age by remember {
        mutableStateOf("")
    }
    var tail by remember {
        mutableStateOf("")
    }
    var groupesanguin by remember {
        mutableStateOf("A")
    }
    var A by remember {
        mutableStateOf(true)
    }
    var O by remember {
        mutableStateOf(false)
    }
    var B by remember {
        mutableStateOf(false)
    }
    var AB by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener(){
                name = it.result.get("nom").toString()
            }
    }
    Scaffold(

        bottomBar = {
//            Bottombarnav(navctl)
            NavigationBar (
                windowInsets = NavigationBarDefaults.windowInsets,
                tonalElevation = 2.dp,
//                modifier = Modifier.height(100.dp)
            ){
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    OutlinedButton(
                        onClick = {navctl.navigate(Route.Home)},
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        Text("Annuler", fontSize = 18.sp, color = Color.Red)
                    }
                    OutlinedButton(
                        onClick = {},
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Soumetre", fontSize = 18.sp, color = couleurprincipal)
                    }
                }
            }
        },
        topBar = {


            TopAppBar(

                title = { Text("")  },
                actions = {


                    if (connect != null && !connect!!.isAnonymous){
                        TextButton(
                            onClick = {
                                navctl.navigate(Route.UtilisateurInfo)
                            }
                        ) {
                            Text(name,fontWeight = FontWeight.Bold, fontSize = 18.sp,)
                        }
                    }
                    else{
                        IconButton(onClick = {
                            navctl.navigate(Route.Login)
                        }) { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "", tint = Color.White) }
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(imageVector = Icons.Sharp.Notifications, contentDescription = "", tint = Color.White)
                    }
                    //
                },
                colors =  TopAppBarDefaults.topAppBarColors(
                    containerColor = couleurprincipal,
                    titleContentColor = MaterialTheme.colorScheme.inverseOnSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.inverseOnSurface
                ),


                )

        },


        ){padding->
            Column(
                modifier = Modifier.padding(16.dp).fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Vous pouvez sauver une vie", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        colors = RadioButtonColors(selectedColor = couleurprincipal, unselectedColor = couleurprincipal.copy(0.3f), disabledSelectedColor = Color.White, disabledUnselectedColor = Color.White),
                        selected = homme,
                        onClick = { sexe = "Home"; femme = false; homme = true})
                    Text("Homme", fontSize = 16.sp, color = if(homme) couleurprincipal else Color.Black)
                    RadioButton(
                        colors = RadioButtonColors(selectedColor = couleurprincipal, unselectedColor = couleurprincipal.copy(0.3f), disabledSelectedColor = Color.White, disabledUnselectedColor = Color.White),
                        selected = femme,
                        onClick = { sexe = "Femme"; homme = false; femme = true})
                    Text("Femme", fontSize = 16.sp, color = if(femme) couleurprincipal else Color.Black)
                }

                TextField(
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = couleurprincipal,
                        cursorColor = Color.Black,
                        focusedIndicatorColor = couleurprincipal,
                        unfocusedIndicatorColor = couleurprincipal),
                    shape = RoundedCornerShape(5.dp),
                    textStyle = TextStyle(fontSize = 18.sp),
                    modifier = Modifier.fillMaxWidth(),
                    value = nom, onValueChange = {nom = it}, label = {
                    Text("Nom")
                })
                Spacer(modifier = Modifier.height(10.dp))
                //
                TextField(
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = couleurprincipal,
                        cursorColor = Color.Black,
                        focusedIndicatorColor = couleurprincipal,
                        unfocusedIndicatorColor = couleurprincipal),
                    shape = RoundedCornerShape(5.dp),
                    textStyle = TextStyle(fontSize = 18.sp),
                    modifier = Modifier.fillMaxWidth(),
                    value = nom, onValueChange = {nom = it}, label = {
                        Text("Prenom")
                    })
                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = couleurprincipal,
                        cursorColor = Color.Black,
                        focusedIndicatorColor = couleurprincipal,
                        unfocusedIndicatorColor = couleurprincipal),
                    shape = RoundedCornerShape(5.dp),
                    textStyle = TextStyle(fontSize = 18.sp),
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    value = nom, onValueChange = {nom = it}, label = {
                        Text("Contact")
                    })
                Spacer(modifier = Modifier.height(10.dp))
                ////
                Row {
                    OutlinedTextField(
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = couleurprincipal,
                            cursorColor = Color.Black,
                            focusedIndicatorColor = couleurprincipal,
                            unfocusedIndicatorColor = couleurprincipal),
                        shape = RoundedCornerShape(5.dp),
                        textStyle = TextStyle(fontSize = 18.sp),
                        modifier = Modifier.width(200.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        value = age, onValueChange = {age = it},
                        label = {
                            Text("Age")
                        }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    ///
                    OutlinedTextField(
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = couleurprincipal,
                            cursorColor = Color.Black,
                            focusedIndicatorColor = couleurprincipal,
                            unfocusedIndicatorColor = couleurprincipal),
                        shape = RoundedCornerShape(5.dp),
                        textStyle = TextStyle(fontSize = 18.sp),
                        modifier = Modifier.width(200.dp),
                        value = tail, onValueChange = {tail = it},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = {
                            Text("Taille")
                        }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RadioButton(
                            colors = RadioButtonColors(selectedColor = couleurprincipal, unselectedColor = couleurprincipal.copy(0.3f), disabledSelectedColor = Color.White, disabledUnselectedColor = Color.White),
                            selected = A,
                            onClick = { groupesanguin = "A"; B = false; A = true; O = false; AB = false})
                        Text("A", fontSize = 16.sp, color = if(A) couleurprincipal else Color.Black)
                    }
                    ///
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RadioButton(
                            colors = RadioButtonColors(selectedColor = couleurprincipal, unselectedColor = couleurprincipal.copy(0.3f), disabledSelectedColor = Color.White, disabledUnselectedColor = Color.White),
                            selected = B,
                            onClick = { groupesanguin = "B"; B = true; A = false; O = false; AB=false})
                        Text("B", fontSize = 16.sp, color = if(B) couleurprincipal else Color.Black)
                    }
                    ///

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RadioButton(
                            colors = RadioButtonColors(selectedColor = couleurprincipal, unselectedColor = couleurprincipal.copy(0.3f), disabledSelectedColor = Color.White, disabledUnselectedColor = Color.White),
                            selected = O,
                            onClick = { groupesanguin = "O"; B = false; A = false; O = true; AB = false})
                        Text("O", fontSize = 16.sp, color = if(O) couleurprincipal else Color.Black)
                    }
                    //
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RadioButton(
                            colors = RadioButtonColors(selectedColor = couleurprincipal, unselectedColor = couleurprincipal.copy(0.3f), disabledSelectedColor = Color.White, disabledUnselectedColor = Color.White),
                            selected = AB,
                            onClick = { groupesanguin = "O"; B = false; A = false; O = false; AB = true})
                        Text("AB", fontSize = 16.sp, color = if(AB) couleurprincipal else Color.Black)
                    }
                }


            }

    }
}