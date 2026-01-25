package com.galleryExplorerMobile.di

import com.galleryExplorerMobile.data.repository.FavoriteRepositoryImpl
import com.galleryExplorerMobile.data.repository.PhotoRepositoryImpl
import com.galleryExplorerMobile.domain.repository.FavoriteRepository
import com.galleryExplorerMobile.domain.repository.PhotoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPhotoRepository(
        impl: PhotoRepositoryImpl
    ): PhotoRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(
        impl: FavoriteRepositoryImpl
    ): FavoriteRepository
}