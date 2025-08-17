package com.mani_group.mani.ui.theme.page.chat_interface

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Info
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.trace
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.mani_group.mani.GlobalNav
import com.mani_group.mani.data.Message
import com.mani_group.mani.data.Pharmacie
import com.mani_group.mani.data.postdata
import com.mani_group.mani.ui.theme.page.post
import java.nio.file.WatchEvent
import kotlin.collections.emptyList
import kotlin.collections.mutableSetOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Commentaire(
    post: String?
) {
//    var post = "8LC8UXyvFAxv6pIQXkVJ"
    var commentaires = remember {
        mutableStateOf(emptyList<String>())
    }
    var message by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("data")
            .document("posts")
            .collection("post")
            .document("$post")
            .addSnapshotListener { document, error ->
                if (document != null) {
                    if (document.exists()) {
                        val comment = document.get("comment") as? List<String> ?: emptyList()
                        commentaires.value = comment
                    }
                }
            }
    }


    Scaffold(
        bottomBar = {
            saisircomment(
                message = message,
                onMessageChange = { message = it },
                post = post
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
                    Text("Serction commentaire")
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
    ) {paddingValues ->
        if (commentaires.value.isEmpty()){
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }else{
            Column(modifier = Modifier.padding(paddingValues)) {
                LazyColumn {
                    items(commentaires.value){item ->

                        androidx.compose.material3.Card(
                            modifier = Modifier
                                .padding(10.dp, 3.dp).fillMaxWidth()
                                .clickable {
//                            GlobalNav.navctl.navigate("${Route.DetailProduit}/${it.id}")
                                },
                            shape = RoundedCornerShape(3.dp),
                            elevation = CardDefaults.cardElevation(1.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
                        ){
                            Text(
                                text = "<-$item->",
                                style = TextStyle(
                                    color = Color.Gray,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                ),
                                modifier = Modifier.padding(16.dp)
                            )
                        }


                    }
                }

            }
        }

    }
}





@Composable
fun saisircomment(
    message: String,
    onMessageChange: (String) -> Unit,
    post: String?
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
                placeholder = {Text("Ajouter un commentaire")},
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
                            try {
                                Firebase.firestore.collection("data")
                                    .document("posts")
                                    .collection("post")
                                    .document("$post")
                                    .update("comment", FieldValue.arrayUnion(message))
                                    .addOnCompleteListener {
                                        if(it.isSuccessful){
                                            Log.d("comment", "commentaire ajouté")

                                        }
                                    }
                            }catch (e: Exception){
                                Log.d("com", "$e")
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