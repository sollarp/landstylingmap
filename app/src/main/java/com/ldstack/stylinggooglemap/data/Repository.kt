package com.ldstack.stylinggooglemap.data

import com.ldstack.stylinggooglemap.data.dto.Site2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class Repository
@Inject constructor() {
    fun getServerResponse(): Flow<List<Site2>> = flow {
        NetworkService.getCountries().sitesByDistrict.let { emit(it.data) }
    }.flowOn(Dispatchers.IO)
}