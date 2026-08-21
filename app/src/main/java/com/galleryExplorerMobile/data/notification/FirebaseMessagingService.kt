package com.galleryExplorerMobile.data.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject

/**
 * 📥 EL QUE RECIBE EL MENSAJE DE FIREBASE
 *
 * Es la puerta de entrada: Firebase despierta esta clase apenas llega un push.
 * Saca el mensaje, y se lo pasa a PushNotificationManager para que lo muestre.
 */

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("FCM_TOKEN", "Token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // El push manda un solo campo "payload", que contiene el JSON completo
        // ya armado con la forma exacta que espera la app (title, description, navigation).
        val data = remoteMessage.data
        val rawJson = data["payload"]

        if (rawJson != null) {
            val json = JSONObject(rawJson)
            val title = json.optString("title", "Nueva notificación")
            val body = json.optString("description", "")

            PushNotificationManager(applicationContext)
                .showNotification(title = title, body = body, rawJson = rawJson)
        }
    }
}