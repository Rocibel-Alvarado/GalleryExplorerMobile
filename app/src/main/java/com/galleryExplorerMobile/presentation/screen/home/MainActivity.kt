package com.galleryExplorerMobile.presentation.screen.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.galleryExplorerMobile.R
import com.galleryExplorerMobile.core.base.BaseVMActivity
import com.galleryExplorerMobile.databinding.ActivityMainBinding
import com.galleryExplorerMobile.domain.notification.NavigationManager
import com.galleryExplorerMobile.presentation.screen.favorite.fragment.FavoriteFragment
import com.galleryExplorerMobile.presentation.screen.gallery.fragment.GalleryPhotosFragment
import com.galleryExplorerMobile.presentation.screen.notification.viewModel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseVMActivity<NotificationViewModel, ActivityMainBinding>() {

    private lateinit var notificationViewModel : NotificationViewModel

    override fun inicializarViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun onViewBindingCreated(savedInstanceState: Bundle?) {

        if (savedInstanceState == null) {
            // Solo mostramos el fragment inicial la primera vez que se crea la Activity,
            // para no duplicar fragments si la pantalla se recrea.
            handleNavigationExtra(intent, isInitial = true)
        }

        initBackStackListener()
        initView()
        applyBottomInset(binding.appBarMain.root)
        initNavBar()
        setButton()
        setNotification()
        selectNavItem(binding.appBarMain.linearLayoutHome)
    }

    // Se llama si la Activity YA estaba abierta y llega una nueva notificación
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleNavigationExtra(intent, isInitial = false)
    }

    private fun handleNavigationExtra(intent: Intent, isInitial: Boolean) {
        val screen = intent.getStringExtra(NavigationManager.EXTRA_DESTINATION_SCREEN)

        // Si no viene ningún destino y es la primera vez que se abre la Activity,
        // mostramos Home por defecto (la app se abrió normal, no desde una notificación).
        if (screen == null) {
            if (isInitial) supportFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
            return
        }

        when (screen) {
            NavigationManager.NavigationType.GALLERY.value -> {
                openFragment(GalleryPhotosFragment())
                selectNavItem(binding.appBarMain.linearLayoutGallery)
            }
            NavigationManager.NavigationType.FAVORITES.value -> {
                openFragment(FavoriteFragment())
                selectNavItem(binding.appBarMain.linearLayoutFavorite)
            }
            NavigationManager.NavigationType.HOME.value -> {
                selectNavItem(binding.appBarMain.linearLayoutHome)
                supportFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }

        }
    }

    private fun setNotification() {
        notificationViewModel = ViewModelProvider(this)[NotificationViewModel::class.java]
        notificationViewModel.getToken()
    }

    private fun initBackStackListener() {
        supportFragmentManager.addOnBackStackChangedListener {
            val hasFragments = supportFragmentManager.backStackEntryCount > 0
            binding.container.visibility = if (hasFragments) View.GONE else View.VISIBLE
            binding.frameLayout.visibility = if (hasFragments) View.VISIBLE else View.GONE
        }
    }

    private fun initView() {
        binding.textViewTitle.text = getString(R.string.text_title)
        binding.buttonAction.text = getString(R.string.text_explorer_gallery)
    }

    private fun setButton() {
        binding.buttonAction.setOnClickListener {
            openFragment(GalleryPhotosFragment())
        }
    }

    private fun initNavBar() {
        binding.appBarMain.linearLayoutHome.setOnClickListener {
            selectNavItem(it)
            binding.container.visibility = View.VISIBLE
            binding.frameLayout.visibility = View.GONE
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.appBarMain.linearLayoutGallery.setOnClickListener {
            selectNavItem(it)
            openFragment(GalleryPhotosFragment())
        }

        binding.appBarMain.linearLayoutFavorite.setOnClickListener {
            selectNavItem(it)
            openFragment(FavoriteFragment())
        }
    }

    private fun selectNavItem(selectedView: View) {
        binding.appBarMain.linearLayoutHome.isSelected = false
        binding.appBarMain.linearLayoutGallery.isSelected = false
        binding.appBarMain.linearLayoutFavorite.isSelected = false

        selectedView.isSelected = true
    }

    private fun openFragment(fragment: Fragment) {
        binding.container.visibility = View.GONE
        binding.frameLayout.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction()
            .replace(binding.frameLayout.id, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun showNavBar() {
        binding.appBarMain.root.visibility = View.VISIBLE
    }
}