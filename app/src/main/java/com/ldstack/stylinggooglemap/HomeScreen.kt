package com.ldstack.stylinggooglemap

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberCameraPositionState
import com.ldstack.stylinggooglemap.data.dto.DataModel
import com.ldstack.stylinggooglemap.utilize.Converter


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen() {

    var selectedPolygonId by remember { mutableStateOf<Int?>(null) }
    val mainViewModel: MapViewModel = hiltViewModel()
    val dataModelList by mainViewModel.polygons.collectAsState()
    val holdAllData = remember { mutableListOf<DataModel>() }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(
                LatLng(
                    51.38903466190211, -2.7818785652738525
                ), 11f
            )
        }
    ) {
        holdAllData.addAll(dataModelList)
        holdAllData.forEachIndexed { _, dataModel ->

            val latLngPoints: List<LatLng> = Converter.convertToLatLng(dataModel.polygonPoints)

            val fillColor =
                Color(android.graphics.Color.parseColor(dataModel.siteCategory.color ))
            val strokeColor = Color(
                android.graphics.Color.parseColor(
                    dataModel.siteCategory.stroke_color
                )
            )
            val strokeWidth = dataModel.siteCategory.stroke_weight.toFloat()

            val isSelected = dataModel.id == selectedPolygonId
            Polygon(
                points = latLngPoints,
                clickable = true,
                fillColor = if (isSelected) Color.Red else fillColor,
                strokeColor = strokeColor,
                strokeWidth = strokeWidth,
                tag = dataModel.name,
                onClick = {
                    selectedPolygonId = dataModel.id
                }
            )
        }
    }
    Box(contentAlignment = Alignment.BottomCenter) {
        Button(
            onClick = {
                selectedPolygonId = null
            },
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text("Reset Selection")
        }
    }
}
