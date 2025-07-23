package com.mani_group.mani

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.sharp.ArrowDropDown
import androidx.compose.material.icons.sharp.KeyboardArrowDown
import androidx.compose.material.icons.sharp.KeyboardArrowLeft
import androidx.compose.material.icons.sharp.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.mani_group.mani.action.ActionBtn
import com.mani_group.mani.data.couleurprincipal
import com.mani_group.mani.data.dropmenu
import com.mani_group.mani.ui.theme.navbar.Bottombarnav
import com.mani_group.mani.ui.theme.section.BarrRecherche
import com.mani_group.mani.ui.theme.section.Categorie
import com.mani_group.mani.ui.theme.section.produitMedicament
import com.mani_group.mani.ui.theme.section.sectionAlaUne

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navctl: NavHostController) {
    val isConnected = Firebase.auth.currentUser != null
    val connect by remember {
        mutableStateOf(Firebase.auth.currentUser)
    }
    var name by remember {
        mutableStateOf("")
    }
    var openmenu by remember {
        mutableStateOf(false)
    }
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get()
            .addOnCompleteListener(){
                name = it.result.get("nom").toString().split(" ")[0]
            }
    }
    var  opendonmenu by remember {
        mutableStateOf(false)
    }
    Scaffold(
        floatingActionButton = {
            androidx.compose.material3.IconButton(
                onClick = {
                    openmenu = true
                },
                modifier = Modifier.size(50.dp),
                colors = IconButtonDefaults.iconButtonColors(containerColor = couleurprincipal, contentColor = Color.White)
            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "Action button",
                )
            }
        },
        bottomBar = {
            Bottombarnav(navctl)
        },
        topBar = {


                TopAppBar(

                    title = { Text("Acceuil")  },
                    actions = {
                        androidx.compose.material3.DropdownMenu(
                            modifier = Modifier.background(color = couleurprincipal),
                            expanded = openmenu,
                            onDismissRequest = {openmenu = false}) {
                            DropdownMenuItem(onClick = { openmenu = false }) {
                                TextButton(
                                    onClick = {
                                        openmenu = false;
                                        navctl.navigate(Route.Post)
                                              },
                                    modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(imageVector = Icons.Sharp.ArrowDropDown, contentDescription = "", tint = Color.White)
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text("Faire une annonce",color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
                                    }
                                }
                            }
                            DropdownMenuItem(onClick = {openmenu = false; opendonmenu = true  }, modifier = Modifier.fillMaxWidth()) {
                                TextButton(onClick = {openmenu = false; opendonmenu = true}) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(imageVector = Icons.Sharp.KeyboardArrowLeft, contentDescription = "", tint = Color.White)
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text("Faire un don", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
                                    }
                                }
                            }
                            DropdownMenuItem(onClick = { openmenu = false }, modifier = Modifier.fillMaxWidth()) {
                                TextButton(onClick = {openmenu = false}) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(imageVector = Icons.Sharp.ArrowDropDown, contentDescription = "", tint = Color.White)
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            "Demander un dignostique",
                                            color = Color.White,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            textAlign = TextAlign.Center,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                            DropdownMenuItem(onClick = {  }) {
                                TextButton(onClick = {openmenu = false; GlobalNav.navctl.navigate(Route.ChatBot)}, modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(imageVector = Icons.Sharp.ArrowDropDown, contentDescription = "", tint = Color.White)
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text("Mani AI", fontSize = 18.sp,color = Color.White, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
                                    }
                                }
                            }



                        }
                        DropdownMenu(
                            expanded = opendonmenu,
                            onDismissRequest = { opendonmenu = false},
                            modifier = Modifier.background(color = couleurprincipal),
                        ) {
                            DropdownMenuItem(
                                onClick = {}
                            ) {
                                TextButton(onClick = {opendonmenu = false; navctl.navigate(Route.Donsang)}, modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(imageVector = Icons.Sharp.ArrowDropDown, contentDescription = "", tint = Color.White)
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text("Don de sang", fontSize = 18.sp,color = Color.White, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
                                    }
                                }
                            }
                            DropdownMenuItem(
                                onClick = {}
                            ) {
                                TextButton(onClick = {opendonmenu = false}, modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(imageVector = Icons.Sharp.ArrowDropDown, contentDescription = "", tint = Color.White)
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text("Don d'orgne", fontSize = 18.sp,color = Color.White, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
                                    }
                                }
                            }
                        }

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
                            }) { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "", tint = Color.White)}
                        }
                        dropmenu()
                        //
                    },
                    colors =  TopAppBarDefaults.topAppBarColors(
                        containerColor = couleurprincipal,
                        titleContentColor = MaterialTheme.colorScheme.inverseOnSurface,
                        actionIconContentColor = MaterialTheme.colorScheme.inverseOnSurface
                    ),


                    )

        },


    ) {padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
//                .verticalScroll(scrollState)
        ) {


            produitMedicament(modifier = Modifier)
        }

    }
}
