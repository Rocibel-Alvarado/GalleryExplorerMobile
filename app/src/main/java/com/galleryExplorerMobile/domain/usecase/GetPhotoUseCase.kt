package com.galleryExplorerMobile.domain.usecase

import com.galleryExplorerMobile.domain.repository.PhotoRepository
import com.galleryExplorerMobile.domain.model.Photo
import com.galleryExplorerMobile.data.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    operator fun invoke(dispatcher: CoroutineDispatcher): Flow<Resource<List<Photo>>> = flow {
        emit(Resource.loading())
        try {
            val data = repository.getPhotos()
            emit(data)
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Error"))
        }
    }.flowOn(dispatcher)
}