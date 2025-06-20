package com.mani_group.mani.ui.theme.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mani_group.mani.GlobalNav
import com.mani_group.mani.Route
import com.mani_group.mani.data.couleurprincipal
import java.util.Locale

@Preview
@Composable
fun BarrRecherche(){
    var filter by rememberSaveable { mutableStateOf("") }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        androidx.compose.material3.OutlinedTextField(
            value = filter,
            onValueChange = {filter = it},
            label = { Text("Rechercher un medicament") },
            shape = RoundedCornerShape(10.dp),
//            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = couleurprincipal,
                focusedBorderColor = couleurprincipal,
                unfocusedBorderColor = couleurprincipal.copy(0.5f),
                unfocusedTextColor = couleurprincipal.copy(0.5f),
            )
        )
        IconButton(
            enabled = filter != "",
            onClick = {
//                filter = filter.uppercase(Locale.ROOT)
                GlobalNav.navctl.navigate("${Route.ResultProduit}/${filter}")
            }
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "", tint = Color(0xFF00bf63))
        }
    }
}



@Composable
fun BarrRecherchepharmacie(){
    var filter by rememberSaveable { mutableStateOf("") }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        androidx.compose.material3.OutlinedTextField(
            value = filter,
            onValueChange = {filter = it},
            label = { Text("Rechercher une pharmacie") },
            shape = RoundedCornerShape(10.dp),
//            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = couleurprincipal,
                focusedBorderColor = couleurprincipal,
                unfocusedBorderColor = couleurprincipal.copy(0.5f),
                unfocusedTextColor = couleurprincipal.copy(0.5f),
            )
        )
        IconButton(

            onClick = {

        }) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "", tint = Color(0xFF00bf63))
        }
    }
}