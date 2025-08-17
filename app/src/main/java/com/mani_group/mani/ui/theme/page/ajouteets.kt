package com.mani_group.mani.ui.theme.page

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.mani_group.mani.GlobalNav
import com.mani_group.mani.data.couleurprincipal
import com.mani_group.mani.data.etsmodel
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.security.MessageDigest
import java.time.Instant
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEts(){
    var nomets by remember {
        mutableStateOf("")
    }
    val estpost = remember {
        mutableStateOf<List<etsmodel>>(emptyList())
    }
    var expad by remember {
        mutableStateOf(false
        )
    }
    var isloading by remember {
        mutableStateOf(false)
    }
    var image: File? = null
    var ville by  remember {
        mutableStateOf("")
    }
    var localisation by  remember {
        mutableStateOf("")
    }
    var region by remember {
        mutableStateOf("")
    }
    var categorie by remember {
        mutableStateOf("")
    }
    var expadville by remember {
        mutableStateOf(false)
    }
    var expadregion by remember {
        mutableStateOf(false)
    }
    var description by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var tel by remember {
        mutableStateOf("")
    }

    var garde by remember {
        mutableStateOf(false)
    }

    var ouvert by remember {
        mutableStateOf(false)
    }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var base64String by remember { mutableStateOf("") }
    val villes = listOf("Yaounde", "Douala", "Bafoussam")
    val regions = listOf("Extrem-Nord", "Nord", "Adamaoua", "Centre", "Est", "Sud", "Litoral", "Ouest", "Nord-Ouest", "Sud-Ouest")


    // Définir le lanceur pour sélectionner l'image
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }
    Scaffold(
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

                        Text(
                            "ajouter un etablissement",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            fontSize = 25.sp,
                            modifier = Modifier.padding(10.dp, 0.dp)
                        )
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {

                        Icon(imageVector = Icons.Outlined.Info, contentDescription = "", tint = MaterialTheme.colorScheme.inverseOnSurface)

                    }

                    DropdownMenu(
                        modifier = Modifier.background(color = couleurprincipal),
                        expanded = expadville, onDismissRequest = { expadville = false},
                        content = {
                            for (vill in villes) {
                                DropdownMenuItem(onClick = {
                                    expadville = false
                                    ville = vill
                                }) {
                                    Text(vill)
                                }

                            }
                        }

                    )
                    DropdownMenu(
                        modifier = Modifier.background(color = couleurprincipal),
                        expanded = expadregion, onDismissRequest = { expadregion = false},
                        content = {
                            for (reg in regions) {
                                DropdownMenuItem(onClick = {
                                    expadregion = false
                                    region = reg
                                }) {
                                    Text(reg)
                                }

                            }
                        }

                    )

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
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            if(isloading){
                Column(
                    modifier = Modifier.fillMaxWidth().padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(100.dp))
                    CircularProgressIndicator()
                }
            }else{
                Column(
                    modifier = Modifier.padding(8.dp).verticalScroll(rememberScrollState()),
//                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                ) {
                    Text("Nom de l'etablissement", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                    OutlinedTextField(
                        value = nomets, onValueChange = {nomets = it}, label = {
                            Text("Ex: Pharmacie Weel +",fontWeight = FontWeight.Bold,)
                        }, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = couleurprincipal,
                            focusedBorderColor = couleurprincipal,
                            unfocusedBorderColor = couleurprincipal
                        )
                    )
                    Spacer( modifier = Modifier.height(8.dp))

                    Text("Localisation", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                    OutlinedTextField(
                        value = localisation, onValueChange = {localisation = it}, label = {
                            Text("Ex: Ron-point express descente acacia",fontWeight = FontWeight.Bold,)
                        }, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = couleurprincipal,
                            focusedBorderColor = couleurprincipal,
                            unfocusedBorderColor = couleurprincipal
                        )
                    )
                    Spacer( modifier = Modifier.height(8.dp))

                    Text("Adress Mail", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                    OutlinedTextField(
                        value = email, onValueChange = {email = it}, label = {
                            Text("Ex: Mani@gmail.com",fontWeight = FontWeight.Bold,)
                        }, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = couleurprincipal,
                            focusedBorderColor = couleurprincipal,
                            unfocusedBorderColor = couleurprincipal
                        )
                    )
                    Spacer( modifier = Modifier.height(8.dp))

                    Text("Numero", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                    OutlinedTextField(
                        value = tel, onValueChange = {tel = it}, label = {
                            Text("Ex: +237 620303107",fontWeight = FontWeight.Bold,)
                        }, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = couleurprincipal,
                            focusedBorderColor = couleurprincipal,
                            unfocusedBorderColor = couleurprincipal
                        ),
//                        keyboardOptions = KeyboardType.Number
                    )
                    Spacer( modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,) {
                        Text("Ville", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                        IconButton(onClick = {expadville = true}) { Icon(imageVector = Icons.Default.Add, contentDescription = "Ajouter une ville")}
                        Spacer(modifier = Modifier.weight(1f))
                        Text(if(ville != "") ville else "Aucune ville selectionner")
                    }
                    Spacer( modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,) {
                        Text("Region", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                        IconButton(onClick = {expadregion = true}) { Icon(imageVector = Icons.Default.Add, contentDescription = "Ajouter une ville")}
                        Spacer(modifier = Modifier.weight(1f))
                        Text(if(region != "") region else "Aucune region selectionner")
                    }
                    Spacer( modifier = Modifier.height(8.dp))

                    TextButton(
                        colors = ButtonDefaults.buttonColors(backgroundColor = couleurprincipal),
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {expad = true}
                    ) { Text("Categorie : $categorie", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)}
                    DropdownMenu(
                        modifier = Modifier.background(color = couleurprincipal),
                        expanded = expad,
                        onDismissRequest = { expad = false },
                        content = {
                            DropdownMenuItem(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                                TextButton(
                                    onClick = {
                                        expad = false
                                        categorie = "pharmacie"
                                    },
                                    modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("Pharmacie",color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
                                    }
                                }
                            }
                            Divider(modifier = Modifier.fillMaxWidth())
                            DropdownMenuItem(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                                TextButton(
                                    onClick = {
                                        expad = false
                                        categorie = "Hopital de district"
                                    },
                                    modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("Hopital de district",color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
                                    }
                                }
                            }

                            Divider(modifier = Modifier.fillMaxWidth())
                            DropdownMenuItem(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                                TextButton(
                                    onClick = {
                                        expad = false
                                        categorie = "Clinique"
                                    },
                                    modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("Clinique",color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
                                    }
                                }
                            }

                            Divider(modifier = Modifier.fillMaxWidth())
                            DropdownMenuItem(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                                TextButton(
                                    onClick = {
                                        expad = false
                                        categorie = "Centre Hophtamologique"
                                    },
                                    modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("Centre Hophtamologique",color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
                                    }
                                }
                            }
                        }
                    )

                    //
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(
                        colors = ButtonDefaults.buttonColors(backgroundColor = couleurprincipal),
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            launcher.launch("image/*")
                        }) {
                        Text("Ajouter une image", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    selectedImageUri?.let { uri ->
                        val context = LocalContext.current
                        val inputStream = context.contentResolver.openInputStream(uri)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        inputStream?.close() // Fermer le flux après lecture

                        val exif = context.contentResolver.openInputStream(uri)?.let { ExifInterface(it) }
//                    val orientation = exif?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL) ?: ExifInterface.ORIENTATION_NORMAL


                        val orientation = exif?.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL
                        ) ?: ExifInterface.ORIENTATION_NORMAL

                        val matrix = Matrix().apply {
                            when (orientation) {
                                ExifInterface.ORIENTATION_ROTATE_90 -> postRotate(90f)
                                ExifInterface.ORIENTATION_ROTATE_180 -> postRotate(180f)
                                ExifInterface.ORIENTATION_ROTATE_270 -> postRotate(270f)
                            }
                        }


                        val correctedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

                        val fill = correctedBitmap?.let { bitmapToFile(context, bitmap, fileName = "image.jpg")}

                        image = fill
                        Log.d("fichier", "$image")

                        Card(

                        ) {
                            Image(
                                bitmap = correctedBitmap.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier.size(200.dp),
                                contentScale = ContentScale.Crop
                            )
//                        img = bitmapToBase64(bitmap)
                        }


                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Ajouter une description", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(value = description, onValueChange = {description = it},
                        label = {
                            Text("Aidez les autres a mieux connaitre la structure", fontSize = 20.sp, textAlign = TextAlign.Center)
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

                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        enabled = if(image == null || localisation =="" || ville == "" || region == "" || categorie == "" || description =="" || nomets =="") false else true,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            isloading = true


                            image?.let{
                                uploadToCloudinary(it) { imageUrl ->
                                    if (imageUrl != null) {
                                        Log.d("Cloudinary", "Lien de l'image : $imageUrl")
                                        val data  = etsmodel(
                                            nom = nomets,
                                            localisation = localisation,
                                            ville = ville,
                                            region = region,
                                            categorie = categorie,
                                            image = imageUrl,
                                            description = description,
                                            email = email,
                                            telephone = tel,
                                            garde = garde,
                                            ouvert = ouvert
                                        )

                                        Firebase.firestore.collection("ets")
                                            .add(data)
                                            .addOnSuccessListener { doc->
                                                isloading = false
                                                val id = doc.id
                                                Firebase.firestore.collection("ets")
                                                    .document(id)
                                                    .update("id", id)
                                                    .addOnCompleteListener {
                                                        if(it.isSuccessful){
                                                            isloading = false
                                                            Log.d("firebase", "Sauvegarder avec succes")
                                                        }else { e: Exception ->
                                                            isloading= false
                                                            Log.d("firebase", "une erreur c'est produit $e")
                                                        }
                                                    }
                                            }

                                    } else {
                                        isloading = false
                                        Log.e("Cloudinary", "Échec de l'upload")
                                        Log.d("Cloudinary", "Fichier : ${image?.absolutePath}, taille : ${image?.length()}")
                                    }
                                }
                            }?: Log.e("Cloudinary", "Le fichier image est nul")
                        }) {
                        Text("Soumetre")
                    }
                }
            }
        }
    }

}

fun bitmapToFile(context: Context, bitmap: Bitmap, fileName: String = "image.jpg" ): File {
    val file = File(context.cacheDir, fileName)
    file.outputStream().use { out ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
    }
    return file
}
















fun uploadToCloudinary(imageFile: File, onResult: (String?) -> Unit) {
    val cloudName = "dcnnsq1xw"
    val apiKey = "557129197414647"
    val apiSecret = "0WPxWgerHQu5okFz3opeHa7MG-w"
    val timestamp = (System.currentTimeMillis() / 1000).toString()




    val paramsToSign = "timestamp=$timestamp"
    val mac = Mac.getInstance("HmacSHA1")
    val secretKey = SecretKeySpec(apiSecret.toByteArray(), "HmacSHA1")
    mac.init(secretKey)

    val signature = generateSignature(timestamp, "0WPxWgerHQu5okFz3opeHa7MG-w")


    val requestBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("file", imageFile.name, imageFile.asRequestBody("image/*".toMediaTypeOrNull()))
        .addFormDataPart("api_key", apiKey)
        .addFormDataPart("timestamp", timestamp)
        .addFormDataPart("signature", signature)
        .build()

    val request = Request.Builder()
        .url("https://api.cloudinary.com/v1_1/$cloudName/image/upload")
        .post(requestBody)
        .build()

    OkHttpClient().newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("Cloudinary", "Erreur réseau : ${e.message}")
            onResult(null)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onResponse(call: Call, response: Response) {
            val body = response.body?.string()
            Log.d("Cloudinary", "Réponse : $body")
            if (response.isSuccessful && body != null) {
                val imageUrl = JSONObject(body).getString("secure_url")
                Log.d("Cloudinary", "Timestamp utilisé : $timestamp")
                onResult(imageUrl)
            } else {
                Log.d("Cloudinary", "Final timestamp: $timestamp, signature: $signature")
                Log.e("Cloudinary", "Échec HTTP : ${response.code}, body : $body")
                Log.d("Cloudinary", "Timestamp: $timestamp, UTC: ${Instant.ofEpochSecond(timestamp.toLong())}")
                onResult(null)
            }
        }
    })
}













fun generateSignature(timestamp: String, apiSecret: String): String {
    val stringToSign = "timestamp=$timestamp"
    val digest = MessageDigest.getInstance("SHA-1")
    val hash = digest.digest((stringToSign + apiSecret).toByteArray())
    return hash.joinToString("") { "%02x".format(it) }
}

