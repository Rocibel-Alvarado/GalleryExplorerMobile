package com.galleryExplorerMobile.domain.usecase

import com.galleryExplorerMobile.domain.model.Favorite
import com.galleryExplorerMobile.domain.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(dispatcher: CoroutineDispatcher): Flow<List<Favorite>> {
        return repository.getAllFavorites().flowOn(dispatcher)
    }
}