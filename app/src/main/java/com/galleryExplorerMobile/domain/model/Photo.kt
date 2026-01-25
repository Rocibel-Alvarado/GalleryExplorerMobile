package com.galleryExplorerMobile.domain.model

data class Photo(
    val id: String,
    val author: String,
    val downloadUrl: String,
    val isFavorite: Boolean
)