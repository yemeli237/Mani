package com.mani_group.mani.ui.theme.page.chat_interface

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.mani_group.mani.GlobalNav
import com.mani_group.mani.Route
import com.mani_group.mani.data.ConversationPreview
import com.mani_group.mani.data.Utilisateur
import com.mani_group.mani.data.couleurprincipal

@Composable
fun BanierUtilisateur(){

    val listUtilisateur = remember {
        mutableStateOf<List<Utilisateur>>(emptyList())
    }
    var load by remember {
        mutableStateOf(false)
    }


    var conversationIds = remember { mutableStateOf<List<String>>(emptyList()) }
    var nom by remember {
        mutableStateOf("")
    }
    //verifier la liste des messages
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(Firebase.auth.currentUser?.uid.toString())
//            .get()
            .addSnapshotListener { document, error ->
                if (document != null) {
                    if (document.exists()) {
                        val conversations = document.get("conversation") as? List<String> ?: emptyList()
                        conversationIds.value = conversations
                    }
                }
            }
    }
    val destinataires = remember { mutableStateOf<List<String>>(emptyList()) }

    for (id in conversationIds.value){
        if(id.isNotBlank()){
            val intervenant1  = id.substring(28)
            val intervenant2 = id.substring(0, 28)
            val receveur = if(intervenant1 == Firebase.auth.currentUser?.uid) intervenant2 else intervenant1
            destinataires.value += receveur
        }
    }
    //recuperer les utilisateur connecter
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val resultat = it.result.documents.mapNotNull { doc ->
                        doc.toObject(Utilisateur::class.java)
                    }
                    listUtilisateur.value = resultat
                    load = true
                }
            }
    }




   if(listUtilisateur.value.isEmpty()){
       LinearProgressIndicator(modifier = Modifier.fillMaxWidth(),color = couleurprincipal,)
   }else{
       LazyRow(modifier = Modifier.padding(10.dp)) {
           items(listUtilisateur.value){item->
               //photo de profil
               if(item.id in destinataires.value){
//                Log.d("destinataire", "BanierUtilisateur: ${item.id}")
               }else{
                   Column(horizontalAlignment = Alignment.CenterHorizontally) {
                       Card(
                           modifier = Modifier
                               .size(50.dp)
                               .border(
                                   width = 2.dp,
                                   color = Color(0xFF00bf63),
                                   shape = RoundedCornerShape(50.dp)
                               )
                               .clickable { GlobalNav.navctl.navigate("${Route.Conversation}/${item.id}") }
                           ,
                           shape = RoundedCornerShape(50.dp),
                           elevation = CardDefaults.cardElevation(4.dp),
                           colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
                       ) {
                           val inputStream = Base64.decode(item.img, Base64.DEFAULT)
                           val bitmap = BitmapFactory.decodeByteArray(inputStream, 0, inputStream.size)
                           if (bitmap == null){
                               Icon(
                                   imageVector = Icons.Sharp.Person,
                                   contentDescription = "",
                                   tint = MaterialTheme.colorScheme.onBackground,
                                   modifier = Modifier.size(50.dp)
                               )
                           }else{
                               Image(
                                   bitmap = bitmap.asImageBitmap(),
                                   contentDescription = null,
                                   modifier = Modifier.size(50.dp),
                                   contentScale = ContentScale.Crop
                               )
                           }


                       }
                       Text(
                           if(item.id == Firebase.auth.currentUser?.uid) "Moi" else item.nom,
                           color = Color(0xFF00bf63),
                           maxLines = 1,
                           overflow = TextOverflow.Ellipsis,
                           modifier = Modifier.width(50.dp)
                       )
                   }
               }
               Spacer(modifier = Modifier.width(8.dp))
           }
       }
   }
//    Spacer(modifier = Modifier.height(8.dp))
    if(load){
        TextButton(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("Voire plus ...") }
    }
}