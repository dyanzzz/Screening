package com.suitmedia.screeningtest.features.screenfour

import com.suitmedia.screeningtest.api.AppService
import com.suitmedia.screeningtest.api.BaseDataSource
import javax.inject.Inject

class GuestRemoteDataSource @Inject constructor(private val service: AppService) : BaseDataSource() {
    suspend fun requestListGuest(page: Int, perPage: Int) = getResult {
        service.getUsers(page, perPage)
    }
}