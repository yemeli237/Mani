package com.mani_group.mani.ui.theme.page

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.mani_group.mani.GlobalNav
import com.mani_group.mani.Route
import com.mani_group.mani.data.Pharmacie
import com.mani_group.mani.data.couleurprincipal
import com.mani_group.mani.data.etsmodel

//import com.mani_group.mani.data.pharmacie

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailPagePharmacie(navctl: NavHostController, produit: String) {
    var pharmacie by remember {
        mutableStateOf(etsmodel())
    }


    LaunchedEffect(Unit) {
        Firebase.firestore.collection("ets")
            .document(produit)
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    var resultat = it.result.toObject(etsmodel::class.java)
                    if (resultat != null) {
                        pharmacie = resultat
                    }
                }
            }
    }



    Scaffold(
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
        }
    ){padding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Text("")
            Text(
                text = pharmacie.nom,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(bottom = 8.dp),
                color = Color(0xFF00bf63),
                modifier = Modifier.padding(16.dp, 0.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
//                val pageetat = rememberPagerState {
//                    pharmacie.image.size
//                }
//                HorizontalPager(state =pageetat, pageSpacing = 16.dp , modifier = Modifier.height(220.dp)) {
                    AsyncImage(
                        model = pharmacie.image,
                        contentDescription = "image de ref",
                        modifier = Modifier.fillMaxWidth()
                            .height(220.dp)
                            .clip(
                                RoundedCornerShape(16.dp)
                            )
                    )
//                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            val ouvert = if(pharmacie.ouvert) "OUVERT" else "FERME"
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(5.dp)) {
                    Text(pharmacie.nom, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                    ) {
                        Text("Categorie :", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                        Text(pharmacie.categorie,fontSize = 15.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                    ) {
                        Text("Region :", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                        Text(pharmacie.region,fontSize = 15.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                    ) {
                        Text("Ville :", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                        Text(pharmacie.ville,fontSize = 15.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                    ) {
                        Text("Localisation :", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                        Text(pharmacie.localisation,fontSize = 15.sp)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                    ) {
                        Text("Note :", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                        Text("8/10",fontSize = 15.sp)
                    }

                    Spacer(modifier = Modifier.height(15.dp))


                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00bf63))
            ) {
                Text("Passer un appel", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
//            Spacer(modifier = Modifier.height(5.dp))
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00bf63))
            ) {
                Text("Voire les produits", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = {
                    Text("Ecrire un message ou une requete")
                },
                modifier = Modifier.fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp, 0.dp)
            )
            Row {
                Spacer(modifier = Modifier.weight(1f).padding(8.dp))
                IconButton(onClick = {}, modifier = Modifier.width(100.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Envoyer", color = Color(0xFF00bf63))
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(imageVector = Icons.Default.Send, contentDescription = "", tint = Color(0xFF00bf63))
                    }
                }
            }

        }
    }


}
