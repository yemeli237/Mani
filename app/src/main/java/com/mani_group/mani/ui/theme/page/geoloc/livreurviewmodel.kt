package com.mani_group.mani.ui.theme.page.geoloc

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.preference.PreferenceManager
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.scale
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.mani_group.mani.R
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


class MapViewModel : ViewModel() {

    var distanceKm by mutableStateOf(0.0)
        private set

    var userLocation by mutableStateOf(GeoPoint(0.0, 0.0))
        private set

    var duree by mutableStateOf(0)
        private set

    //code du formatage des donner de tracage
    fun decodePolylineIti(polyline: String, ): List<GeoPoint> {
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



    suspend fun fetchRouteIti(
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
            val duration = route.getJSONObject("summary").getInt("duration") // en secondes
            distanceKm = distance
            duree = duration/60


            Log.d("Distance", "Distance: ${"%.1f".format(distance / 1000)} km")

            return@withContext decodePolylineIti(geometry,)
        } catch (e: Exception) {
            Log.e("RouteError", "Exception: ${e.message}")
            return@withContext emptyList()
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    @Composable
    fun Livraison() {
        val context = LocalContext.current
        val mapView = remember { MapView(context) }

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
        // Tracer l’itinéraire
        if (permissionGranted) {
            try {
                fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener { location ->
                        if (location != null) {
                            val start = GeoPoint(location.latitude, location.longitude)
                            val end = GeoPoint(3.8480, 11.5021)

                            CoroutineScope(Dispatchers.IO).launch {
                                try {
                                    val routePoints = fetchRouteIti(start, end, cleopenrouteservice)

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
//                                        icon = resizedDrawable
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



        // Afficher la position actuelle
        LaunchedEffect(Unit) {
            Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))


            val originalDrawable = ResourcesCompat.getDrawable(context.resources, R.drawable.localperson, null)
            val originalBitmap = (originalDrawable as BitmapDrawable).bitmap
            val resizedBitmap = originalBitmap.scale(24, 24)
            val resizedDrawable = resizedBitmap.toDrawable(context.resources)

            if (permissionGranted) {
                fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener { location ->
                        if (location != null) {
                            val currentPoint = GeoPoint(location.latitude, location.longitude)
                            mapView.controller.setZoom(18.0)
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

}
