package com.ldstack.stylinggooglemap

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.google.maps.android.compose.Marker

@Composable
fun HomeScreen(
    //mainViewModel: MapViewModel = hiltViewModel()
) {
    val singapore = LatLng(1.35, 103.87)
    val singaporeMarkerState = rememberMarkerState(position = singapore)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(bottom = 100.dp),onClick ={ /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), // Apply padding to avoid overlap with FAB
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = singaporeMarkerState,
                title = "Singapore",
                snippet = "Marker in Singapore"
            )
        }
    }
}
