package com.mani_group.mani.ui.theme.page

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.mani_group.mani.Route
import com.mani_group.mani.data.Pharmacie
import com.mani_group.mani.data.couleurprincipal
//import com.mani_group.mani.data.pharmacie

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailPagePharmacie(navctl: NavHostController, produit: String) {
    var pharmacie by remember {
        mutableStateOf(Pharmacie())
    }


    LaunchedEffect(Unit) {
        Firebase.firestore.collection("pharmacies")
            .document(produit)
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    var resultat = it.result.toObject(Pharmacie::class.java)
                    if (resultat != null) {
                        pharmacie = resultat
                    }
                }
            }
    }


    Scaffold(
        topBar = {
            TopAppBar(
//                contentColor = couleurprincipal,
                navigationIcon = {
                    IconButton(onClick = {
                        navctl.navigate(Route.Pharmacie)
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                },
                backgroundColor = couleurprincipal,
                title = {
                    Text(
                        "Detail",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                },

                )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Text("")
            Text(
                text = "${pharmacie}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(bottom = 8.dp),
                color = Color(0xFF00bf63),
                modifier = Modifier.padding(16.dp, 0.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))

        }
    }
}

//    Scaffold(
//        topBar = {
//            TopAppBar(
////                contentColor = couleurprincipal,
//                navigationIcon = {
//                    IconButton(onClick = {
//                        navctl.navigate(Route.Pharmacie)
//                    }) {
//                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "", tint = Color.White)
//                    }
//                },
//                backgroundColor = couleurprincipal,
//                title = {
//                    Text("Detail", textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 18.sp)
//                },
//
//            )
//        }
//    ){padding ->
//        Column(
//            modifier = Modifier.fillMaxSize()
//                .padding(padding)
//                .verticalScroll(rememberScrollState())
//        ) {
//            Text("")
//            Text(
//                text = "${pharmacie.nom}",
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold,
////                modifier = Modifier.padding(bottom = 8.dp),
//                color = Color(0xFF00bf63),
//                modifier = Modifier.padding(16.dp, 0.dp)
//            )
//            Spacer(modifier = Modifier.height(5.dp))
//
//            Column(
//                modifier = Modifier.padding(16.dp)
//            ) {
//                val pageetat = rememberPagerState {
//                    pharmacie.image.size
//                }
//                HorizontalPager(state =pageetat, pageSpacing = 16.dp , modifier = Modifier.height(220.dp)) {
//                    AsyncImage(
//                        model = pharmacie.image.get(it),
//                        contentDescription = "image de ref",
//                        modifier = Modifier.fillMaxWidth()
//                            .height(220.dp)
//                            .clip(
//                                RoundedCornerShape(16.dp)
//                            )
//                    )
//                }
//            }
//            Spacer(modifier = Modifier.height(5.dp))
//            val ouvert = if(pharmacie.ouvert) "OUVERT" else "FERME"
//            Card(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth()
//                    .height(250.dp)
//                    ,
//                shape = RoundedCornerShape(12.dp),
//                elevation = CardDefaults.cardElevation(2.dp),
//
//
//                ) {
//                Row() {
//                    Text("Pharmacie " +
//                            pharmacie.nom,
//                        modifier = Modifier.padding(8.dp),
//                        fontSize = 25.sp, fontWeight = FontWeight.SemiBold,
//                        color = Color(0xFF00bf63)
//                    )
//                    Spacer(modifier = Modifier.weight(1f))
//                    Text(
//                        ouvert,
//                        modifier = Modifier.padding(8.dp),
//                        fontSize = 16.sp,
//                    )
//                }
//                Spacer(modifier = Modifier.height(8.dp))
//                Row {
//                    Text(
//                        pharmacie.localisation,
//                        modifier = Modifier.padding(8.dp),
//                        fontSize = 16.sp,
//                    )
//                    Spacer(modifier = Modifier.weight(1f))
//                    Text(
//                        pharmacie.telephone,
//                        modifier = Modifier.padding(8.dp),
//                        fontSize = 16.sp,)
//                }
//                Row {
//                    Text(text = pharmacie.ouverture + "   ->", modifier = Modifier.padding(8.dp), fontSize = 20.sp)
//
//                    Text(pharmacie.fermeture, modifier = Modifier.padding(8.dp), fontSize = 18.sp)
//                    Spacer(modifier = Modifier.weight(1f))
//                    Text(text ="De garde  :" +if(!pharmacie.garde)"  NON" else "  OUI", Modifier.padding(8.dp),fontSize = 16.sp)
//                }
//                Spacer(modifier = Modifier.height(8.dp))
//                Text("Ville : ${pharmacie.ville}", modifier = Modifier.padding(8.dp), fontSize = 16.sp)
//                Spacer(modifier = Modifier.height(8.dp))
//                Text("Region : ${pharmacie.region}", modifier = Modifier.padding(8.dp), fontSize = 16.sp)
//            }
//            Spacer(modifier = Modifier.height(5.dp))
//            Button(
//                onClick = {},
//                modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp),
//                shape = RoundedCornerShape(10.dp),
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00bf63))
//            ) {
//                Text("Passer un appel", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
//            }
////            Spacer(modifier = Modifier.height(5.dp))
//            Button(
//                onClick = {},
//                modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp),
//                shape = RoundedCornerShape(10.dp),
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00bf63))
//            ) {
//                Text("Voire les produits", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
//            }
//
//            OutlinedTextField(
//                value = "",
//                onValueChange = {},
//                label = {
//                    Text("Ecrire un message ou une requete")
//                },
//                modifier = Modifier.fillMaxWidth()
//                    .height(200.dp)
//                    .padding(16.dp, 0.dp)
//            )
//            Row {
//                Spacer(modifier = Modifier.weight(1f).padding(8.dp))
//                IconButton(onClick = {}, modifier = Modifier.width(100.dp)) {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text("Envoyer", color = Color(0xFF00bf63))
//                        Spacer(modifier = Modifier.weight(1f))
//                        Icon(imageVector = Icons.Default.Send, contentDescription = "", tint = Color(0xFF00bf63))
//                    }
//                }
//            }
//
//        }
//    }


//}
