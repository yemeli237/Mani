package com.mani_group.mani.ui.theme.page.maniai

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.Firebase
//import com.google.firebase.ai.GenerativeModel
//import com.google.firebase.ai.ai
//import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.auth.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.mani_group.mani.GlobalNav
import com.mani_group.mani.R
import com.mani_group.mani.Route
import com.mani_group.mani.data.Message
import com.mani_group.mani.data.couleurprincipal
import com.mani_group.mani.ui.theme.page.chat_interface.Saisir
import com.mani_group.mani.ui.theme.page.chat_interface.saisir
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.content.TextContent
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBot(){

    var message by remember {
        mutableStateOf("")
    }
    var resultat by remember {
        mutableStateOf("")
    }
    val conversion = remember {
        mutableStateOf<List<Message>>(emptyList())
    }

//    AIzaSyA1RUZAk1nM4Sa_OfPcPc_c8aPNh8gt7Nw


    suspend fun RequeteGemini(prompt: String): String {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val response: String = client.post(
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=${const().apikey}") {
            contentType(ContentType.Application.Json)
            setBody(
                mapOf(
                    "contents" to listOf(
                        mapOf("parts" to listOf(mapOf("text" to prompt)))
                    )
                )
            )
        }.bodyAsText()

        return response
    }



    Scaffold(
        bottomBar = {
            SaisirAI(
                message = message,
                onMessageChange = { message = it },
                log = message,
                id = "AI"
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
                        Card(
                            modifier = Modifier
                                .size(50.dp)
                                .border(
                                    width = 2.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(50.dp)
                                )
//                                .clickable { GlobalNav.navctl.navigate("${Route.Conversation}/${item.id}") }
                            ,
                            shape = RoundedCornerShape(50.dp),
                            elevation = CardDefaults.cardElevation(1.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
                        ) {

                            Image(
                                painter = painterResource(R.drawable.ai),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Text(
                            "Mani AI",
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
        Column (modifier = Modifier.padding(padding)){

            LaunchedEffect (Unit){
                Firebase.firestore.collection("users")
                    .document(Firebase.auth.currentUser?.uid.toString())
                    .collection("conversationIA")
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = if (message.id == "AI") Arrangement.End else Arrangement.Start
                    ) {
                        Card(
//                            shape = RoundedCornerShape(5),
                            colors = CardDefaults.cardColors(
                                containerColor = if(message.id == "AI") Color.Gray else couleurprincipal,
                                contentColor = Color.White
                            ),
                            modifier = Modifier.padding(8.dp, 0.dp)
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
                                    text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(
                                        Date(message.timestamp)
                                    ),
                                    modifier = Modifier.padding(0.dp, 15.dp, 5.dp, 0.dp),
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Serif)
                            }
                        }
                    }

                }
            }

//            Text(conversion.value.toString())
        }
    }


}



@Composable
fun SaisirAI(
    message: String,
    onMessageChange: (String) -> Unit,
    log: String,
    id: String?
) {
    var reponseIA by remember {
        mutableStateOf("")
    }



    suspend fun RequeteGemini(prompt: String): String {
        val recentMessages = Firebase.firestore.collection("users")
            .document(Firebase.auth.currentUser?.uid.toString())
            .collection("conversationIA")
            .orderBy("timestamp")
            .limit(10) // Les 3 derniers
            .get()
            .await()
            .map { doc ->
                Message(doc["id"] as String, doc["text"] as String,
                    (doc["timestamp"] as Long).toString(), doc["uid"] as String
                )
            }
        val prompt = buildString {
            append("Voici la conversation :\n")
            recentMessages.forEach { append("$it\n") }
            append("Utilisateur : $message\n")
            append("Assistant :")
        }


        val instruction = "Tu es un médecin expérimenté. Ne réponds qu'aux questions médicales avec précision,clarté et sans founir un long text:"
        val formattedPrompt = "$instruction $prompt"

        return try {
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json(Json { ignoreUnknownKeys = true })
                }
            }

            val response: String = client.post(
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=${const().apikey}"
            ) {
                contentType(ContentType.Application.Json)
                setBody(
                    mapOf(
                        "contents" to listOf(
                            mapOf("parts" to listOf(mapOf("text" to formattedPrompt)))
                        )
                    )
                )
            }.bodyAsText()

            response
        } catch (e: Exception) {
            Log.d("GeminiAI", e.message.toString())
            ""
        }
    }
    var isLoading by remember { mutableStateOf(false) }

    NavigationBar(
        modifier = Modifier.imePadding(),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp, 0.dp, 15.dp, 0.dp),
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
                placeholder = {
                    Text(if(isLoading) " Chargement..." else "Demandez moi quelque chose")
                },
                trailingIcon = {
                    IconButton(
                        enabled = message.isNotEmpty(),
//                        ???????

                        onClick = {
                            isLoading = true
                            val new_message = id?.let {
                                Message(
                                    text = message,
                                    id = it,
                                    uid = "${Firebase.auth.currentUser?.uid}",
                                    smsid = "${Firebase.auth.currentUser?.uid}$id",
                                    timestamp = System.currentTimeMillis()
                                )
                            }
                            if (new_message != null) {
                                Firebase.auth.currentUser?.uid?.let {
                                    Firebase.firestore.collection("users")
                                        .document(it)
                                        .collection("conversationIA")
                                        .add(new_message)
                                        .addOnCompleteListener { sms->
                                            Log.d("sms", "SaisirAI: message envoyer")
                                            try {
                                                CoroutineScope(Dispatchers.IO).launch {
                                                    val reponse = RequeteGemini("$message")
                                                    withContext(Dispatchers.Main){
                                                        val resultat = JSONObject(reponse)
                                                            .getJSONArray("candidates")
                                                            .getJSONObject(0)
                                                            .getJSONObject("content")
                                                            .getJSONArray("parts")
                                                            .getJSONObject(0)
                                                            .getString("text")
                                                        new_message.text = resultat
                                                        new_message.id = "model"
                                                        new_message.timestamp = System.currentTimeMillis()
                                                        Firebase.firestore.collection("users")
                                                            .document(it)
                                                            .collection("conversationIA")
                                                            .add(new_message)
                                                            .addOnCompleteListener { sms->
                                                                Log.d("sms", "SaisirAI: message de l'ia  envoyer")
                                                                isLoading = false
                                                            }

                                                        Log.d("GeminiAI", resultat+new_message.text)
                                                    }
                                                }
                                            } catch (e: Exception) {
                                                Log.d("GeminiAI", e.message.toString())
                                            }



                                        }
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