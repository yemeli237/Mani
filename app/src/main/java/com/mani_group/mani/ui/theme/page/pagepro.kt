package com.mani_group.mani.ui.theme.page

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mani_group.mani.GlobalNav
import com.mani_group.mani.R
import com.mani_group.mani.Route
import com.mani_group.mani.data.abonement
import com.mani_group.mani.data.couleurprincipal

@Composable
fun PagePro(navctl: NavHostController) {
    var expand by remember {
        mutableStateOf(false)
    }
    var expanoperateur by remember {
        mutableStateOf(false)
    }
    var montan by remember {
        mutableStateOf(0)
    }
    val forfait = remember {
        mutableStateOf<List<abonement>>(emptyList())
    }
    var payer by remember {
        mutableStateOf("")
    }
    var dialog by remember {
        mutableStateOf(false)
    }
    var operateur by remember {
        mutableStateOf("MTN Mobile Money")
    }

    Column (
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        Image(painter = painterResource(R.drawable.mani), contentDescription = "", modifier = Modifier.size(200.dp))
        Text("C'est un vrai regale d'avoir un compte PRO",
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline)

        Spacer(modifier = Modifier.height(20.dp))

        TextButton(
            onClick = {expand = true},
//            elevation = androidx.compose.material.ButtonDefaults.elevation(2.dp),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth(),

        ) {
            Text("Choisir un abonnement!", color = couleurprincipal, fontSize = 15.sp)
        }

        OutlinedTextField(
            value = payer,
            onValueChange = {payer = it},
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = couleurprincipal,
                unfocusedBorderColor = couleurprincipal.copy(0.5f),
                unfocusedLabelColor = Color.Gray,
                focusedLabelColor = couleurprincipal.copy(0.7f)
            ),
            label = {
            Text("Numero de telephone du payeur")
        })
        Spacer(modifier = Modifier.height(20.dp))

        DropdownMenu(
            expanded = expand,
            onDismissRequest = {expand = false},
            modifier = Modifier.fillMaxWidth().background(color = couleurprincipal)
        ) {
            DropdownMenuItem(onClick = {
                expand = false;
                montan = 50
            }) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text("PRO", color = Color.Yellow.copy(0.6f), fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.weight(0.5f))
                    Text(("50fcfa(24Heurs)"), color = Color.White)
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Sharp.Info, contentDescription = "...")
                    }
                }
            }
            //
            DropdownMenuItem(onClick = {
                expand = false;
                montan = 500;
            }) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text("GOLD", color = Color.Yellow.copy(0.8f), fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.weight(0.5f))
                    Text(("500fcfa(30 jours)"),color = Color.White)
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Sharp.Info, contentDescription = "...")
                    }
                }
            }
            //
            DropdownMenuItem(onClick = {
                expand = false;
                montan = 2500;
            }) {
                Row (modifier = Modifier.fillMaxWidth()){
                    Text("PREMIUM", color = Color.Yellow.copy(1f), fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.weight(0.5f))
                    Text(("2500fcfa(12mois)"),color = Color.White)
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Sharp.Info, contentDescription = "...")
                    }
                }
            }
        }


        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.padding(16.dp, 0.dp)) {
                    Text("Montan de transfert  :    ",
                        fontSize = 18.sp,
                        color = couleurprincipal,
                        fontWeight = FontWeight.Bold)
                    Text("$montan",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.padding(16.dp, 0.dp)) {
                    Text("Beneficiaire :    ", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = couleurprincipal)
                    Text("Mani", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material3.TextButton (
                        onClick = {
                            expanoperateur = true
                        },
//                        modifier = Modifier.fillMaxWidth()
                        ) {
                        Text("Operateur : ", fontWeight = FontWeight.Bold, color = couleurprincipal, fontSize = 18.sp)
                    }
                    Text(operateur, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
                androidx.compose.material.DropdownMenu(
                    expanded = expanoperateur, onDismissRequest = {}
                ) {
                    DropdownMenuItem(onClick = {expanoperateur = false; operateur = "MTN Mobile Money"} ) {
                        Text("MTN Mobile Money")
                    }
                    DropdownMenuItem(onClick = {expanoperateur = false; operateur = "Orane Money"}) {
                        Text("Orane Money")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            androidx.compose.material3.OutlinedButton (
                onClick = {
                    GlobalNav.navctl.navigate(Route.Home)
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.width(150.dp),
            ) {
                Text("Anuler", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.Red.copy(0.5f))
            }
            androidx.compose.material3.OutlinedButton(
                onClick = {
                    dialog = true
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.width(150.dp),
                enabled = if (montan == 0 || payer == "") false else true,
            ) {
                Text("Suivant", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = couleurprincipal.copy(0.5f))
            }
        }


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
                    Text("Valider", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = couleurprincipal.copy(0.5f))
                }
            },
            title = {
                Text("Abonnement", textAlign = TextAlign.Center)
            },
            dismissButton = {
                androidx.compose.material3.OutlinedButton (
                    onClick = {
                        dialog = false
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.width(100.dp),
                ) {
                    Text("Anuler", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.Red.copy(0.5f))
                }
            },
            text = {
                Text("Vous etes sur le point de faire un depot de ${montan} chez Mani a partir de votre compte ${operateur}")
            }
        )
    }
}
