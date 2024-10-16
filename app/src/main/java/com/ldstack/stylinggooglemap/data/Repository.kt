package com.ldstack.stylinggooglemap.data

import com.ldstack.stylinggooglemap.data.dto.Site2
import com.ldstack.stylinggooglemap.data.dto.SitesByDistrict2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class Repository
@Inject constructor() {
    fun getServerResponse(page: Int): Flow<SitesByDistrict2> = flow {
        NetworkService.getCountries(page).sitesByDistrict.let { emit(it) }
    }.flowOn(Dispatchers.IO)
}