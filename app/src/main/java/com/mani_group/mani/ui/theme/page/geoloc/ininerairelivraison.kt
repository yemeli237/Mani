package com.mani_group.mani.ui.theme.page.geoloc

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.preference.PreferenceManager
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material3.Card
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.scale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.mani_group.mani.GlobalNav
import com.mani_group.mani.R
import com.mani_group.mani.Route
import com.mani_group.mani.data.couleurprincipal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import java.net.HttpURLConnection
import java.net.URL

object Globals {
    var distanceKm: Double = 0.0
}
@Composable
fun ItineraireLivraison(viewModel: MapViewModel = viewModel()){
    var distance = remember {
        mutableStateOf(0.0)
    }
    var duree = remember {
        mutableStateOf(0.0)
    }


    val long = remember { mutableStateOf(0.0) }
    val lat = remember { mutableStateOf(0.0) }

    Scaffold(
        floatingActionButton = {
            Column {
                androidx.compose.material3.IconButton(
                    onClick = {
                        GlobalNav.navctl.navigate((Route.ItineraireLivraison))
                    },
                    modifier = Modifier.size(50.dp),
                    colors = IconButtonDefaults.iconButtonColors(containerColor = couleurprincipal, contentColor = Color.White)
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Filled.LocationSearching,
                        contentDescription = "Action button",
                    )
                }
            }
        },
        topBar = {
            Card(
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Nom du livreur : ",)
                    Text("Yemeli",)
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Societe : ",)
                    Text("Food delivery",)
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Distance", )
                        Text("${viewModel.distanceKm}m",)
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Duree", )
                        Text("${viewModel.duree}min", )
                    }
                }
            }

        }
    ) { padding ->

        Column(
            modifier = Modifier
//                .fillMaxSize()
                .padding(padding)
//                .verticalScroll(rememberScrollState())
        ) {

            viewModel.Livraison()
        }

    }

}

@Composable
fun SuivreLivraison(viewModel: MapViewModel = viewModel()){
    viewModel.Livraison()
}







