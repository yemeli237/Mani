package com.mani_group.mani.ui.theme.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.mani_group.mani.GlobalNav
import com.mani_group.mani.Route
import com.mani_group.mani.data.categorimodel
import com.mani_group.mani.data.couleurprincipal

@Composable
fun Categorie(modifire: Modifier.Companion) {
    val categorielist = remember {
        mutableStateOf<List<categorimodel>>(emptyList())
    }

//
//    LaunchedEffect(Unit) {
////        url = "https://res.cloudinary.com/dcnnsq1xw/image/upload/v1745581244/4_v368dr.png"
//        Firebase.firestore.collection("data")
//            .document("stock")
//            .collection("categories")
//            .get().addOnCompleteListener(){
//                if(it.isSuccessful){
//                   val resultat = it.result.documents.mapNotNull { doc->
//                       doc.toObject(categorimodel::class.java)
//                   }
//                    categorielist.value = resultat
//
//                }
//            }
//    }
    LaunchedEffect(Unit){
        Firebase.firestore.collection("data")
            .document("stock")
            .collection("categories")
            .addSnapshotListener{snapshot,error->
                if(snapshot!=null){
                    val resultat = snapshot.documents.mapNotNull { doc->
                        doc.toObject(categorimodel::class.java)
                    }
                    categorielist.value = resultat
                }
            }
    }



    if(categorielist.value.isEmpty()){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Chargement")
        }
    }else{
        Text("Categories",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp, 0.dp)
        )
    }
    LazyRow (
        modifier = Modifier.padding(16.dp, 0.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        content = {
        items(categorielist.value){item: categorimodel ->
            categoriitem(categorie = item)
        }
    })
}

@Composable
fun categoriitem(categorie:categorimodel){
    Card (
        onClick = {
            GlobalNav.navctl.navigate("${Route.CategoriParProduit}/${categorie.id}")
        },
        modifier = Modifier.size(100.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = couleurprincipal)

    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = categorie.image,
                contentDescription = "Image",
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(categorie.nom, textAlign = TextAlign.Center, color = Color.White, fontWeight = FontWeight.SemiBold)
        }
    }
}