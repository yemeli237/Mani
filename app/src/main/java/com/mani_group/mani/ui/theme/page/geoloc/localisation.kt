package com.mani_group.mani.ui.theme.page.geoloc

import android.Manifest
import android.preference.PreferenceManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.location.LocationServices

import com.mani_group.mani.GlobalNav
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.util.GeoPoint
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Polyline
import java.net.HttpURLConnection
import java.net.URL
import com.mani_group.mani.R
import androidx.core.graphics.scale
import androidx.core.graphics.drawable.toDrawable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeoLocalisation(){

    val selectedPoint = remember { mutableStateOf<GeoPoint?>(null) }
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted) {
                Toast.makeText(context, "Permission localisation requise", Toast.LENGTH_LONG).show()
            }
        }
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
    var destination by rememberSaveable { mutableStateOf("") }
    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        GlobalNav.navctl.popBackStack()
                        /*TODO*/
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.inverseOnSurface
                        )
                    }
                },
                title = {

                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {

                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.inverseOnSurface
                        )

                    }



                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00bf63),
                    titleContentColor = MaterialTheme.colorScheme.inverseOnSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.inverseOnSurface
                )

            )
        }
    ){padding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(padding)
//                .verticalScroll(rememberScrollState())
        ) {

            OsmMapSimple()
        }
    }
}



//code du formatage des donner de tracage
fun decodePolyline(polyline: String,): List<GeoPoint> {
    val coordinates = mutableListOf<GeoPoint>()
    var index = 0
    var lat = 0
    var lng = 0

    while (index < polyline.length) {
        var result = 1
        var shift = 0
        var b: Int
        do {
            b = polyline[index++].code - 63 - 1
            result += b shl shift
            shift += 5
        } while (b >= 0x1f)
        lat += if (result and 1 != 0) -(result shr 1) else result shr 1

        result = 1
        shift = 0
        do {
            b = polyline[index++].code - 63 - 1
            result += b shl shift
            shift += 5
        } while (b >= 0x1f)
        lng += if (result and 1 != 0) -(result shr 1) else result shr 1

        coordinates.add(GeoPoint(lat / 1E5, lng / 1E5))
    }

    return coordinates
}



//tracage d'itineraire de parcour
suspend fun fetchRoute(
    start: GeoPoint,
    end: GeoPoint,
    apiKey: String): List<GeoPoint> = withContext(Dispatchers.IO) {
    val url = URL("https://api.openrouteservice.org/v2/directions/driving-car")
    val body = """
        {
          "coordinates": [
            [${start.longitude}, ${start.latitude}],
            [${end.longitude}, ${end.latitude}]
          ]
        }
    """.trimIndent()

    try {
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "POST"
        conn.setRequestProperty("Authorization", apiKey)
        conn.setRequestProperty("Content-Type", "application/json")
        conn.doOutput = true
        conn.outputStream.write(body.toByteArray())

        val responseCode = conn.responseCode
        val responseStream = if (responseCode in 200..299) conn.inputStream else conn.errorStream
        val response = responseStream.bufferedReader().readText()

        Log.d("RouteResponse", "Body: $response")

        val json = JSONObject(response)
        val geometry = json
            .getJSONArray("routes")
            .getJSONObject(0)
            .getString("geometry")

        val route = json.getJSONArray("routes").getJSONObject(0)
        val distance = route.getJSONObject("summary").getDouble("distance") // en mètres
        val duration = route.getJSONObject("summary").getDouble("duration") // en secondes

        Log.d("Distance", "Distance: ${"%.1f".format(distance / 1000)} km")

        return@withContext decodePolyline(geometry, )
    } catch (e: Exception) {
        Log.e("RouteError", "Exception: ${e.message}")
        return@withContext emptyList()
    }
}




@OptIn(DelicateCoroutinesApi::class)
@Composable
fun OsmMapSimple() {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    val long = remember { mutableStateOf(0.0) }
    val lat = remember { mutableStateOf(0.0) }
    val currentMarker = remember { mutableStateOf<Marker?>(null) }

    // Nouveaux états pour tracer et marqueurs
    val routeLine = remember { mutableStateOf<Polyline?>(null) }
    val startMarker = remember { mutableStateOf<Marker?>(null) }
    val endMarker = remember { mutableStateOf<Marker?>(null) }

    val cleopenrouteservice = "eyJvcmciOiI1YjNjZTM1OTc4NTExMTAwMDFjZjYyNDgiLCJpZCI6IjhjMDJlYjU2YTlhNjQwM2Y5ZGIwNTJlNWM1NDU3NWNhIiwiaCI6Im11cm11cjY0In0="

    val permissionGranted = remember {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val mapEventsReceiver = object : MapEventsReceiver {
        override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
            long.value = p.longitude
            lat.value = p.latitude
            Log.d("carte", "longitude : ${long.value}, Latitude : ${lat.value}")

            // Supprimer ancien marqueur personnalisé
            currentMarker.value?.let { mapView.overlays.remove(it) }

            // Supprimer ancien tracé et marqueurs d’itinéraire
            routeLine.value?.let { mapView.overlays.remove(it) }
            startMarker.value?.let { mapView.overlays.remove(it) }
            endMarker.value?.let { mapView.overlays.remove(it) }

            // Marqueur personnalisé
            val originalDrawable = ResourcesCompat.getDrawable(context.resources, R.drawable.localisation, null)
            val originalBitmap = (originalDrawable as BitmapDrawable).bitmap
            val resizedBitmap = originalBitmap.scale(24, 24)
            val resizedDrawable = resizedBitmap.toDrawable(context.resources)

            val marker = Marker(mapView).apply {
                position = p
                title = "Point sélectionné"
                icon = resizedDrawable
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            }

            mapView.overlays.add(marker)
            currentMarker.value = marker

            // Tracer l’itinéraire
            if (permissionGranted) {
                try {
                    fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                        .addOnSuccessListener { location ->
                            if (location != null) {
                                val start = GeoPoint(location.latitude, location.longitude)
                                val end = GeoPoint(lat.value, long.value)

                                CoroutineScope(Dispatchers.IO).launch {
                                    try {
                                        val routePoints = fetchRoute(start, end, cleopenrouteservice)

                                        withContext(Dispatchers.Main) {
                                            val roadLine = Polyline().apply {
                                                setPoints(routePoints)
                                                color = android.graphics.Color.BLUE
                                                width = 10f
                                            }
                                            mapView.overlays.add(roadLine)
                                            routeLine.value = roadLine

                                            val startM = Marker(mapView).apply {
                                                position = start
                                                title = "Départ"
                                            }
                                            val endM = Marker(mapView).apply {
                                                position = end
                                                title = "Arrivée"
                                                icon = resizedDrawable
                                            }

                                            mapView.overlays.addAll(listOf(startM, endM))
                                            startMarker.value = startM
                                            endMarker.value = endM

                                            mapView.invalidate()
                                        }
                                    } catch (e: Exception) {
                                        Log.e("RouteError", "Erreur : ${e.message}")
                                    }
                                }
                            }
                        }
                } catch (e: SecurityException) {
                    Log.e("PermissionError", "Permission manquante : ${e.message}")
                }
            }

            return true
        }

        override fun longPressHelper(p: GeoPoint): Boolean = false
    }

    // Afficher la position actuelle
    LaunchedEffect(Unit) {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
        val mapEventsOverlay = MapEventsOverlay(mapEventsReceiver)
        mapView.overlays.add(mapEventsOverlay)

        val originalDrawable = ResourcesCompat.getDrawable(context.resources, R.drawable.localperson, null)
        val originalBitmap = (originalDrawable as BitmapDrawable).bitmap
        val resizedBitmap = originalBitmap.scale(24, 24)
        val resizedDrawable = resizedBitmap.toDrawable(context.resources)

        if (permissionGranted) {
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location ->
                    if (location != null) {
                        val currentPoint = GeoPoint(location.latitude, location.longitude)
                        mapView.controller.setZoom(20.0)
                        mapView.controller.setCenter(currentPoint)

                        val marker = Marker(mapView).apply {
                            position = currentPoint
                            title = "Vous êtes ici"
                            icon = resizedDrawable
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        }

                        mapView.overlays.add(marker)
                    }
                }
        } else {
            val defaultPoint = GeoPoint(3.8480, 11.5021) // Yaoundé
            mapView.controller.setZoom(5.0)
            mapView.controller.setCenter(defaultPoint)
        }
    }

    AndroidView(factory = { mapView }, modifier = Modifier.fillMaxSize())
}







//
//
////code du formatage des donner de tracage
//fun decodePolyline(polyline: String): List<GeoPoint> {
//    val coordinates = mutableListOf<GeoPoint>()
//    var index = 0
//    var lat = 0
//    var lng = 0
//
//    while (index < polyline.length) {
//        var result = 1
//        var shift = 0
//        var b: Int
//        do {
//            b = polyline[index++].code - 63 - 1
//            result += b shl shift
//            shift += 5
//        } while (b >= 0x1f)
//        lat += if (result and 1 != 0) -(result shr 1) else result shr 1
//
//        result = 1
//        shift = 0
//        do {
//            b = polyline[index++].code - 63 - 1
//            result += b shl shift
//            shift += 5
//        } while (b >= 0x1f)
//        lng += if (result and 1 != 0) -(result shr 1) else result shr 1
//
//        coordinates.add(GeoPoint(lat / 1E5, lng / 1E5))
//    }
//
//    return coordinates
//}
//
//
//
//suspend fun fetchRoute(
//    start: GeoPoint,
//    end: GeoPoint,
//    apiKey: String): List<GeoPoint> = withContext(Dispatchers.IO) {
//    val url = URL("https://api.openrouteservice.org/v2/directions/driving-car")
//    val body = """
//        {
//          "coordinates": [
//            [${start.longitude}, ${start.latitude}],
//            [${end.longitude}, ${end.latitude}]
//          ]
//        }
//    """.trimIndent()
//
//    try {
//        val conn = url.openConnection() as HttpURLConnection
//        conn.requestMethod = "POST"
//        conn.setRequestProperty("Authorization", apiKey)
//        conn.setRequestProperty("Content-Type", "application/json")
//        conn.doOutput = true
//        conn.outputStream.write(body.toByteArray())
//
//        val responseCode = conn.responseCode
//        val responseStream = if (responseCode in 200..299) conn.inputStream else conn.errorStream
//        val response = responseStream.bufferedReader().readText()
//
//        Log.d("RouteResponse", "Body: $response")
//
//        val json = JSONObject(response)
//        val geometry = json
//            .getJSONArray("routes")
//            .getJSONObject(0)
//            .getString("geometry")
//
//        val route = json.getJSONArray("routes").getJSONObject(0)
//        val distance = route.getJSONObject("summary").getDouble("distance") // en mètres
//        val duration = route.getJSONObject("summary").getDouble("duration") // en secondes
//
//        Log.d("Distance", "Distance: ${"%.1f".format(distance / 1000)} km")
//
//        return@withContext decodePolyline(geometry)
//    } catch (e: Exception) {
//        Log.e("RouteError", "Exception: ${e.message}")
//        return@withContext emptyList()
//    }
//}
//
//
//
//
//@OptIn(DelicateCoroutinesApi::class)
//@Composable
//fun OsmMapSimple() {
//    val context = LocalContext.current
//    val mapView = remember { MapView(context) }
//    var long = remember { mutableStateOf(0.0) }
//    var lat = remember { mutableStateOf(0.0) }
//    val permissionGranted = remember {
//        ContextCompat.checkSelfPermission(
//            context,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED
//    }
//
//    val currentMarker = remember { mutableStateOf<Marker?>(null) }
//    val cleopenrouteservice = "eyJvcmciOiI1YjNjZTM1OTc4NTExMTAwMDFjZjYyNDgiLCJpZCI6IjhjMDJlYjU2YTlhNjQwM2Y5ZGIwNTJlNWM1NDU3NWNhIiwiaCI6Im11cm11cjY0In0="
//    //obtenir les cordonnee au toucher
//    val mapEventsReceiver = object : MapEventsReceiver {
//
//        override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
//            val originalDrawable = ResourcesCompat.getDrawable(context.resources, R.drawable.localisation, null)
//            val originalBitmap = (originalDrawable as BitmapDrawable).bitmap
//
//            val resizedBitmap = originalBitmap.scale(24, 24)
//            val resizedDrawable = resizedBitmap.toDrawable(context.resources)
//            long.value = p.longitude
//            lat.value = p.latitude
//            Log.d("carte", "longitude : ${long.value}, Latitude : ${lat.value}",)
//
//            // Supprimer l’ancien marqueur s’il existe
//            currentMarker.value?.let {
//                mapView.overlays.remove(it)
//            }
//
//            // Créer le nouveau marqueur
//            val marker = Marker(mapView).apply {
//                position = p
//                title = "Point sélectionné"
//                icon = resizedDrawable
//                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
//            }
//
//            // Ajouter le nouveau marqueur et le stocker
//            mapView.overlays.add(marker)
//            currentMarker.value = marker
//
//            mapView.invalidate()
//            return true
//        }
//
//        override fun longPressHelper(p: GeoPoint): Boolean {
//            return false
//        }
//    }
//
//    LaunchedEffect(Unit) {
//        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
//        val mapEventsOverlay = MapEventsOverlay(mapEventsReceiver)
//        mapView.overlays.add(mapEventsOverlay)
//
//    }
//
//
//
//
//    LaunchedEffect(permissionGranted) {
//        if (permissionGranted) {
//            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
//
//            val locationRequest = LocationRequest.create().apply {
//                priority = Priority.PRIORITY_HIGH_ACCURACY
//                interval = 5000
//                fastestInterval = 2000
//                numUpdates = 1
//            }
//
//            val locationCallback = object : LocationCallback() {
//                override fun onLocationResult(result: LocationResult) {
//
//
//                    val location = result.lastLocation
//                    if (location != null) {
//                        val currentPoint = GeoPoint(location.latitude, location.longitude)
//                        mapView.controller.setZoom(20.0)
//                        mapView.controller.setCenter(currentPoint)
//
//                        val marker = Marker(mapView).apply {
//                            position = currentPoint
//                            title = "Vous êtes ici"
////                            icon = ResourcesCompat.getDrawable(context.resources, R.drawable.ai, null)
//                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
//                        }
//                        mapView.overlays.add(marker)
//
//                        CoroutineScope(Dispatchers.Main).launch {
//                            val start = GeoPoint(location.latitude, location.longitude)
//                            val end = GeoPoint(lat.value, long.value) // coordonne du point selectione
//
//                            try {
//                                val routePoints = fetchRoute(start, end, cleopenrouteservice)
//
//                                val roadLine = Polyline().apply {
//                                    setPoints(routePoints)
//                                    color = android.graphics.Color.BLUE
//                                    width = 5f
//                                }
//
//                                mapView.overlays.add(roadLine)
//
//                                val startMarker = Marker(mapView).apply {
//                                    position = start
//                                    title = "Départ"
//                                }
//                                val endMarker = Marker(mapView).apply {
//                                    position = end
//                                    title = "Arrivée"
//                                }
//                                mapView.overlays.addAll(listOf(startMarker, endMarker))
//
//                                mapView.invalidate()
//                            } catch (e: Exception) {
//                                Log.e("RouteError", "Erreur : ${e.message}")
//                            }
//                        }
//                    }
//
//                    fusedLocationClient.removeLocationUpdates(this)
//                }
//            }
//
//            fusedLocationClient.requestLocationUpdates(
//                locationRequest,
//                locationCallback,
//                Looper.getMainLooper()
//            )
//        } else {
//            val defaultPoint = GeoPoint(3.8480, 11.5021) // Yaoundé
//            mapView.controller.setZoom(5.0)
//            mapView.controller.setCenter(defaultPoint)
//        }
//    }
//    AndroidView(factory = { mapView }, modifier = Modifier.fillMaxSize())
//}
