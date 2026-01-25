package com.galleryExplorerMobile.data.repository

import com.galleryExplorerMobile.data.local.dao.FavoriteDao
import com.galleryExplorerMobile.data.mapper.FavoriteMapper
import com.galleryExplorerMobile.domain.model.Favorite
import com.galleryExplorerMobile.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteDao,
    private val mapper: FavoriteMapper
) : FavoriteRepository {

    override fun getAllFavorites(): Flow<List<Favorite>> {
        return dao.getFavorites().map { entities ->
            entities.map { mapper.toDomain(it) }
        }
    }

    override suspend fun setFavorite(favorite: Favorite) {
        dao.setFavorite(mapper.toEntity(favorite))
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        dao.deleteFavorite(mapper.toEntity(favorite))
    }
}