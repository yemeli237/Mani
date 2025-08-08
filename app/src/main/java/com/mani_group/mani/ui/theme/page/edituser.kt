package com.mani_group.mani.ui.theme.page

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.sharp.KeyboardArrowDown
import androidx.compose.material.icons.sharp.KeyboardArrowRight
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.mani_group.mani.Route
import com.mani_group.mani.data.AppUtil
import com.mani_group.mani.data.Utilisateur
import com.mani_group.mani.data.couleurprincipal
//import kotlinx.coroutines.flow.internal.NoOpContinuation.context
import java.io.ByteArrayOutputStream
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

@Composable
fun EditUser(navctl: NavHostController) {
    var nom by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var img by remember {
        mutableStateOf("")
    }
    var profession by remember {
        mutableStateOf("")
    }
    var numero by remember {
        mutableStateOf("")
    }
    var genre by remember {
        mutableStateOf("")
    }
    var opperateurbanque by remember {
        mutableStateOf("")
    }
    var context = LocalContext.current

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var base64String by remember { mutableStateOf("") }
    var isloading by rememberSaveable {
        mutableStateOf(false)
    }

    // Définir le lanceur pour sélectionner l'image
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    /////recuper le nom
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener(){
                nom = it.result.get("nom").toString()
//                    .split(" ")[0]
            }
    }
    ////recupere l'image
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener(){
                img = it.result.get("img").toString()

            }
    }
    ////recupere l'adresse
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener(){
                email = it.result.get("email").toString()

            }
    }

    ////recupere le numero
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener(){
                numero = it.result.get("contact").toString()

            }
    }
    ////recupere la profession
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener(){
                profession = it.result.get("profession").toString()

            }
    }

    ////recupere l'operateur de banque
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener(){
                opperateurbanque = it.result.get("opperateurbanque").toString()

            }
    }

    ////recupere l'operateur de banque
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener(){
                genre = it.result.get("genre").toString()

            }
    }
    selectedImageUri?.let { uri ->
        val context = LocalContext.current
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        img = com.mani_group.mani.bitmapToBase64(bitmap)
    }
    Scaffold(
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
                    Text("", textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 18.sp)
                },
                actions = {
                    TextButton(onClick = { update(
                        nom,
                        email,
                        "",
                        numero,
                        img,
                        genre,
                        opperateurbanque,
                        "",
                        profession,
                        context,
                        isloading = true
                    ) }) {
                        Text("Sauvegarder", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                    }
                }

                )
        }
    ){padding->
        Column(
            modifier = Modifier.padding(16.dp).fillMaxSize().verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Editer le profil", textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold,  fontSize = 25.sp)
            Card(
                modifier = Modifier.fillMaxWidth().height(320.dp).padding(16.dp)
            ) {
                Column {
                    Text("Photo de profil", modifier = Modifier.padding(100.dp, 0.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth().height(200.dp)
                        ) {
                            val inputStream = Base64.decode(img, Base64.DEFAULT)
                            val bitmap = BitmapFactory.decodeByteArray(inputStream, 0, inputStream.size)


                            if (bitmap == null){
                                androidx.compose.material.Text("")
                            }else{
                                Image(
                                    bitmap = bitmap.asImageBitmap(),
                                    contentDescription = null,
                                    modifier = Modifier.height(200.dp).fillMaxWidth(),
                                    contentScale = ContentScale.Crop,
                                )
                            }
                        }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(nom, fontSize = 18.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = {launcher.launch("image/*")}
                        ) {
                            Icon(imageVector = Icons.Default.Create, contentDescription = "")
                        }
                    }
                }

            }///
            OutlinedTextField(value = nom, onValueChange = {nom = it}, label = {
                Text("Nom",fontWeight = FontWeight.Bold,)
            }, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = couleurprincipal,
                focusedBorderColor = couleurprincipal,
                unfocusedBorderColor = couleurprincipal
            )
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = email, onValueChange = {email = it}, label = {
                Text("Adresse mail", fontWeight = FontWeight.Bold,)
            }, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = couleurprincipal,
                focusedBorderColor = couleurprincipal,
                unfocusedBorderColor = couleurprincipal
            )
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = numero, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), onValueChange = {numero = it}, label = {
                Text("contact", fontWeight = FontWeight.Bold,)
            }, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = couleurprincipal,
                focusedBorderColor = couleurprincipal,
                unfocusedBorderColor = couleurprincipal
            ),
            )
            var expanprofession by remember {
                mutableStateOf(false)
            }
            //
            TextButton(
                onClick = {expanprofession = true},
//                modifier = Modifier.padding(20.dp, 0.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        androidx.compose.material.Icon(
                            imageVector = Icons.Sharp.Person,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                        androidx.compose.material.Text(
                            "Votre profession : $profession",
                            color = Color(0xFF00bf63),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(10.dp, 0.dp)
                        )
                    }
                    androidx.compose.material.Icon(
                        imageVector = Icons.Sharp.KeyboardArrowDown,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            androidx.compose.material.DropdownMenu(
                expanded = expanprofession, onDismissRequest = {}
            ) {
                DropdownMenuItem(onClick = {expanprofession = false; profession = "Utilisateur"} ) {
                    androidx.compose.material.Text("Utilisateur")
                }
                DropdownMenuItem(onClick = {expanprofession = false; profession = "Livreurs"}) {
                    androidx.compose.material.Text("Livreurs")
                }
                DropdownMenuItem(onClick = {expanprofession = false; profession = "Corp medical"}) {
                    androidx.compose.material.Text("Corp medical")
                }
                DropdownMenuItem(onClick = {expanprofession = false; profession = "Minsante"}) {
                    androidx.compose.material.Text("Minsante")
                }
            }

        }
    }
}

fun update(
    nom:String,
    email:String,
    password:String,
    numero:String,
    img:String,
    genre:String,
    opperateurbanque:String,
    contact:String,
    profession:String,
//    onResult: (Boolean, String) -> Unit,
    context: android.content.Context,
    isloading:Boolean
){
    val userid = FirebaseAuth.getInstance().currentUser?.uid!!
    val User = Utilisateur(
        nom = nom,
        email = email,
        password = password,
        id = userid!!,
        numero = numero,
        img = img,
        genre = genre,
        opperateurbanque = opperateurbanque,
        profession = profession,
        contact = contact,
    )
    Firebase.firestore.collection("users").document(userid).set(User).addOnCompleteListener { dbtask ->
        if (dbtask.isSuccessful){

//            onResult(true, "Creation du compte reussi")
            AppUtil.showToast(context,  "Mise a jour avec succes")

        }else{
//            onResult(false, dbtask.exception?.localizedMessage?: "Une erreur c'est produite")
            AppUtil.showToast(context,  "Une erreur s'est produite")
        }
    }
}

fun bitmapToBase64(bitmap: Bitmap): String {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 10, outputStream) // Compression en PNG
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT) // Conversion en Base64
}