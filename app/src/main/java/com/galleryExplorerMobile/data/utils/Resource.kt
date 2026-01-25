package com.galleryExplorerMobile.data.utils

data class Resource<T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val code: Int? = null
) {
    companion object {
        fun <T> success(data: T?, code: Int? = null): Resource<T> =
            Resource(Status.SUCCESS, data, null, code)

        fun <T> error(message: String, data: T? = null): Resource<T> =
            Resource(Status.ERROR, data, message)

        fun <T> apiError(message: String, data: T? = null, code: Int? = null): Resource<T> =
            Resource(Status.API_ERROR, data, message, code)

        fun <T> loading(data: T? = null): Resource<T> =
            Resource(Status.LOADING, data, null)
    }

    enum class Status {
        SUCCESS, ERROR, API_ERROR, LOADING
    }

    fun <R> map(transform: (T?) -> R): Resource<R> {
        return Resource(status, transform(data), message, code)
    }
}