package com.galleryExplorerMobile.data.repository

import com.galleryExplorerMobile.data.mapper.PhotoMapper
import com.galleryExplorerMobile.data.remote.api.MyApi
import com.galleryExplorerMobile.domain.repository.PhotoRepository
import com.galleryExplorerMobile.domain.model.Photo
import com.galleryExplorerMobile.data.utils.Resource
import retrofit2.Retrofit
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    retrofit: Retrofit,
    private val photoMapper: PhotoMapper
) : BaseRepository(retrofit), PhotoRepository {

    override suspend fun getPhotos(): Resource<List<Photo>> {
        val response = getApi(MyApi::class.java).getPhotos()
        val resourceDto = parseResponse(response)

        return resourceDto.map { dtoList ->
            dtoList?.map { dto ->
                photoMapper.toDomain(dto)
            } ?: emptyList()
        }
    }
}