package com.ldstack.stylinggooglemap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ldstack.stylinggooglemap.data.Repository
import com.ldstack.stylinggooglemap.data.Site
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor (
    private val repository: Repository
) : ViewModel() {

    private val _countries = MutableLiveData<List<Site>>()
    val countries: LiveData<List<Site>> = _countries
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        println("MapViewModel initialized")
        loadCountries()
    }

    fun loadCountries() {
        viewModelScope.launch {
            repository.getCountries()
                .catch { e ->
                    // Handle error case
                    _error.postValue(e.message)
                }
                .collect { siteList ->
                    // Update LiveData with the result
                    _countries.postValue(siteList)
                }
        }
    }
}