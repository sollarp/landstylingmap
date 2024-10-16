package com.ldstack.stylinggooglemap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ldstack.stylinggooglemap.data.Repository
import com.ldstack.stylinggooglemap.data.dto.DataModel
import com.ldstack.stylinggooglemap.data.dto.toDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _polygonPoints = MutableStateFlow<List<DataModel>>(emptyList())
    val polygons: StateFlow<List<DataModel>> = _polygonPoints.asStateFlow()

    private val addedPolygonIds = mutableSetOf<Int>()

    private var hasMorePages = true
    private var pageNumber = 1

    init {
        viewModelScope.launch {
            try {
                while (hasMorePages) {
                    val serverResponse = repository.getServerResponse(page = pageNumber)
                    serverResponse.collect { siteList ->
                        val setModel = siteList.data.toDataModel()
                        hasMorePages = siteList.paginatorInfo.hasMorePages
                        pageNumber++

                        var startIndex = 0
                        val batchSize = 50

                        while (startIndex < setModel.size) {
                            val endIndex = minOf(startIndex + batchSize, setModel.size)
                            val batch = setModel.subList(startIndex, endIndex)

                            // Filter new items in the batch
                            val newItems = batch.filter { it.id !in addedPolygonIds }

                            // Add new items to the map
                            _polygonPoints.value = newItems

                            // Update added items set
                            addedPolygonIds.addAll(newItems.map { it.id })

                            // Update _polygonPoints.value with the entire list
                            _polygonPoints.value = _polygonPoints.value // Update the entire list

                            startIndex = endIndex

                            // Introduce a delay if new items were added
                            if (newItems.isNotEmpty()) {
                                delay(500) // Adjust delay as needed
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}
