package com.galleryExplorerMobile.data.notification

import com.galleryExplorerMobile.domain.notification.NotificationRepository
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val firebaseMessaging: FirebaseMessaging
) : NotificationRepository {

    override suspend fun getToken(): String {
        return firebaseMessaging.token.await()
    }
}
