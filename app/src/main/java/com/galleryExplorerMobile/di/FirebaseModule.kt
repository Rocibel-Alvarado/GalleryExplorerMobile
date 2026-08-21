package com.galleryExplorerMobile.di

import com.galleryExplorerMobile.data.notification.NotificationRepositoryImpl
import com.galleryExplorerMobile.domain.notification.NotificationRepository
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

class FirebaseModule {

    @Module
    @InstallIn(SingletonComponent::class)
    object FirebaseModule {

        @Provides
        @Singleton
        fun provideFirebaseMessaging(): FirebaseMessaging {
            return FirebaseMessaging.getInstance()
        }
    }

    @Module
    @InstallIn(SingletonComponent::class) // o ViewModelComponent, según tu caso
    abstract class NotificationRepositoryModule {

        @Binds
        abstract fun bindNotificationRepository(
            impl: NotificationRepositoryImpl
        ): NotificationRepository
    }
}