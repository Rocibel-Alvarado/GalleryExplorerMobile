package com.galleryExplorerMobile.domain.notification

import android.content.Context
import android.content.Intent
import com.galleryExplorerMobile.presentation.screen.home.MainActivity
import org.json.JSONObject

/**
 * Se encarga únicamente de la navegación: recibe el bloque "navigation" del JSON
 * y decide a qué pantalla va la navegacion.
 *
 * Formato esperado del JSON completo:
 * {
 *   "title": "...",
 *   "description": "...",
 *   "navigation": {
 *     "screen": "home" | "gallery" | "favorite"
 *     }
 *   "extras": { "promoId": "123" }  //opcional
 *
 * }
 */

class NavigationManager(private val context: Context) {

    enum class NavigationType(val value: String) {
        HOME("home"),
        GALLERY("gallery"),
        FAVORITES("favorites");

        companion object {
            fun fromValue(value: String?) = NavigationType.entries.find { it.value == value }
        }
    }

    companion object {
        const val EXTRA_DESTINATION_SCREEN = "extra_destination_screen"
    }

    /**
     * @param rawJson JSON completo de la notificación (el mismo que llegó del push)
     */

    fun navigateFromJson(rawJson: String) {
        val json = JSONObject(rawJson)
        val navigationJson = json.optJSONObject("navigation")
        val screenValue = navigationJson?.optString("screen")

        val screen = NavigationType.fromValue(screenValue) ?: NavigationType.HOME

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(EXTRA_DESTINATION_SCREEN, screen.value)
        }

        context.startActivity(intent)
    }
}