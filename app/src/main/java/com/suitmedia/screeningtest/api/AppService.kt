package com.suitmedia.screeningtest.api

import com.suitmedia.screeningtest.features.screenone.ProfileEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * App REST API access points
 */
interface AppService {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Response<ResultsResponse<ProfileEntity>>
}