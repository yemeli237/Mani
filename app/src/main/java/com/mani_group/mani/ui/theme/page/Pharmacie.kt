package com.mani_group.mani.ui.theme.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.google.firebase.firestore.firestore
import com.mani_group.mani.GlobalNav
import com.mani_group.mani.Route
import com.mani_group.mani.data.Pharmacie
import com.mani_group.mani.data.couleurprincipal
import com.mani_group.mani.data.dropmenu
import com.mani_group.mani.data.medmodl
//import com.mani_group.mani.data.pharmacie
import com.mani_group.mani.ui.theme.navbar.Bottombarnav
import com.mani_group.mani.ui.theme.section.BarrRecherche
import com.mani_group.mani.ui.theme.section.BarrRecherchepharmacie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pharmacie(navctl: NavHostController) {
    val pharmacielist = remember {
        mutableStateOf<List<Pharmacie>>(emptyList())
    }
    val id = ""

//    LaunchedEffect(Unit) {
//        Firebase.firestore.collection("data")
//            .document("pharmacie")
//            .collection("pharmacie")
//            .get().addOnCompleteListener(){
//                if(it.isSuccessful){
//                    val resultat = it.result.documents.mapNotNull { doc->
//                        doc.toObject(pharmacie::class.java)
//                    }
//                    pharmacielist.value = resultat.plus(resultat).plus(resultat)
//                }
//            }
//    }
fun recupererPharmaciesAvecId(onResult: (List<Pair<String, Pharmacie>>) -> Unit) {
    Firebase.firestore.collection("pharmacies")
        .get()
        .addOnSuccessListener { result ->
            val pharmacies = result.documents.map { document ->
                val id = document.id // 🔥 Récupérer l'ID unique
                val pharmacie = document.toObject(Pharmacie::class.java) // Convertir en objet Pharmacie
                if (pharmacie != null) id to pharmacie else null // Associer l'ID à chaque pharmacie
            }.filterNotNull() // Supprimer les éventuelles valeurs null
            onResult(pharmacies)
        }
        .addOnFailureListener { exception ->
            println("Erreur lors de la récupération des pharmacies et des IDs : ${exception.message}")
        }
}

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("pharmacies").limit(20)
            .get().addOnCompleteListener(){
                if (it.isSuccessful){
                    val resultat = it.result.documents.mapNotNull { doc->
                        val id = doc.id
                        doc.toObject(Pharmacie::class.java)
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
        topBar = {

            TopAppBar(

                title = { Text("Pharmacie")  },
                actions = {
                    dropmenu()
                },
                colors =  TopAppBarDefaults.topAppBarColors(
                    containerColor = couleurprincipal,
                    titleContentColor = MaterialTheme.colorScheme.inverseOnSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.inverseOnSurface
                )

            )
        }
    ) {padding ->
            LazyColumn(
                contentPadding = padding,
                modifier = Modifier.padding(16.dp).fillMaxSize()
            ) {
                item {
                    BarrRecherchepharmacie()
                    Spacer(modifier = Modifier.height(15.dp))
                }

                items(pharmacielist.value){item ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
//                            .height(320.dp)
                            .clickable {
                                GlobalNav.navctl.navigate("${Route.DetailPharmacie}/${item.nom}")
                            },
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {

                        Text(
                            item.nom,
                            modifier = Modifier.padding(8.dp),
                            fontSize = 18.sp, fontWeight = FontWeight.SemiBold,
                            color = couleurprincipal,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Row {
                            Text(
                                item.adresse,
                                modifier = Modifier.padding(8.dp).width(200.dp),
                                fontSize = 16.sp,
                                color = couleurprincipal,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                item.telephone[0],
                                modifier = Modifier.padding(8.dp),
                                fontSize = 16.sp,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                                )
                        }

                    }
                }
                item{

                    androidx.compose.material.Text(
                        "Sivous n'avez rien trouve, essayez de rechercher!!!",
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        textDecoration = TextDecoration.Underline
                    )
                }

            }



    }

}