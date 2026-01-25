package com.galleryExplorerMobile.di

import com.galleryExplorerMobile.domain.repository.FavoriteRepository
import com.galleryExplorerMobile.domain.repository.PhotoRepository
import com.galleryExplorerMobile.domain.usecase.AddFavoriteUseCase
import com.galleryExplorerMobile.domain.usecase.DeleteFavoriteUseCase
import com.galleryExplorerMobile.domain.usecase.GetFavoritesUseCase
import com.galleryExplorerMobile.domain.usecase.GetPhotosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetPhotosUseCase(
        photoRepository: PhotoRepository
    ): GetPhotosUseCase {
        return GetPhotosUseCase(photoRepository)
    }

    @Provides
    @Singleton
    fun provideGetFavoritesUseCase(
        favoriteRepository: FavoriteRepository
    ): GetFavoritesUseCase {
        return GetFavoritesUseCase(favoriteRepository)
    }

    @Provides
    @Singleton
    fun provideAddFavoriteUseCase(
        favoriteRepository: FavoriteRepository
    ): AddFavoriteUseCase {
        return AddFavoriteUseCase(favoriteRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteFavoriteUseCase(
        favoriteRepository: FavoriteRepository
    ): DeleteFavoriteUseCase {
        return DeleteFavoriteUseCase(favoriteRepository)
    }
}