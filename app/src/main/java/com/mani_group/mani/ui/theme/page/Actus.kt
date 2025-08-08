package com.mani_group.mani.ui.theme.page

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.mani_group.mani.GlobalNav
import com.mani_group.mani.Route
import com.mani_group.mani.data.couleurprincipal
import com.mani_group.mani.data.dropmenu
import com.mani_group.mani.data.medmodl
import com.mani_group.mani.data.postdata
import com.mani_group.mani.ui.theme.navbar.Bottombarnav
import com.mani_group.mani.ui.theme.section.BarrRecherchepharmacie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Actus(navctl: NavHostController) {
    val posttlist = remember {
        mutableStateOf<List<postdata>>(emptyList())
    }
    var name by remember {
        mutableStateOf("")
    }
    var img by remember {
        mutableStateOf("")
    }
    var couleurtext by remember {
        mutableStateOf(Color.White)
    }
    var couleurbackground by remember {
        mutableStateOf(couleurprincipal)
    }
    var likelist = remember {
        mutableStateOf(emptyList<String>())
    }
    var conversationIds = remember { mutableStateOf<List<String>>(emptyList()) }


//    LaunchedEffect(Unit) {
//        Firebase.firestore.collection("data")
//            .document("posts")
//            .collection("post")
//            .get()
//            .addOnCompleteListener (){
//                if(it.isSuccessful){
//                    val resultat = it.result.documents.mapNotNull { doc->
//                        doc.toObject(postdata::class.java)
//                    }
//                    posttlist.value = resultat.plus(resultat).plus(resultat).shuffled()
//
//                }
//            }
//    }

    LaunchedEffect (Unit){
        Firebase.firestore.collection("data")
            .document("posts")
            .collection("post")
            .addSnapshotListener{ result, error ->
                if(result != null){
                    val resultat = result.documents.mapNotNull { doc->
                        doc.toObject(postdata::class.java)
                    }
                    posttlist.value = resultat

                }

            }
    }
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(Firebase.auth.currentUser?.uid.toString())
            .addSnapshotListener { document, error ->
                if (document != null) {
                    if (document.exists()) {
                        val conversations = document.get("conversation") as? List<String> ?: emptyList()
                        conversationIds.value = conversations
                    }
                }
            }
    }
    Scaffold(
        bottomBar = {
            Bottombarnav(navctl)
        },
        topBar = {

            TopAppBar(

                title = { Text("Actualite")  },
                actions = {
                    dropmenu()
                },
                colors =  TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00bf63),
                    titleContentColor = MaterialTheme.colorScheme.inverseOnSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.inverseOnSurface
                )

            )
        }

    ) {padding ->
        if (posttlist.value.isEmpty()){
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth().padding(padding))
        }else{
            LazyColumn(
                contentPadding = padding,
                modifier = Modifier.padding(0.dp).fillMaxSize().background(MaterialTheme.colorScheme.inverseSurface)
            ) {

                items(posttlist.value) { item ->
                    androidx.compose.material3.Card(
                        modifier = Modifier
                            .padding(0.dp, 3.dp).fillMaxWidth()
                            .clickable {
//                            GlobalNav.navctl.navigate("${Route.DetailProduit}/${it.id}")
                            },
                        shape = RoundedCornerShape(5.dp),
                        elevation = CardDefaults.cardElevation(3.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
                    ){
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(5.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                val db = FirebaseFirestore.getInstance()

                                db.collection("users")
                                    .document(item.uid)
                                    .get().addOnCompleteListener(){doc ->
                                        if(doc.isSuccessful()){
                                            img = doc.result.get("img").toString()
                                        }
                                    }
                                IconButton(
                                    onClick = {},
                                    modifier = Modifier.padding(5.dp),
                                ) {
                                    val inputStream = Base64.decode(img, Base64.DEFAULT)
                                    val bitmap = BitmapFactory.decodeByteArray(inputStream, 0, inputStream.size)
                                    if (bitmap == null){
                                        androidx.compose.material.Text("")
                                    }else{
                                        Image(
                                            bitmap = bitmap.asImageBitmap(),
                                            contentDescription = null,
                                            modifier = Modifier.size(60.dp).shadow(elevation = 2.dp, shape = RoundedCornerShape(50.dp)),
                                            contentScale = ContentScale.Crop,
                                        )
                                    }
                                }
                                Text(
                                    item.auteur,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(8.dp, 0.dp))
                            }

                            if(item.type == "Photo"){
                                if(item.description != ""){
                                    Text(item.description,
                                        maxLines = 2, fontSize = 18.sp,
                                        color = MaterialTheme.colorScheme.inverseSurface,
                                        fontWeight = FontWeight.SemiBold,
                                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                        modifier = Modifier.padding(10.dp, 0.dp)
                                    )
                                }
                                val inputStream = Base64.decode(item.contenu, Base64.DEFAULT)
                                val bitmap = BitmapFactory.decodeByteArray(inputStream, 0, inputStream.size)
                                if (bitmap == null){
                                    androidx.compose.material.Text("une erreu lors du chargement")
                                }else{
                                    Image(
                                        bitmap = bitmap.asImageBitmap(),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxWidth(),
                                        contentScale = ContentScale.Crop,
                                    )
                                }

                            }else if(item.type == "Text"){

                                Box(
                                    modifier = Modifier.height(300.dp).fillMaxWidth().background(stringToColor(item.couleurbackground))
                                ) {
                                    Text(item.contenu,
                                        fontSize = 30.sp,
                                        color = stringToColor(item.couleurtext),
                                        maxLines = 5,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }

                            }else if(item.type == "Video"){

                            }
//                        Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.inverseOnSurface),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceAround
                            ) {
                                //bouton de like
                                OutlinedButton (
                                    onClick = {

                                        //recupere la liste complete des like
                                        Firebase.firestore.collection("users")
                                            .document(Firebase.auth.currentUser?.uid.toString())
                                            .addSnapshotListener { document, error ->
                                                if (document != null) {
                                                    if (document.exists()) {
                                                        val like = document.get("like") as? List<String> ?: emptyList()
                                                        likelist.value = like
                                                    }
                                                }
                                            }


                                        if(item.id in likelist.value){
                                            Firebase.firestore.collection("data")
                                                .document("posts")
                                                .collection("post")
                                                .document(item.id)
                                                .update("like", item.like - 1)

                                            Firebase.firestore.collection("users")
                                                .document("${Firebase.auth.currentUser?.uid}")
                                                .update("like", FieldValue.arrayRemove(item.id))
                                                .addOnCompleteListener {
                                                    if(it.isSuccessful) {
                                                        Log.d("like", "like supprimé")
                                                    }
                                                }
                                        }else{
                                            Firebase.firestore.collection("data")
                                                .document("posts")
                                                .collection("post")
                                                .document(item.id)
                                                .update("like", item.like + 1)

                                            Firebase.firestore.collection("users")
                                                .document("${Firebase.auth.currentUser?.uid}")
                                                .update("like", FieldValue.arrayUnion(item.id))
                                                .addOnCompleteListener {
                                                    if(it.isSuccessful){
                                                        Log.d("like", "like ajouté")

                                                    }
                                                }


                                        }
                                    },
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                                    ) {
                                        Icon(imageVector = Icons.Default.ThumbUp, contentDescription = "", tint = couleurprincipal)
                                        Text(item.like.toString(), color = couleurprincipal)
                                    }
                                }
                                //bouton des commentaire
                                OutlinedButton (
                                    onClick = {
                                        GlobalNav.navctl.navigate("${Route.Commentaire}/${item.id}")
                                    },
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                                    ) {
                                        Icon(imageVector = Icons.Default.AddComment, contentDescription = "", tint = couleurprincipal)
                                        Text(item.comment.size.toString(), color = couleurprincipal)
                                    }
                                }
                                //bouton de partage
                                OutlinedButton(
                                    onClick = {},
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                                    ) {
                                        Icon(imageVector = Icons.Default.Share, contentDescription = "", tint = couleurprincipal)
                                        Text("...", color = couleurprincipal)
                                    }
                                }
                                //bouton de message
                                OutlinedButton(
                                    onClick = {
//                                        GlobalNav.navctl.navigate(Route.Chat)
                                        val useruid = Firebase.auth.currentUser?.uid.toString()
                                        val posteruid = item.uid

                                        if(useruid+posteruid  in conversationIds.value){
                                            Log.d("con", "conversation trouve")
                                            GlobalNav.navctl.navigate("${Route.LoadConversation}/${useruid+posteruid.toString()}\"")
                                        }else if(posteruid+useruid in conversationIds.value){
                                            Log.d("con", "conversation trouve")
                                            GlobalNav.navctl.navigate("${Route.LoadConversation}/${posteruid+useruid.toString()}\"")
                                        }else{
                                            Log.d("con", "pas de conversation")
                                            GlobalNav.navctl.navigate("${Route.Conversation}/${posteruid.toString()}")
                                        }
                                    },
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                                    ) {
                                        Icon(imageVector = Icons.Default.MailOutline, contentDescription = "", tint = couleurprincipal)
                                        Text("...", color = couleurprincipal)
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }


    }
}

