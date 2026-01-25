package com.galleryExplorerMobile.presentation.screen.favorite.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.galleryExplorerMobile.R
import com.galleryExplorerMobile.core.base.BaseVMFragment
import com.galleryExplorerMobile.core.ui.SnackBarManager
import com.galleryExplorerMobile.core.ui.SnackBarModel
import com.galleryExplorerMobile.databinding.FragmentFavoriteBinding
import com.galleryExplorerMobile.domain.model.Favorite
import com.galleryExplorerMobile.presentation.screen.common.adapter.SkeletonAdapter
import com.galleryExplorerMobile.presentation.screen.favorite.adapter.FavoriteAdapter
import com.galleryExplorerMobile.presentation.screen.favorite.viewModel.FavoriteViewModel
import com.galleryExplorerMobile.presentation.screen.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseVMFragment<FavoriteViewModel, FragmentFavoriteBinding>() {

    override lateinit var viewModel: FavoriteViewModel
    private lateinit var skeletonAdapter: SkeletonAdapter


    override fun inicializarViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFavoriteBinding {
        return FragmentFavoriteBinding.inflate(inflater, container, false)
    }


    override fun onViewModelCreated() {
        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        viewModel.favorites.observe(viewLifecycleOwner) { favoritesList ->
            if (favoritesList.isNullOrEmpty()) {
                binding.favoritesCards.adapter?.let { adapter ->
                    if (adapter is FavoriteAdapter) {
                        adapter.updateListFavorites(emptyList())
                    }
                }
                viewSkeleton(true)
                setSnackBar()
            } else {
                viewSkeleton(false)
                hideSnackBar()
                initView(favoritesList)
            }
        }

        viewModel.loadFavorites()
    }

    private fun initView(list: List<Favorite>) {
        (requireActivity() as MainActivity).showNavBar()

        val navigationCardsAdapter = FavoriteAdapter(list) { photo ->
            photo?.let {
                val favorite = Favorite(it.id, it.name, it.url)
                viewModel.deleteFavorite(favorite)
            }
        }
        binding.favoritesCards.apply {
            adapter = navigationCardsAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
        navigationCardsAdapter.updateListFavorites(list)
    }

    private fun viewSkeleton(isSkeleton: Boolean) {
        if (isSkeleton) {
            binding.skeletonCards.visibility = View.VISIBLE
            binding.favoritesCards.visibility = View.GONE

            skeletonAdapter = SkeletonAdapter()
            binding.skeletonCards.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = skeletonAdapter
            }
        } else {
            binding.skeletonCards.visibility = View.GONE
            binding.favoritesCards.visibility = View.VISIBLE
        }
    }

    private fun setSnackBar() {
        binding.coordinatorLayoutSnackbar.visibility = View.VISIBLE
        val snackBarModel = SnackBarModel(
            getString(R.string.error_load_plans),
            SnackBarModel.SNACKBAR_TIPO.ERROR
        )
        SnackBarManager.showSnackbar(requireContext(), snackBarModel, binding.viewSnackBar)
    }

    private fun hideSnackBar() {
        binding.coordinatorLayoutSnackbar.visibility = View.GONE
    }
}