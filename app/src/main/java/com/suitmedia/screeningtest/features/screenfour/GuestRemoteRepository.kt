package com.suitmedia.screeningtest.features.screenfour

import com.suitmedia.screeningtest.data.resultMutableLiveDataRemote
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GuestRemoteRepository @Inject constructor(private val remoteDataSource: GuestRemoteDataSource) {
    fun observeListGuest(page: Int, perPage: Int) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListGuest(page, perPage) }
    )
}