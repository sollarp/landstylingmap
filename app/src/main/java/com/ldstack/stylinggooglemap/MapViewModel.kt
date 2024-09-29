package com.ldstack.stylinggooglemap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ldstack.stylinggooglemap.data.Repository
import com.ldstack.stylinggooglemap.data.dto.DataModel
import com.ldstack.stylinggooglemap.data.dto.toDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor (
    private val repository: Repository
) : ViewModel() {

    private val _polygonPoints = MutableLiveData<List<DataModel>>()
    val polygonPoints: LiveData<List<DataModel>> = _polygonPoints

    init {
        viewModelScope.launch {
            try {
                val serverResponse = repository.getServerResponse()
                serverResponse.collect { siteList ->
                    val setModel = siteList.toDataModel()
                    _polygonPoints.value = setModel
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}
