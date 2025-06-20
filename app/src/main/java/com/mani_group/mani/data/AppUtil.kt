package com.mani_group.mani.data

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.mani_group.mani.GlobalNav
import com.mani_group.mani.Route

object AppUtil {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}


@Composable
fun dropmenu(){
    val isConnected = Firebase.auth.currentUser != null
    var expended by remember { mutableStateOf(false) }

    IconButton(onClick = {
        expended = true
    }) { Icon(imageVector = Icons.Default.MoreVert, contentDescription = "", tint = Color.White) }
    DropdownMenu(
        expanded = expended,
        onDismissRequest = {expended = false},
        modifier = Modifier.fillMaxWidth().background(color = couleurprincipal)
    ) {
        DropdownMenuItem(onClick = {}) {
            TextButton(
                onClick = {
                    expended = false;
                    if(isConnected) GlobalNav.navctl.navigate(Route.PagePro) else GlobalNav.navctl.navigate(Route.Singin)
                          },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Devenir Pro", fontSize = 18.sp)
            }
        }
        DropdownMenuItem(
            onClick = {}) {
            TextButton(
                onClick = {expended = false; GlobalNav.navctl.navigate(Route.Aide)},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Aide", fontSize = 18.sp)
            }
        }
        DropdownMenuItem(
            onClick = {}) {
            TextButton(
                onClick = {expended = false; GlobalNav.navctl.navigate(Route.Menu)},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Menu", fontSize = 18.sp)
            }
        }
        DropdownMenuItem(
            onClick = {}) {
            TextButton(
                onClick = {expended = false; GlobalNav.navctl.navigate(Route.Menu)},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Parametre", fontSize = 18.sp)
            }
        }
        DropdownMenuItem(
            onClick = {}) {
            TextButton(
                onClick = {expended = false; GlobalNav.navctl.navigate(Route.Menu)},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Nos services", fontSize = 18.sp)
            }
        }
    }
}