package com.galleryExplorerMobile.data.dto

import com.google.gson.annotations.SerializedName

data class PhotoDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("download_url")
    val downloadUrl: String,
)