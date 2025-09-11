package com.mani_group.mani.ui.theme.page

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.mani_group.mani.GlobalNav
import com.mani_group.mani.Route
import com.mani_group.mani.data.couleurprincipal
import com.mani_group.mani.data.etsmodel
import com.mani_group.mani.data.medmodl
import com.mani_group.mani.ui.theme.page.geoloc.AllerpharmacieViewModel

@SuppressLint("CommitPrefEdits")
@Composable
fun CommandProduit(

    navctl: NavHostController,
    produit: String?,
    viewModel: AllerpharmacieViewModel = viewModel(),
) {
    var produits by remember {
        mutableStateOf(medmodl())
    }
//    var produit = "bWxmxghowjWqWGYR6vQz"
    var quantite by remember {
        mutableStateOf(1)
    }
    var montan by remember {
        mutableStateOf(0)
    }
    var somme by remember {
        mutableStateOf(0)
    }
    var frais by remember {
        mutableStateOf(0)
    }

    var info by remember {
        mutableStateOf(false)
    }
    var addfeed by remember {
        mutableStateOf(false)
    }
    var numero by remember {
        mutableStateOf("")
    }

    var name by remember {
        mutableStateOf("")
    }
    var userlikelist = remember { mutableStateOf<List<String>>(emptyList()) }
    var openallert by remember {
        mutableStateOf(false)
    }
    var oprendromemodemenu by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val store = context.getSharedPreferences("localisation", Context.MODE_PRIVATE)

    LaunchedEffect(Unit) {
        try {
            Firebase.firestore.collection("users")
                .document(Firebase.auth.currentUser?.uid.toString())
                .addSnapshotListener { document, error ->
                    if (document != null) {
                        if (document.exists()) {
                            val content = document.get("like") as? List<String> ?: emptyList()
                            userlikelist.value = content
                        }
                    }
                }
        }catch (e:Exception){
            Log.d("like", "erreur: ${userlikelist}")
        }
    }

    LaunchedEffect(Unit){
        Firebase.firestore.collection("data")
            .document("stocke-med")
            .collection("medicament")
            .document("${produit}")
            .addSnapshotListener { snapshot, error ->
                if (snapshot != null) {
                    var resultat = snapshot.toObject(medmodl::class.java)
                    if (resultat != null) {
                        produits = resultat
                    }
                }
            }
    }
    LaunchedEffect(Unit) {
        try {
            Firebase.firestore.collection("ets")
                .document(produits.idPharmacie)
                .get()
                .addOnSuccessListener { document ->
                    val ets = document.toObject(etsmodel::class.java)
                    if (ets != null) {
                        viewModel.latpharmacie = ets.lat
                        viewModel.longpharmacie = ets.long
                        store.edit().putLong("long", ets.long.toLong()).apply()
                        store.edit().putLong("lat", ets.lat.toLong()).apply()
                        store.edit().putString("nom", produits.pharmacie).apply()
                    } else {

                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Erreur de chargement : ${e.message}")
                }
        }catch (e: Exception){
            Log.e("Firestore", "Erreur de chargement : ${e.message}")
        }

    }
    Log.d("Store", "donnes stocke${store.getString("nom", "")}, nom pharmacie : ${produits.pharmacie}")

    Scaffold(
        floatingActionButton = {
            Column {
                androidx.compose.material3.IconButton(
                    onClick = {
//                    openmenu = true
                        openallert = true
                    },
                    modifier = Modifier.size(50.dp),
                    colors = IconButtonDefaults.iconButtonColors(containerColor = couleurprincipal, contentColor = Color.White)
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Filled.AddLocation,
                        contentDescription = "Action button",
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                androidx.compose.material3.IconButton(
                    enabled = addfeed,
                    onClick = {
//                    openmenu = true
                        GlobalNav.navctl.navigate(Route.ItineraireLivraison)
                    },
                    modifier = Modifier.size(50.dp),
                    colors = IconButtonDefaults.iconButtonColors(containerColor = couleurprincipal, contentColor = Color.White)
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Filled.DeliveryDining,
                        contentDescription = "Action button",
                    )
                }
            }
        },
        topBar = {
            TopAppBar(
//                contentColor = couleurprincipal,
                navigationIcon = {
                    androidx.compose.material3.IconButton(onClick = {
                        navctl.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "", tint = Color.White)
                    }
                },
                backgroundColor = couleurprincipal,
                title = {
                    Text("Commander", textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 18.sp)
                },

                )

        }
    ) {padding->

        Column(
            modifier = Modifier
                .fillMaxSize()
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
                    modifier = Modifier
                        .padding(10.dp)
                        .height(160.dp)
                ) {
                    AsyncImage(
                        model = produits.image.get(it),
                        contentDescription = "image de ref",
                        modifier = Modifier
                            .fillMaxWidth()
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
                modifier = Modifier
                    .padding(16.dp, 0.dp)
                    .fillMaxWidth(),

                ) {
//                Text(
//                    produits.prix + "fcf", fontSize = 16.sp, color = Color.Red,
//                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
//                )
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
                            contentDescription = "",
                            tint = if (produit in userlikelist.value) Color.Green else Color.Gray
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text("Quantite",fontWeight = FontWeight.Bold)
                IconButton(onClick = {quantite = if(quantite <= 1) 1 else quantite - 1}) {
                    Icon(imageVector = Icons.Default.RemoveCircleOutline, contentDescription = "reduire", tint = Color.Red)
                }
                Text("$quantite",fontWeight = FontWeight.Bold)
                IconButton(onClick = {quantite = quantite +1}) {
                    Icon(imageVector = Icons.Default.AddCircle, contentDescription = "reduire", tint = couleurprincipal)
                }
                try {
                    somme = quantite*produits.prixnet.toInt()
                }catch (e: Exception){
                    Log.d("prix", "$e")
                }
                Text("total : ${somme}", fontWeight = FontWeight.Bold)

            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Net a payer : ${somme+frais}XAF", fontWeight = FontWeight.Bold)
                Text("Livraison : ${frais}XAF", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Livraison ?", fontWeight = FontWeight.Bold)
                if(addfeed){
                    IconButton(onClick = {addfeed = false; frais = if(frais > 500) frais - 500 else 0}) {
                        Icon(imageVector = Icons.Default.ToggleOn, contentDescription = "ajourter les frais de livraison", tint = couleurprincipal, modifier = Modifier.size(50.dp))
                    }
                }else{
                    IconButton(onClick = {addfeed = true; frais = if (frais == 0) 0 + 500 else 0}) {
                        Icon(imageVector = Icons.Default.ToggleOff, contentDescription = "retirer les frais de livraison", tint = Color.Red,modifier = Modifier.size(50.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Numero de payement")
                OutlinedTextField(value = numero, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), onValueChange = {numero = it}, label = {
                    Text("numero OM ou MOMO", fontWeight = FontWeight.Bold,)
                }, shape = RoundedCornerShape(10.dp),  colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = couleurprincipal,
                    focusedBorderColor = couleurprincipal,
                    unfocusedBorderColor = couleurprincipal
                ),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(

                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(16.dp, 0.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = couleurprincipal,
                    contentColor = Color.White
                )
            ) { Text(text = "Valider", fontWeight = FontWeight.Bold, fontSize = 18.sp) }


            Spacer(modifier = Modifier.height(8.dp))

            if(info){
                TextButton(onClick = {info = false}) {
                    Text("Masquer les information")
                }
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .padding(16.dp, 0.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("$cle : ", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = couleurprincipal)
                        Text(valeur, fontSize = 16.sp)
                    }
                }
            }else{
                TextButton(onClick = {info = true}) {
                    Text("Voire les information")
                }
            }


        }
        if(openallert){
            AlertDialog(
                onDismissRequest = {
                    openallert = false
                },
                confirmButton = {
                    OutlinedButton(
                        onClick = {oprendromemodemenu = true},
                        shape = RoundedCornerShape(10.dp),) {
                        Text("Mode", color = couleurprincipal)
                        Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = "", tint = couleurprincipal)
                    }
                    DropdownMenu(expanded = oprendromemodemenu, onDismissRequest = {
                        openallert = false
                    }) {
                        for (item in viewModel.transportModes){
                            DropdownMenuItem(onClick = {
                                oprendromemodemenu=false
                                viewModel.mode = item
                                store.edit().putString("mode", item).apply()
                                GlobalNav.navctl.navigate(Route.AllePharmacie)
                            }) {
                                Text(item,color = couleurprincipal.copy(0.5f))
                            }
                        }
                    }
                },
                title = {
                    androidx.compose.material.Text("Anonce", textAlign = TextAlign.Center)
                },
                dismissButton = {
                    androidx.compose.material3.OutlinedButton (
                        onClick = {
                            openallert = false
                        },
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        androidx.compose.material.Text(
                            "Annuler",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = couleurprincipal.copy(0.5f)
                        )
                    }
                },
                text = {
                    androidx.compose.material.Text("Quel est votre mode de transport")
                }
            )


        }
    }
}