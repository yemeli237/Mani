package com.mani_group.mani.action

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mani_group.mani.data.couleurprincipal

@Composable
fun ActionBtn(){
    var openmenu by remember {
        mutableStateOf(false)
    }
    androidx.compose.material3.IconButton(
        onClick = {
            openmenu = true
        },
        modifier = Modifier.size(50.dp),
        colors = IconButtonDefaults.iconButtonColors(containerColor = couleurprincipal, contentColor = Color.White)
    ) { Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "Action button", ) }

    @Composable
    fun menu(){
        DropdownMenu(expanded = openmenu, onDismissRequest = {}) {
            DropdownMenuItem(onClick = {openmenu = false}) {
                Text("Bonjour")
            }
        }
    }
}