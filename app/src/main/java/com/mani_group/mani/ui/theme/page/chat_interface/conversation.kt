package com.mani_group.mani.ui.theme.page.chat_interface

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.mani_group.mani.GlobalNav
import com.mani_group.mani.Route
import com.mani_group.mani.data.Message
import com.mani_group.mani.data.Utilisateur
import com.mani_group.mani.data.couleurprincipal
import com.mani_group.mani.data.medmodl
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//import com.mani_group.mani.data.message


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Conversation(navctl: NavHostController, id: String?) {
    var message by remember {
        mutableStateOf("")
    }

    val destinataire = mutableStateOf<List<Utilisateur>>(emptyList())

    var uid by remember {
        mutableStateOf("")
    }
    var load by remember {
        mutableStateOf(false)
    }
    var destinatairimg by remember {
        mutableStateOf("")
    }
    var log by remember {
        mutableStateOf("")
    }
//    var conversion = mutableListOf<List<message>>(emptyList())
    val conversion = remember {
        mutableStateOf<List<Message>>(emptyList())
    }
    val medproduitlist = remember {
        mutableStateOf<List<medmodl>>(emptyList())
    }

    LaunchedEffect(key1 = id) {
        if (id != null) {
            Firebase.firestore.collection("users")
                .document(id)
                .get()
                .addOnSuccessListener { document ->
                    val user = document.toObject(Utilisateur::class.java)
                    if (user != null) {
                        destinataire.value = listOf(user)
                        load = true
                    } else {
                        load = false
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Erreur de chargement : ${e.message}")
                    load = false
                }
        }
    }



    Scaffold(
        bottomBar = {
            saisir(
                message = message,
                onMessageChange = { message = it },
                log = message,
                id = id
            )
        },
        topBar = {

            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        GlobalNav.navctl.popBackStack()
                    /*TODO*/ }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "", tint = MaterialTheme.colorScheme.inverseOnSurface)
                    }
                },
                title = {

                    TextButton (onClick = {}) {
                        val inputStream = Base64.decode(destinataire.value.firstOrNull()?.img.toString(), Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(inputStream, 0, inputStream.size)
                        Card(
                            modifier = Modifier.size(50.dp)
                                .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(50.dp))
//                                .clickable { GlobalNav.navctl.navigate("${Route.Conversation}/${item.id}") }
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
                        Text(
                            if(destinataire.value.firstOrNull()?.nom != null)"${destinataire.value.firstOrNull()?.nom}" else "",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            fontSize = 25.sp,
                            modifier = Modifier.padding(10.dp, 0.dp)
                        )

                    }
                        },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {

                        Icon(imageVector = Icons.Outlined.Info, contentDescription = "", tint = MaterialTheme.colorScheme.inverseOnSurface)

                    }
                },
                colors =  TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00bf63),
                    titleContentColor = MaterialTheme.colorScheme.inverseOnSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.inverseOnSurface
                )

            )
        }
    ) {padding ->


        Column(
            modifier = Modifier.padding(padding),
        ) {


            LaunchedEffect (Unit){
                Firebase.firestore.collection("chat")
                    .document("${Firebase.auth.currentUser?.uid}$id")
                    .collection("messages")
                    .orderBy("timestamp", Query.Direction.ASCENDING)
                    .addSnapshotListener{snapshot, error ->//verifier a charque foie l'etat et actualiser
                        if (error != null) {
                            Log.e("Firestore", "Erreur de chargement : ${error.message}")
                            return@addSnapshotListener
                        }
                        val messages = snapshot?.documents?.mapNotNull { it.toObject(Message::class.java) } ?: emptyList()
                        conversion.value = messages

                    }
            }
            val listState = rememberLazyListState()//etat du scrolle
            LaunchedEffect(conversion.value.size) {//aller au denier row a chaque foi
                if (conversion.value.isNotEmpty()) {
                    listState.animateScrollToItem(conversion.value.lastIndex)
                }
            }

            LazyColumn(
                state = listState,
                reverseLayout = false
            ){
            items(
                items = conversion.value,
            ){message ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement = if (message.uid == Firebase.auth.currentUser?.uid) Arrangement.End else Arrangement.Start
                ) {
                    Card(
//                            shape = RoundedCornerShape(1)
                            colors = CardDefaults.cardColors(
                                containerColor = if(message.uid == Firebase.auth.currentUser?.uid) Color.Gray else couleurprincipal,
                                contentColor = Color.White
                            )
                        ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = message.text,
                                modifier = Modifier.padding(5.dp),
                                fontSize = 18.sp,
                                fontFamily = FontFamily.Serif
                            )
                            Text(
                                text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(message.timestamp)),
                                modifier = Modifier.padding(0.dp, 15.dp, 5.dp, 0.dp),
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Serif)
                        }
                        }
                }

            }
            }

        }

    }
}


@Composable
fun saisir(
    message: String,
    onMessageChange: (String) -> Unit,
    log: String,
    id: String?
) {
    NavigationBar(
        modifier = Modifier.imePadding(),
        containerColor = MaterialTheme.colorScheme.surface,
        ) {
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth().padding(15.dp, 0.dp, 15.dp, 0.dp),
                value = message,
                onValueChange = onMessageChange,
                shape = RoundedCornerShape(20.dp),
                singleLine = false,
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = MaterialTheme.colorScheme.onSurface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    IconButton(
                        enabled = message.isNotEmpty(),
//                        ???????

                        onClick = {
                            val new_message = Message(
                                text = message,
                                id = "$id",
                                uid = "${Firebase.auth.currentUser?.uid}",
                                smsid = "${Firebase.auth.currentUser?.uid}$id",
                                timestamp = System.currentTimeMillis()
                            )
                            val timestamp = System.currentTimeMillis()
                            if(new_message.text != "" ){
                                Firebase.firestore.collection("chat")
                                    .document(new_message.smsid)
                                    .collection("messages")
                                    .add(new_message)
                                    .addOnCompleteListener { receiver->
                                        Firebase.firestore.collection("chat")
                                            .document(new_message.smsid)
                                            .update("timestamp", timestamp)
                                        Firebase.firestore.collection("users")
                                            .document("$id")
                                            .update("conversation", FieldValue.arrayUnion(new_message.smsid))

                                        Firebase.firestore.collection("users")
                                            .document(Firebase.auth.currentUser?.uid!!)
                                            .update("conversation", FieldValue.arrayUnion(new_message.smsid))

                                    }
                            }
                            onMessageChange("")
                        }

                    ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = "")
                    }
                }
            )

        }
    }
}