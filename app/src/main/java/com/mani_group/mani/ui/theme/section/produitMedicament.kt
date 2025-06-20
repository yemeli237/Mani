package com.mani_group.mani.ui.theme.section

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.mani_group.mani.GlobalNav
import com.mani_group.mani.Route
import com.mani_group.mani.data.categorimodel
import com.mani_group.mani.data.couleurprincipal
import com.mani_group.mani.data.medmodl

@Composable
fun produitMedicament(modifier: Modifier = Modifier.padding(16.dp, 0.dp)){
    val medproduitlist = remember {
        mutableStateOf<List<medmodl>>(emptyList())
    }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("data")
            .document("stocke-med")
            .collection("medicament")
//            .whereEqualTo("categorie", "Doliprane")//faire un filtre
            .get().addOnCompleteListener(){
                if(it.isSuccessful){
                    val resultat = it.result.documents.mapNotNull { doc->
                        doc.toObject(medmodl::class.java)
                    }
                    medproduitlist.value = resultat.plus(resultat).plus(resultat).shuffled()


                }
            }
    }


    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp, 0.dp)
    ) {
        item {
            BarrRecherche()
            Spacer(modifier = Modifier.height(5.dp))
            sectionAlaUne(modifire = Modifier)
            Spacer(modifier = Modifier.height(5.dp))
            Categorie(modifire = Modifier)
            Spacer(modifier = Modifier.height(15.dp))
        }
        items(medproduitlist.value.chunked(2)){item->
            Row(
                modifier = Modifier.clickable {

                }
            ) {
                item.forEach {
                    androidx.compose.material3.Card(
                        modifier = Modifier.weight(1f)
                            .padding(8.dp)
                            .clickable {
                                GlobalNav.navctl.navigate("${Route.DetailProduit}/${it.id}")
                            },
                        shape = RoundedCornerShape(15.dp),
                        elevation = CardDefaults.cardElevation(3.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(

                        ) {
                            AsyncImage(
                                model = it.image.firstOrNull(),
                                contentDescription = "image produit",
                                modifier = Modifier
                                    .height(120.dp)
                                    .fillMaxWidth()
                                    .padding(3.dp)
                            )

                            Text(
                                it.nom,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(8.dp)
                            )
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(5.dp)
                                    .fillMaxWidth(),

                            ){
                                Text(it.prix+"fcf", fontSize = 14.sp, color = Color.Red,
                                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(it.prixnet+"fcf", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = couleurprincipal)
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