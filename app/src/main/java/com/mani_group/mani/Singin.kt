package com.mani_group.mani

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mani_group.mani.data.AppUtil
import com.mani_group.mani.viewmodell.AuthViewModesl

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*

import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.util.Base64
import androidx.compose.foundation.border
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import com.mani_group.mani.data.couleurprincipal
import java.io.ByteArrayOutputStream





@Composable
fun Singin(
    navctl: NavHostController,
    modifier: Modifier = Modifier,
    AuthViewModesl: AuthViewModesl = viewModel(),
) {
    var adress by rememberSaveable {
        mutableStateOf("")
    }
    var pass by rememberSaveable {
        mutableStateOf("")
    }
    var name by rememberSaveable {
        mutableStateOf("")
    }
    var img by rememberSaveable {
        mutableStateOf("")
    }
    var genre by rememberSaveable {
        mutableStateOf("")
    }
    var opperateurbanque by rememberSaveable {
        mutableStateOf("")
    }
    var numero by rememberSaveable {
        mutableStateOf("")
    }
    var profession by rememberSaveable {
        mutableStateOf("")
    }
    var contact by rememberSaveable {
        mutableStateOf("")
    }
    var context = LocalContext.current

    var isloading by rememberSaveable {
        mutableStateOf(false)
    }

    ////

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var base64String by remember { mutableStateOf("") }


    // Définir le lanceur pour sélectionner l'image
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    ///

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(onClick = {
            launcher.launch("image/*")
        }) {
            Row {
                Text("Ajouter une image", color = couleurprincipal, fontWeight = FontWeight.Bold)
            }
        }
        selectedImageUri?.let { uri ->
            val context = LocalContext.current
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close() // Fermer le flux après lecture

            val exif = context.contentResolver.openInputStream(uri)?.let { ExifInterface(it) }
            val orientation = exif?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL) ?: ExifInterface.ORIENTATION_NORMAL


            val matrix = Matrix()
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
            }

            val correctedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)


            Card(
                modifier = Modifier.size(150.dp)
                    .border(width = 2.dp, color = Color(0xFF00bf63), shape = RoundedCornerShape(100.dp)),
                shape = RoundedCornerShape(100.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
            ) {
                Image(
                    bitmap = correctedBitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Crop
                )
                img = bitmapToBase64(bitmap)
            }


        }
//        Image(painter = painterResource(R.drawable.mani), contentDescription = "")
        Text("Creation du compte",fontWeight = FontWeight.Bold, fontSize = 20.sp)


        OutlinedTextField(value = name, onValueChange = {name = it}, label = {
            Text("Nom",fontWeight = FontWeight.Bold,)
        }, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = couleurprincipal,
            focusedBorderColor = couleurprincipal,
            unfocusedBorderColor = couleurprincipal
        )
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = adress, onValueChange = {adress = it}, label = {
                Text("Adresse mail", fontWeight = FontWeight.Bold,)
            }, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = couleurprincipal,
                focusedBorderColor = couleurprincipal,
                unfocusedBorderColor = couleurprincipal
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = pass, onValueChange = {pass = it}, label = {
            Text("Mot de passe", fontWeight = FontWeight.Bold,)
        }, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = couleurprincipal,
            focusedBorderColor = couleurprincipal,
            unfocusedBorderColor = couleurprincipal
        ),
            visualTransformation = PasswordVisualTransformation(),
        )

        Spacer(modifier = Modifier.height(10.dp))
        Button(
            enabled = !isloading,
            onClick = {
                isloading = true
                AuthViewModesl.Singin(
                    adress, pass, name, img, genre, numero, opperateurbanque,
                    contact,
                    profession,
                    notification = emptyList(),
                    panier = emptyList(),
                    like = emptyList()
                ) { success, message ->
                    if (success) {
                        isloading = false
                        navctl.navigate(Route.Home){
                            popUpTo(Route.Home){inclusive = true}
                        }
                    } else {
                        isloading = false
                        // Afficher un message d'erreur à l'utilisateur
                        AppUtil.showToast(context, message?: "Une erreur s'est produite")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = couleurprincipal, contentColor = Color.White)
        ) { Text(text = if(!isloading) "Creer un compte" else "Chargement...", fontWeight = FontWeight.Bold, fontSize = 20.sp) }
        Spacer(modifier = Modifier.height(10.dp))

        Text("Ou se connecter avec")
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = {}, modifier = Modifier.size(100.dp), shape = RoundedCornerShape(100.dp)) {
                Image(painter = painterResource(R.drawable.f), contentDescription = "")
            }

            TextButton(onClick = {},modifier = Modifier.size(100.dp),shape = RoundedCornerShape(100.dp)) {
                Image(painter = painterResource(R.drawable.t), contentDescription = "")
            }

            TextButton(onClick = {},modifier = Modifier.size(100.dp),shape = RoundedCornerShape(100.dp)) {
                Image(painter = painterResource(R.drawable.g), contentDescription = "")
            }
        }

        TextButton(onClick = {navctl.navigate(Route.Login)}) {
            Row {
                Text("J'ai un compte", color = couleurprincipal, fontWeight = FontWeight.Bold)
            }
        }
    }
}



@Composable
fun SelectImageScreen() {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var base64String by remember { mutableStateOf("") }


    // Définir le lanceur pour sélectionner l'image
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Bouton pour ouvrir la galerie
        Button(
            onClick = { launcher.launch("image/*") },
            colors = ButtonDefaults.buttonColors(backgroundColor = couleurprincipal, contentColor = Color.White)

            ) {
            Text("Choisir une image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Afficher l'image sélectionnée si disponible
        selectedImageUri?.let { uri ->
            val context = LocalContext.current
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)


            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop
            )
            base64String = bitmapToBase64(bitmap)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(backgroundColor = couleurprincipal, contentColor = Color.White)
            ) { Text("Valider") }
        } ?: Text("Base64 : ${if (base64String.isNotEmpty()) base64String else "Aucune image sélectionnée"}")}
    }


//





fun bitmapToBase64(bitmap: Bitmap): String {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream) // Compression en PNG
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT) // Conversion en Base64
}
