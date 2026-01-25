package com.galleryExplorerMobile.presentation.screen.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.galleryExplorerMobile.R
import com.galleryExplorerMobile.core.base.BaseActivity
import com.galleryExplorerMobile.databinding.ActivityMainBinding
import com.galleryExplorerMobile.presentation.screen.favorite.fragment.FavoriteFragment
import com.galleryExplorerMobile.presentation.screen.gallery.fragment.GalleryPhotosFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun inicializarViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun onViewBindingCreated(savedInstanceState: Bundle?) {

        initBackStackListener()
        initView()
        applyBottomInset(binding.appBarMain.root)
        initNavBar()
        setButton()

        selectNavItem(binding.appBarMain.linearLayoutHome)
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