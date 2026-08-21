package com.galleryExplorerMobile.presentation.screen.notification.screen

import android.os.Bundle
import com.galleryExplorerMobile.core.base.BaseActivity
import com.galleryExplorerMobile.data.notification.PushNotificationManager
import com.galleryExplorerMobile.databinding.ActivityMessagingServiceBinding
import com.galleryExplorerMobile.domain.notification.NavigationManager
import org.json.JSONObject

/**
 * 🔔 LA PANTALLA DE LA NOTIFICACIÓN
 *
 * Se abre cuando tocas la notificación. Muestra el título y la descripción,
 * y si tocas el botón, le avisa a NavigationManager para que te lleve.
 * Los pulgares solo guardan si te gustó o no.
 */

class MessagingServiceActivity : BaseActivity<ActivityMessagingServiceBinding>() {

    private lateinit var rawJson: String
    private lateinit var navigationManager: NavigationManager

    override fun inicializarViewBinding(): ActivityMessagingServiceBinding =
        ActivityMessagingServiceBinding.inflate(layoutInflater)

    override fun onViewBindingCreated(savedInstanceState: Bundle?) {

        navigationManager = NavigationManager(this)

        rawJson = intent.getStringExtra(PushNotificationManager.EXTRA_NOTIFICATION_JSON) ?: run {
            finish()
            return
        }

        renderData(rawJson)
        setupListeners(rawJson)
    }

    private fun renderData(rawJson: String) {
        val json = JSONObject(rawJson)

        binding.tvTitle.text = json.optString("title", "Notificación")
        binding.tvDescription.text = json.optString("description", "")
    }

    private fun setupListeners(rawJson: String) {
        binding.btnAction.setOnClickListener {
            navigationManager.navigateFromJson(rawJson)
        }

        binding.btnThumbUp.setOnClickListener {
            // Aquí registras la valoración positiva (analytics / backend)
        }

        binding.btnThumbDown.setOnClickListener {
            // Aquí registras la valoración negativa (analytics / backend)
        }
    }
}