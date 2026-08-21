package com.galleryExplorerMobile.domain.notification

import javax.inject.Inject

class GetNotificationTokenUseCase @Inject constructor(
    private val repository: NotificationRepository
) {

    suspend operator fun invoke(): String {
        return repository.getToken()
    }
}
