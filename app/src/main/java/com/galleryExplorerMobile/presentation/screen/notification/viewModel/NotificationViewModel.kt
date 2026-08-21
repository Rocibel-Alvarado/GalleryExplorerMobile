package com.galleryExplorerMobile.presentation.screen.notification.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galleryExplorerMobile.domain.notification.GetNotificationTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * 🎫 EL QUE PIDE EL TOKEN
 *
 * Esta clase se lo pide a Firebase,
 * y cuando lo tiene, lo guarda para que la pantalla (Home) lo pueda usar
 *
 */


@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationTokenUseCase: GetNotificationTokenUseCase
) : ViewModel() {

    fun getToken() {
        viewModelScope.launch {
            try {
                val token = getNotificationTokenUseCase()

                Log.d("FCM_TOKEN", "Token: $token")

            } catch (e: Exception) {
                Log.e("FCM_TOKEN", "Error obteniendo token", e)
            }
        }
    }
}