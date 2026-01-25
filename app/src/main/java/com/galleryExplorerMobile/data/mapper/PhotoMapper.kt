package com.galleryExplorerMobile.data.mapper

import com.galleryExplorerMobile.data.dto.PhotoDto
import com.galleryExplorerMobile.domain.model.Photo
import javax.inject.Inject

class PhotoMapper @Inject constructor() {

    fun toDomain(dto: PhotoDto, isFavorite: Boolean = false): Photo {
        return Photo(
            id = dto.id,
            author = dto.author,
            downloadUrl = dto.downloadUrl,
            isFavorite = isFavorite
        )
    }
}