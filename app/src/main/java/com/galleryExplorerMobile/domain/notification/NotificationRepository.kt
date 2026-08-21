package com.galleryExplorerMobile.domain.notification

interface NotificationRepository {

    suspend fun getToken(): String
}