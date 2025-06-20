package com.mani_group.mani.ui.theme.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
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
import com.mani_group.mani.data.couleurprincipal
import com.mani_group.mani.data.medmodl

@Composable
fun ResultProduit(navctl: NavHostController, medicament: String?) {
    val medproduitlist = remember {
        mutableStateOf<List<medmodl>>(emptyList())
    }

    var isloading by rememberSaveable {
        mutableStateOf(false)
    }
    val suggestlist = remember {
        mutableStateOf<List<medmodl>>(emptyList())
    }
    var suggest by remember {
        mutableStateOf("")
    }
    LaunchedEffect(Unit) {
        if (medicament != null) {
            Firebase.firestore.collection("data")
                .document("stocke-med")
                .collection("medicament")
//                .whereEqualTo("nom", medicament)//faire un filtre
                .whereGreaterThanOrEqualTo("nom", medicament)
                .whereLessThanOrEqualTo("nom", medicament + "\uf8ff")
                .get().addOnCompleteListener(){
                    if(it.isSuccessful){
                        val resultat = it.result.documents.mapNotNull { doc->
                            doc.toObject(medmodl::class.java)
                        }
                        medproduitlist.value = resultat
                        isloading = true

                    }
                }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
//                contentColor = couleurprincipal,
                navigationIcon = {
                    androidx.compose.material3.IconButton(onClick = {
                        navctl.navigate(Route.Home)
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "", tint = Color.White)
                    }
                },
                backgroundColor = couleurprincipal,
                title = {
                    Text("Resultat         $medicament", textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 18.sp)
                },

                )
        }
    ) { padding ->
        if (isloading){
            if (medproduitlist.value.isEmpty()){

                if (medicament != null) {
                    suggest = medicament.first().toString()
                }
//                LaunchedEffect(Unit) {
                    if (medicament != null) {
                        Firebase.firestore.collection("data")
                            .document("stocke-med")
                            .collection("medicament")
//                            .whereEqualTo("nom", "doliprane")//faire un filtre
//                            .whereGreaterThanOrEqualTo("nom", suggest)
                            .whereLessThanOrEqualTo("nom", suggest)
                            .get().addOnCompleteListener(){
                                if(it.isSuccessful){
                                    val resultat = it.result.documents.mapNotNull { doc->
                                        doc.toObject(medmodl::class.java)
                                    }
                                    suggestlist.value = resultat

                                }
                            }
                    }
//                }



                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {

                    if (suggestlist.value.isEmpty()){
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                . fillMaxSize()
                            ,
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(
                                "Dommage rien trouve",
                                modifier = Modifier.fillMaxSize(),
                                fontWeight = FontWeight.Thin,
                                color = Color.Red,
                                textAlign = TextAlign.Center
                            )
                        }

                    }else{
                        Column {
                            LazyColumn {
                                item {
                                    Column(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            . fillMaxSize()
                                        ,
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Card {
                                            Text(
                                                "Aucun medicament ne commence par ce nom",
                                                fontSize = 25.sp, color = Color.Red.copy(0.6f),
                                                textAlign = TextAlign.Center,
                                                fontWeight = FontWeight.SemiBold,
                                                fontFamily = FontFamily.Cursive
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(20.dp))
                                        Text("Note :  ", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                        Text("Essaie d'entrer les premier lettre correcte de ton medicament", textAlign = TextAlign.Center, fontSize = 18.sp, fontWeight = FontWeight.Thin)
                                        Spacer(modifier = Modifier.height(20.dp))
                                        Text("Rien trouve pour cette orthographe  :  $medicament", fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = Color.Red)
                                        Spacer(modifier = Modifier.height(10.dp))

                                        Text("Quelque suggestion:", fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = couleurprincipal)



                                    }
                                }
                                items(suggestlist.value.chunked(2)){item->
                                    Row(
                                        modifier = Modifier.padding(16.dp, 0.dp)
                                    ) {
                                        item.forEach {
                                            androidx.compose.material3.Card(
                                                modifier = Modifier.weight(1f)
                                                    .clickable {
                                                        GlobalNav.navctl.navigate("${Route.DetailProduit}/${it.id}")
                                                    }
                                                    .padding(8.dp),
                                                shape = RoundedCornerShape(12.dp),
                                                elevation = CardDefaults.cardElevation(4.dp),
                                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                                            ) {
                                                Column(

                                                ) {
                                                    AsyncImage(
                                                        model = it.image.firstOrNull(),
                                                        contentDescription = "image produit",
                                                        modifier = Modifier.height(120.dp)
                                                            .fillMaxWidth()
                                                    )

                                                    androidx.compose.material3.Text(
                                                        it.nom,
                                                        fontWeight = FontWeight.Bold,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis,
                                                        modifier = Modifier.padding(8.dp)
                                                    )
                                                    Row (
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        modifier = Modifier.padding(8.dp)
                                                            .fillMaxWidth(),

                                                        ){
                                                        androidx.compose.material3.Text(it.prix+"fcf", fontSize = 14.sp, color = Color.Red,
                                                            style = TextStyle(textDecoration = TextDecoration.LineThrough)
                                                        )
                                                        Spacer(modifier = Modifier.width(8.dp))
                                                        androidx.compose.material3.Text(it.prix+"fcf", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                                        Spacer(modifier = Modifier.weight(1f))
                                                        IconButton(
                                                            onClick = {}
                                                        ) {
                                                            Icon(
                                                                imageVector = Icons.Default.ShoppingCart,
                                                                contentDescription = ""
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        if(item.size == 1){
                                            Spacer(modifier = Modifier.weight(1f))
                                        }
                                    }
                                }

                            }
                        }
                    }
                    ////
                }

            }else{
                Column(
                    modifier = Modifier.padding(padding)
                ){
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                            .padding(16.dp, 0.dp)
                    ) {
                        items(medproduitlist.value.chunked(2)){item->
                            Row(

                            ) {
                                item.forEach {
                                    androidx.compose.material3.Card(
                                        modifier = Modifier.weight(1f)
                                            .clickable {
                                                GlobalNav.navctl.navigate("${Route.DetailProduit}/${it.id}")
                                            }
                                            .padding(8.dp),
                                        shape = RoundedCornerShape(12.dp),
                                        elevation = CardDefaults.cardElevation(4.dp),
                                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                                    ) {
                                        Column(

                                        ) {
                                            AsyncImage(
                                                model = it.image.firstOrNull(),
                                                contentDescription = "image produit",
                                                modifier = Modifier.height(120.dp)
                                                    .fillMaxWidth()
                                            )

                                            androidx.compose.material3.Text(
                                                it.nom,
                                                fontWeight = FontWeight.Bold,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = Modifier.padding(8.dp)
                                            )
                                            Row (
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(8.dp)
                                                    .fillMaxWidth(),

                                                ){
                                                androidx.compose.material3.Text(it.prix+"fcf", fontSize = 14.sp, color = Color.Red,
                                                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                androidx.compose.material3.Text(it.prix+"fcf", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                                Spacer(modifier = Modifier.weight(1f))
                                                IconButton(
                                                    onClick = {}
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.ShoppingCart,
                                                        contentDescription = ""
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                                if(item.size == 1){
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }

            }
        }else{
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Chargement...", fontSize = 18.sp, fontWeight = FontWeight.Thin)
            }
        }
    }
}