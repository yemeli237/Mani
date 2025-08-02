package com.mani_group.mani.ui.theme.page

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.github.skydoves.colorpicker.compose.ImageColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.mani_group.mani.GlobalNav
import com.mani_group.mani.R
import com.mani_group.mani.Route
import com.mani_group.mani.data.AppUtil
import com.mani_group.mani.data.couleurprincipal
import com.mani_group.mani.data.postdata
//import kotlinx.coroutines.flow.internal.NoOpContinuation.context
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Post(navctl: NavHostController) {
    val controller = rememberColorPickerController()
    val controller1 = rememberColorPickerController()
    var couleurtext by remember {
        mutableStateOf("${couleurprincipal}")
    }
    var dialog by remember {
        mutableStateOf(false)
    }
    var isloading by rememberSaveable {
        mutableStateOf(false)
    }
    val post = remember {
        mutableStateOf<List<postdata>>(emptyList())
    }
    var type by remember {
        mutableStateOf("")
    }
    var contenu by remember {
        mutableStateOf("")
    }
    var img by remember {
        mutableStateOf("")
    }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var description by remember {
        mutableStateOf("Pas de description")
    }
    var auteur by remember {
        mutableStateOf("")
    }
    var couleurbackground by remember {
        mutableStateOf("${Color.White}")
    }
    val formatdate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    val date = LocalDateTime.now().format(formatdate)
    var context = LocalContext.current

    /////recuper le nom
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener(){
                auteur = it.result.get("nom").toString()
//                    .split(" ")[0]
            }
    }


    // Définir le lanceur pour sélectionner l'image
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    selectedImageUri?.let { uri ->
        val context = LocalContext.current
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        img = com.mani_group.mani.bitmapToBase64(bitmap)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
        ,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){

                IconButton(
                    onClick = {
                        if(type == "" && contenu == ""){
                            GlobalNav.navctl.navigate(Route.Home)
                        }else{
                            dialog = true

                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "close post page")
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text("Postez un contenu", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(
                    rememberScrollState()
                )
        ) {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .width(150.dp)
                    .background(couleurprincipal.copy(0.9f))

            ) {
                IconButton(
                    onClick = {
                        type = "Text"
                    },
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(imageVector = Icons.Default.Create, contentDescription = "post text", tint = Color.White)
                        Text("Text", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Box(
                modifier = Modifier
                    .height(200.dp)
                    .width(150.dp)
                    .background(couleurprincipal.copy(0.7f))

            ) {
                IconButton(
                    onClick = {
                        type = "Photo"
                    }
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(imageVector = Icons.Default.Create, contentDescription = "post text", tint = Color.White)
                        Text("Image", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            //
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .width(150.dp)
                    .background(couleurprincipal.copy(0.5f))

            ) {
                IconButton(
                    onClick = {
                        type = "Video"
                    },
                    enabled = false
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(imageVector = Icons.Default.Create, contentDescription = "post video", tint = Color.White)
                        Text("Video", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        ////




        if(type == "Text"){
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = contenu, onValueChange = {contenu = it},
                label = {
                    Text("Dire ce que vous pensez", fontSize = 20.sp, textAlign = TextAlign.Center)
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = stringToColor(couleurtext),
                    focusedBorderColor = couleurprincipal,
                    unfocusedBorderColor = couleurprincipal,
                    unfocusedContainerColor = stringToColor(couleurbackground),
                    focusedTextColor = stringToColor(couleurtext),
                    focusedContainerColor = stringToColor(couleurbackground),
                    cursorColor = couleurprincipal
                ),
                textStyle = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
            )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    var selectedColor by remember { mutableStateOf("${couleurprincipal}") }

                    Column(
//                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        ImageColorPicker(
                            modifier = Modifier.size(250.dp),
                            controller = controller,
                            paletteImageBitmap = ImageBitmap.imageResource(R.drawable.couleur),
                            onColorChanged = { colorEnvelope ->
                                selectedColor = colorEnvelope.color.toString()
                                couleurbackground = selectedColor
                            }
                        )

                        Text("Couleur de fond ")

                    }


                    ////
                    var selectedtextColor by remember { mutableStateOf("${Color.White}") }

                    Column(
//                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        ImageColorPicker(
                            modifier = Modifier.size(250.dp),
                            controller = controller1,
                            paletteImageBitmap = ImageBitmap.imageResource(R.drawable.couleur),
                            onColorChanged = { colorEnvelope ->
                                selectedtextColor = colorEnvelope.color.toString()
                                couleurtext = selectedtextColor
                            }
                        )

                        Text("Couleur de text ")

                    }
                }


        }else if (type == "Photo"){
            Spacer(modifier = Modifier.height(10.dp))
            androidx.compose.material3.IconButton(
                onClick = { launcher.launch("image/*");},
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Create, contentDescription = "")
                    Text(if (img == "") "Choisir une image" else "Choisir une autre image", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = couleurprincipal)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                val inputStream = Base64.decode(img, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(inputStream, 0, inputStream.size)
                if (bitmap == null){
                    androidx.compose.material.Text("")
                }else{
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            if (img != ""){
                contenu = img
                OutlinedTextField(value = description, onValueChange = {description = it},
                    label = {
                        Text("Ajouter une description", fontSize = 20.sp, textAlign = TextAlign.Center)
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = couleurprincipal,
                        focusedBorderColor = couleurprincipal,
                        unfocusedBorderColor = couleurprincipal
                    ),
                    textStyle = TextStyle(fontSize = 25.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold)
                )
            }
//            Text(img.toString())

        }else if (type == "Video"){

        }
        if(type != "" && contenu != ""){
            Button(
                enabled = !isloading,
                onClick = {
                    if (type != "" && contenu != ""){
                        post(
                            type,
                            contenu,
                            description,
                            auteur,
                            date,
                            context,
                            isloading = true,
                            like = 0,
                            comment = "" ,
                            uid = "",
                            couleurtext = couleurtext,
                            couleurbackground = couleurbackground)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00bf63), contentColor = Color.White)
            ) {
                Text("Poster", fontSize = 18.sp, color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

    }
    if(dialog){
        AlertDialog(
            onDismissRequest = {
                dialog = false
            },
            confirmButton = {
                androidx.compose.material3.OutlinedButton (
                    onClick = {
                        GlobalNav.navctl.navigate(Route.Home)
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.width(110.dp),
                ) {
                    androidx.compose.material.Text(
                        "Fermer",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Red.copy(0.5f)
                    )
                }
            },
            title = {
                androidx.compose.material.Text("Anonce", textAlign = TextAlign.Center)
            },
            dismissButton = {
                androidx.compose.material3.OutlinedButton (
                    onClick = {
                        dialog = false
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.width(100.dp),
                ) {
                    androidx.compose.material.Text(
                        "Anuller",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = couleurprincipal.copy(0.5f)
                    )
                }
            },
            text = {
                androidx.compose.material.Text("Vous etes sur le point de tout anuller")
            }
        )


    }
}

fun stringToColor(colorString: String): Color {
    val regex = "Color\\((\\d+.?\\d*), (\\d+.?\\d*), (\\d+.?\\d*), (\\d+.?\\d*)".toRegex()
    val matchResult = regex.find(colorString)

    return if (matchResult != null) {
        val (red, green, blue, alpha) = matchResult.destructured
        Color(red.toFloat(), green.toFloat(), blue.toFloat(), alpha.toFloat()) // Conversion en Color
    } else {
        Color.Black // Valeur par défaut en cas d'erreur
    }
}

@SuppressLint("StaticFieldLeak")
val db = FirebaseFirestore.getInstance()

fun post(
    type: String,
    contenu: String,
    description: String,
    auteur: String,
    date: String,
    context: android.content.Context,
    isloading: Boolean,
    like: Int,
    comment: String,
    uid: String,
    couleurtext: String,
    couleurbackground: String,
    id: String = ""
) {
    val poste = postdata(
        type = type,
        contenu = contenu,
        description = description,
        auteur = auteur,
        date = date,
        like = 0,
        comment = emptyMap(),
        uid = FirebaseAuth.getInstance().currentUser?.uid!!,
        couleurtext = couleurtext,
        couleurbackground = couleurbackground,
        id = id
    )

    db
        .collection("data")
        .document("posts")
        .collection("post")
        .add(poste).addOnSuccessListener { postdata->
            val idgenerer = postdata.id
            db.collection("data")
                .document("posts")
                .collection("post")
                .document(idgenerer)
                .update("id", idgenerer)
            GlobalNav.navctl.navigate(Route.Home)
            AppUtil.showToast(context,  "vous avez fait une annonce avec succès")
        }.addOnFailureListener{e->
            AppUtil.showToast(context,  "Une erreur s'est produite")
        }


}

