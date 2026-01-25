package com.galleryExplorerMobile.domain.repository

import com.galleryExplorerMobile.domain.model.Photo
import com.galleryExplorerMobile.data.utils.Resource

interface PhotoRepository {
    suspend fun getPhotos(): Resource<List<Photo>>
}