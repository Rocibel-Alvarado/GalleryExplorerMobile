package com.galleryExplorerMobile.data.repository

import com.galleryExplorerMobile.data.utils.Resource
import retrofit2.Retrofit

abstract class BaseRepository constructor(
    private val retrofit: Retrofit
) {

    protected fun <T> getApi(apiClass: Class<T>): T {
        return retrofit.create(apiClass)
    }

    protected fun <T> parseResponse(response: retrofit2.Response<T>): Resource<T> {
        return if (response.isSuccessful) {
            Resource.success(response.body(), response.code())
        } else {
            Resource.apiError(" ", null, response.code())
        }
    }
}