package com.mani_group.mani.ui.theme.page

import android.annotation.SuppressLint
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.mani_group.mani.Route
import com.mani_group.mani.data.AppUtil
import com.mani_group.mani.data.couleurprincipal
import com.mani_group.mani.data.medmodl

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailPage(navctl: NavHostController, produit: String) {

    var produits by remember {
        mutableStateOf(medmodl())
    }

//    LaunchedEffect(Unit) {
//        Firebase.firestore.collection("data")
//            .document("stocke-med")
//            .collection("medicament")
//            .document(produit).get()
//            .addOnCompleteListener {
//                if(it.isSuccessful){
//                    var resultat = it.result.toObject(medmodl::class.java)
//                    if (resultat != null) {
//                        produits = resultat
//                    }
//                }
//            }
//    }
    LaunchedEffect(Unit){
        Firebase.firestore.collection("data")
            .document("stocke-med")
            .collection("medicament")
            .document(produit)
            .addSnapshotListener { snapshot, error ->
                if (snapshot != null) {
                    var resultat = snapshot.toObject(medmodl::class.java)
                    if (resultat != null) {
                        produits = resultat
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
                    Text("Detail", textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 18.sp)
                },

                )
        }
    ) {padding->

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "${produits.nom}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(bottom = 8.dp)
                modifier = Modifier.padding(16.dp, 0.dp),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(

            ) {
                val pageetat = rememberPagerState {
                    produits.image.size
                }
                HorizontalPager(
                    state = pageetat,
                    pageSpacing = 16.dp,
                    modifier = Modifier.padding(10.dp).height(160.dp)
                ) {
                    AsyncImage(
                        model = produits.image.get(it),
                        contentDescription = "image de ref",
                        modifier = Modifier.fillMaxWidth()
                            .height(220.dp)
                            .clip(
                                RoundedCornerShape(16.dp)
                            )
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp, 0.dp)
                    .fillMaxWidth(),

                ) {
                Text(
                    produits.prix + "fcf", fontSize = 16.sp, color = Color.Red,
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(produits.prixnet + "fcf", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {}
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(produits.like.toString())
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = ""
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().height(50.dp).padding(16.dp, 0.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = couleurprincipal,
                    contentColor = Color.White
                )
            ) { Text(text = "Sauvegarder", fontWeight = FontWeight.Bold, fontSize = 18.sp) }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().height(50.dp).padding(16.dp, 0.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = couleurprincipal,
                    contentColor = Color.White
                )
            ) { Text(text = "Passer la commande", fontWeight = FontWeight.Bold, fontSize = 18.sp) }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Pharmacie ${produits.pharmacie}", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(16.dp, 0.dp))
            Spacer(modifier = Modifier.height(8.dp))

            Text("Description: ", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(16.dp, 0.dp), color = couleurprincipal)
            Spacer(modifier = Modifier.height(8.dp))

            Text(produits.description, fontSize = 16.sp,modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Thin)
            Spacer(modifier = Modifier.height(8.dp))

            if (produits.autre.isNotEmpty()) {
                Text("Autre: ", fontSize = 20.sp, color = couleurprincipal, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(16.dp, 0.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))

            produits.autre.forEach { (cle, valeur) ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(5.dp).padding(16.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("$cle : ", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = couleurprincipal)
                    Text(valeur, fontSize = 16.sp)
                }
            }


        }
    }






}