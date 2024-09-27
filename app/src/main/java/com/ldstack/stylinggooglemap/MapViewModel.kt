package com.ldstack.stylinggooglemap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ldstack.stylinggooglemap.data.Repository
import com.ldstack.stylinggooglemap.data.Site
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MapViewModel (
    private val repository: Repository
) : ViewModel() {

    val countries: StateFlow<List<Site>>
        get() = repository.getCountries()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(1000),
                initialValue = emptyList()
            )

    fun fetchCountries() {
        viewModelScope.launch {
            countries.collect { sites ->
                println("Countries: $sites") // Print the list of sites
            }
        }
    }
}