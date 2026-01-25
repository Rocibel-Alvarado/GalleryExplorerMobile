package com.galleryExplorerMobile.domain.repository

import com.galleryExplorerMobile.domain.model.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getAllFavorites(): Flow<List<Favorite>>
    suspend fun setFavorite(favorite: Favorite)
    suspend fun deleteFavorite(favorite: Favorite)
}