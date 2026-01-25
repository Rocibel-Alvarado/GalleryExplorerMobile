package com.galleryExplorerMobile.domain.usecase

import com.galleryExplorerMobile.domain.model.Favorite
import com.galleryExplorerMobile.domain.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(favorite: Favorite, dispatcher: CoroutineDispatcher) {
        withContext(dispatcher) {
            repository.setFavorite(favorite)
        }
    }
}