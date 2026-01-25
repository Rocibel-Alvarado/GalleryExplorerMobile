package com.galleryExplorerMobile.data.mapper

import com.galleryExplorerMobile.data.local.entity.FavoriteEntity
import com.galleryExplorerMobile.domain.model.Favorite
import javax.inject.Inject

class FavoriteMapper @Inject constructor() {
    fun toDomain(entity: FavoriteEntity): Favorite {
        return Favorite(
            id = entity.id,
            name = entity.name,
            url = entity.url
        )
    }

    fun toEntity(favorite: Favorite): FavoriteEntity {
        return FavoriteEntity(
            id = favorite.id,
            name = favorite.name,
            url = favorite.url
        )
    }
}