package com.mani_group.mani.ui.theme.page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.mani_group.mani.GlobalNav
import com.mani_group.mani.Route
import com.mani_group.mani.data.Pharmacie
import com.mani_group.mani.data.couleurprincipal
import com.mani_group.mani.data.dropmenu
import com.mani_group.mani.data.etsmodel
import com.mani_group.mani.data.postdata
//import com.mani_group.mani.data.pharmacie
import com.mani_group.mani.ui.theme.navbar.Bottombarnav
import com.mani_group.mani.ui.theme.section.BarrRecherchepharmacie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pharmacie(navctl: NavHostController) {
    val pharmacielist = remember {
        mutableStateOf<List<etsmodel>>(emptyList())
    }
//    LaunchedEffect (Unit){
//        try {
//            Firebase.firestore.collection("data")
//                .document("posts")
//                .collection("post")
//                .addSnapshotListener{ result, error ->
//                    if(result != null){
//                        val resultat = result.documents.mapNotNull { doc->
//                            doc.toObject(etsmodel()::class.java)
//                        }
//                        pharmacielist.value = resultat.shuffled()
////
//                    }
//
//                }
//        }catch (e: Exception){
//            Log.d("firebase", "erreur $e")
//        }
//    }



    LaunchedEffect(Unit) {
        Firebase.firestore.collection("ets").limit(20)
            .get().addOnCompleteListener(){
                if (it.isSuccessful){
                    val resultat = it.result.documents.mapNotNull { doc->
                        doc.toObject(etsmodel::class.java)
                    }
                    pharmacielist.value = resultat
                }
            }
    }


//    Text(pharmacielist.value.toString())
    Scaffold(
        bottomBar = {
            Bottombarnav(navctl)
        },

    ) {padding ->
        Column (modifier = Modifier.padding(padding)){
            if (pharmacielist.value.isEmpty()){
                androidx.compose.material.LinearProgressIndicator(
                    color = couleurprincipal,
                    modifier = Modifier.fillMaxWidth().padding(padding)
                )
            }else {
                LazyColumn(
//                    contentPadding = padding,
                    modifier = Modifier.padding(0.dp).fillMaxSize()
//                        .background(MaterialTheme.colorScheme.inverseSurface)
                ) {

                    items(pharmacielist.value) { item ->
                        androidx.compose.material3.Card(
                            modifier = Modifier
                                .padding(0.dp, 3.dp).fillMaxWidth()
                                .clickable {
//                            GlobalNav.navctl.navigate("${Route.DetailPharmacie}/${item.id}")
                                },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(5.dp)) {

                                    AsyncImage(
                                        model = item.image,
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxWidth().height(250.dp),
                                        contentScale = ContentScale.Crop,
                                    )
                                    Text(item.nom, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                                    ) {
                                        Text("Categorie :", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                                        Text(item.categorie,fontSize = 15.sp)
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                                    ) {
                                        Text("Region :", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                                        Text(item.region,fontSize = 15.sp)
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                                    ) {
                                        Text("Ville :", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                                        Text(item.ville,fontSize = 15.sp)
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                                    ) {
                                        Text("Localisation :", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                                        Text(item.localisation,fontSize = 15.sp)
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
                                    Divider(modifier = Modifier.fillMaxWidth())
                                    Spacer(modifier = Modifier.height(15.dp))
                                    Row {
                                        Spacer(modifier = Modifier.weight(1f))
                                        Button(
                                            colors = ButtonDefaults.buttonColors( backgroundColor = couleurprincipal),
                                            onClick = {
                                            GlobalNav.navctl.navigate("${Route.DetailPharmacie}/${item.id}")
                                        }) {
                                            Text("Consulter")
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(15.dp))
                                    Divider(modifier = Modifier.fillMaxWidth())
                                    Row(
                                        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.inverseOnSurface),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                                    ) {


                                        //bouton de boutique
                                        TextButton(
                                            onClick = {},
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                                            ) {
                                                Icon(imageVector = Icons.Default.Shop, contentDescription = "", tint = couleurprincipal)
                                                Text("Produit", color = couleurprincipal)
                                            }
                                        }
                                        //bouton de partage
                                        TextButton(
                                            onClick = {},
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                                            ) {
                                                Icon(imageVector = Icons.Default.Share, contentDescription = "", tint = couleurprincipal)
                                                Text("partager", color = couleurprincipal)
                                            }
                                        }
                                        //bouton de message
                                        TextButton(
                                            onClick = {
//                                        GlobalNav.navctl.navigate(Route.Chat)
//                                                val useruid = Firebase.auth.currentUser?.uid.toString()
//                                                val posteruid = item.uid
//
//                                                if(useruid+posteruid  in conversationIds.value){
//                                                    Log.d("con", "conversation trouve")
//                                                    GlobalNav.navctl.navigate("${Route.LoadConversation}/${useruid+posteruid.toString()}\"")
//                                                }else if(posteruid+useruid in conversationIds.value){
//                                                    Log.d("con", "conversation trouve")
//                                                    GlobalNav.navctl.navigate("${Route.LoadConversation}/${posteruid+useruid.toString()}\"")
//                                                }else{
//                                                    Log.d("con", "pas de conversation")
//                                                    GlobalNav.navctl.navigate("${Route.Conversation}/${posteruid.toString()}")
//                                                }
                                            },
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                                            ) {
                                                Icon(imageVector = Icons.Default.MailOutline, contentDescription = "", tint = couleurprincipal)
                                                Text("Ecrire", color = couleurprincipal)
                                            }
                                        }
                                    }

                                }
                            }

                        }
                    }
                }
            }
        }


    }

}