package com.mani_group.mani.ui.theme.page.geoloc

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.preference.PreferenceManager
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline

class ajoutecoordone: ViewModel() {
    var longitude by mutableStateOf(0.0)
        private set

    var latitude by mutableStateOf(0.0)
        private set


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
                longitude = long.value
                latitude = lat.value


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


}