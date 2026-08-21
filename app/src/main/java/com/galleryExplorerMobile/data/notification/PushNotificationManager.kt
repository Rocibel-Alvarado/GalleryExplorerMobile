package com.galleryExplorerMobile.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager as AndroidNotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.galleryExplorerMobile.presentation.screen.notification.screen.MessagingServiceActivity
import kotlin.jvm.java

/**
 * Se encarga de:
 * 1. Recibir el payload del push (por ejemplo desde FirebaseMessagingService.onMessageReceived)
 * 2. Mostrar la notificación del sistema
 * 3. Armar el Intent hacia NotificationActivity, pasando el JSON completo
 *
 * No decide la navegación interna (eso lo hace NavigationManager) — solo lleva
 * al usuario desde la notificación del sistema hasta la pantalla intermedia.
 */

class PushNotificationManager(private val context: Context) {

    companion object {
        private const val CHANNEL_ID = "default_channel"
        private const val CHANNEL_NAME = "Notificaciones"
        const val EXTRA_NOTIFICATION_JSON = "extra_notification_json"
    }

    init {
        createChannelIfNeeded()
    }

    /**
     * @param title Título mostrado en la notificación del sistema
     * @param body Cuerpo mostrado en la notificación del sistema
     * @param rawJson El JSON completo tal cual llega del backend (incluye el bloque "navigation")
     * @param notificationId Id único para esta notificación
     */

    fun showNotification(
        title: String,
        body: String,
        rawJson: String,
        notificationId: Int = System.currentTimeMillis().toInt()
    ) {
        val intent = buildActivityIntent(rawJson)

        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as AndroidNotificationManager
        manager.notify(notificationId, notification)
    }

    /**
     * Construye el Intent hacia la pantalla de notificación (NotificationActivity),
     * pasando el JSON crudo para que la Activity lo parsee.
     */

    private fun buildActivityIntent(rawJson: String): Intent {
        return Intent(context, MessagingServiceActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(EXTRA_NOTIFICATION_JSON, rawJson)
        }
    }

    private fun createChannelIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                AndroidNotificationManager.IMPORTANCE_HIGH
            )
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as AndroidNotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}