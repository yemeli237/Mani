package com.mani_group.mani.ui.theme.page.chat_interface

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.mani_group.mani.GlobalNav
import com.mani_group.mani.Route
import com.mani_group.mani.data.ConversationPreview
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@SuppressLint("SimpleDateFormat")
@Composable
fun Discussions(){
    var conversationIds = remember { mutableStateOf<List<String>>(emptyList()) }
    var nom by remember {
        mutableStateOf("")
    }
    val destinataires = remember { mutableStateOf<List<String>>(emptyList()) }
    val previews = remember { mutableStateOf<List<ConversationPreview>>(emptyList()) }
    var destinataireId by remember { mutableStateOf("") }

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

    if(conversationIds.value.isEmpty()){
        Text("Aucune discussion pour le moment")
    }else {


        LaunchedEffect(conversationIds.value) {
            destinataires.value = emptyList()
            previews.value = emptyList()




            conversationIds.value
                .filter { it.isNotBlank() }
                .forEach { idsms ->
                    Log.d("idsms", idsms)
                    val intervenant1  = idsms.substring(28)
                    val intervenant2 = idsms.substring(0, 28)
                    val receveur = if(intervenant1 == Firebase.auth.currentUser?.uid) intervenant2 else intervenant1

                    Firebase.firestore.collection("chat")
                        .document(idsms)
                        .collection("messages")
                        .orderBy("timestamp", Query.Direction.DESCENDING)
                        .limit(1)
                        .addSnapshotListener { snapshot, error ->
                            if (error != null) {
                                Log.e("Firestore", "Erreur de mise à jour : ${error.message}")
                                return@addSnapshotListener
                            }

                            val doc = snapshot?.documents?.firstOrNull()
                            destinataireId = doc?.getString("id") ?: return@addSnapshotListener
                            val dernierMessage = doc.getString("text") ?: ""
                            val timestamp = doc.getLong("timestamp") ?: 0

                            // Met à jour la liste des destinataires
                            if (!destinataires.value.contains(destinataireId)) {
                                destinataires.value = destinataires.value + destinataireId
                            }

                            // 🔁 Récupérer le nom de l'utilisateur
                            Firebase.firestore.collection("users")
                                .document(receveur)
                                .get()
                                .addOnSuccessListener { userDoc ->
                                    val name = userDoc.getString("nom") ?: "Inconnu"
                                    val img = userDoc.getString("img") ?: ""


                                    if (idsms.isNotBlank() && name.isNotBlank() && dernierMessage.isNotBlank()) {
                                        val preview = ConversationPreview(
                                            smsid = idsms,
                                            receiverUid = name,
                                            lastMessage = dernierMessage,
                                            timestamp = timestamp,
                                            img = img
                                        )

                                        // Remplace l'ancien aperçu s'il existe
                                        previews.value = previews.value
                                            .filterNot { it.smsid == idsms } + preview
                                    }
                                }
                        }
                }
        }

        //#####################################################

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            previews.value.forEach { preview ->
                if (preview.smsid.isNotBlank() && preview.lastMessage.isNotBlank()) {
                    Column(
                        modifier = Modifier.padding(16.dp).clickable {
                            GlobalNav.navctl.navigate("${Route.LoadConversation}/${preview.smsid.toString()}\"")
                        }

                    ) {

                        Row {
                            val inputStream = Base64.decode(preview.img, Base64.DEFAULT)
                            val bitmap = BitmapFactory.decodeByteArray(inputStream, 0, inputStream.size)
                            Card(
                                modifier = Modifier
                                    .size(50.dp)
                                    .border(
                                        width = 2.dp,
                                        color = Color.White,
                                        shape = RoundedCornerShape(50.dp)
                                    )
                                ,
                                shape = RoundedCornerShape(50.dp),
                                elevation = CardDefaults.cardElevation(1.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
                            ) {
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
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(text = preview.receiverUid.ifBlank { "Nom inconnu" }, style = MaterialTheme.typography.titleMedium, )
                                Row(
                                    modifier = Modifier.padding(top = 5.dp).fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = preview.lastMessage.ifBlank { "Message vide" },
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                    )
                                    Text(text = formatWhatsAppTime(preview.timestamp))
//                                    Text(text = SimpleDateFormat("HH:mm").format(Date(preview.timestamp)))
//                                    Text(text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(preview.timestamp)))
//                                    Log.d("Timestamp", "Valeur brute : ${preview.timestamp}")
                                }
                            }
                        }
                        Divider()
                    }
                }
            }
        }


    }


}

fun formatWhatsAppTime(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
    val hours = TimeUnit.MILLISECONDS.toHours(diff)
    val days = TimeUnit.MILLISECONDS.toDays(diff)

    return when {
        minutes < 1 -> "À l’instant"
        minutes < 60 -> "Il y a $minutes min"
        hours < 24 -> "Il y a $hours h"
        days == 1L -> {
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            "Hier à ${sdf.format(Date(timestamp))}"
        }
        days < 7 -> {
            val sdf = SimpleDateFormat("EEEE 'à' HH:mm", Locale.getDefault())
            sdf.format(Date(timestamp)) // Exemple : "lundi à 14:30"
        }
        else -> {
            val sdf = SimpleDateFormat("dd/MM/yyyy 'à' HH:mm", Locale.getDefault())
            sdf.format(Date(timestamp)) // Exemple : "12/07/2025 à 09:45"
        }
    }
}