package com.ldstack.stylinggooglemap.data

import com.ldstack.stylinggooglemap.data.dto.DataX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class Repository
@Inject constructor() {
    fun getServerResponse(): Flow<List<DataX>> = flow {
        emit(NetworkService.getCountries().sitesByDistrict.data)
    }.flowOn(Dispatchers.IO)
}