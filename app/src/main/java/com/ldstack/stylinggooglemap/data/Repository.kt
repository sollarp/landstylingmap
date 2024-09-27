package com.ldstack.stylinggooglemap.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class Repository
//@Inject constructor
    () {


    fun getCountries(): Flow<List<Site>> = flow {
        emit(NetworkService.getCountries())
    }.flowOn(Dispatchers.IO)
}