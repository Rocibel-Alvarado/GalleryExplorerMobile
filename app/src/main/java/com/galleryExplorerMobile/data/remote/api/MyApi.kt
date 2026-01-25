package com.galleryExplorerMobile.data.remote.api

import com.galleryExplorerMobile.data.dto.PhotoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApi {

    @GET("v2/list")
    suspend fun getPhotos(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<List<PhotoDto>>
}