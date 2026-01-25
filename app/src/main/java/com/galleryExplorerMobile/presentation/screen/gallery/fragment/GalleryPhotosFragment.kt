package com.galleryExplorerMobile.presentation.screen.gallery.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.galleryExplorerMobile.R
import com.galleryExplorerMobile.presentation.screen.common.adapter.SkeletonAdapter
import com.galleryExplorerMobile.core.base.BaseVMFragment
import com.galleryExplorerMobile.databinding.FragmentGalleryPhotosBinding
import com.galleryExplorerMobile.domain.model.Favorite
import com.galleryExplorerMobile.domain.model.Photo
import com.galleryExplorerMobile.presentation.screen.favorite.viewModel.FavoriteViewModel
import com.galleryExplorerMobile.presentation.screen.gallery.adapter.PhotosCardsAdapter
import com.galleryExplorerMobile.presentation.screen.gallery.viewmodel.GalleryPhotosViewModel
import com.galleryExplorerMobile.presentation.screen.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryPhotosFragment : BaseVMFragment<GalleryPhotosViewModel, FragmentGalleryPhotosBinding>() {

    override lateinit var viewModel: GalleryPhotosViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    private var lastPhotosFromService: List<Photo> = emptyList()
    private var favoriteIds: Set<String> = emptySet()
    private lateinit var navigationCardsAdapter: PhotosCardsAdapter
    private lateinit var skeletonAdapter: SkeletonAdapter


    override fun inicializarViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGalleryPhotosBinding {
        return FragmentGalleryPhotosBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewSkeleton(true)
    }

    override fun onViewModelCreated() {
        viewModel = ViewModelProvider(this)[GalleryPhotosViewModel::class.java]
        favoriteViewModel = ViewModelProvider(requireActivity())[FavoriteViewModel::class.java]

        viewModel.onGetPhotosGallery()
        favoriteViewModel.loadFavorites()

        setupObservers()

    }

    private fun setupObservers() {

        viewModel.getPhotosResult.observe(viewLifecycleOwner) { photos ->
            if (!photos.isNullOrEmpty()) {
                lastPhotosFromService = photos
                viewSkeleton(false)
                setupAdapter()
                updateAdapterList()
            }
        }

        favoriteViewModel.favorites.observe(viewLifecycleOwner) { favorites ->
            favoriteIds = favorites.map { it.id.toString() }.toSet()
            updateAdapterList()
        }
    }

    private fun viewSkeleton(isSkeleton: Boolean) {
        if (isSkeleton) {
            binding.galleryCards.visibility = View.GONE
            binding.skeletonCards.visibility = View.VISIBLE

            if (!::skeletonAdapter.isInitialized) {
                skeletonAdapter = SkeletonAdapter()
                binding.skeletonCards.apply {
                    layoutManager = GridLayoutManager(requireContext(), 2)
                    adapter = skeletonAdapter
                }
            }
        } else {
            binding.galleryCards.visibility = View.VISIBLE
            binding.skeletonCards.visibility = View.GONE
        }
    }

    private fun setupAdapter() {
        (requireActivity() as MainActivity).showNavBar()

        navigationCardsAdapter = PhotosCardsAdapter(
            emptyList<Photo?>().toMutableList(),
            favoriteIds,
            onFavoriteClick = { photo ->
                val favorite = Favorite(photo?.id?.toInt() ?: 0, photo?.author ?: getString(R.string.empty_text), photo?.downloadUrl ?: getString(R.string.empty_text))
                favoriteViewModel.addFavorite(favorite)

                val position = lastPhotosFromService.indexOfFirst { it.id == photo?.id }
                if (position != -1 && photo != null) {
                    binding.galleryCards.post {
                        navigationCardsAdapter.updateItem(position, photo.copy(isFavorite = true), favoriteIds)
                    }
                }
            },
            onNotFavoriteClick = { photo ->
                val favorite = Favorite(photo?.id?.toInt() ?: 0, photo?.author ?: getString(R.string.empty_text), photo?.downloadUrl ?: getString(R.string.empty_text))
                favoriteViewModel.deleteFavorite(favorite)

                val position = lastPhotosFromService.indexOfFirst { it.id == photo?.id }
                if (position != -1 && photo != null) {
                    val updatedPhoto = photo.copy(isFavorite = false)
                    navigationCardsAdapter.updateItem(position, updatedPhoto, favoriteIds)
                }
            }
        )

        binding.galleryCards.apply {
            adapter = navigationCardsAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun updateAdapterList() {
        if (lastPhotosFromService.isEmpty()) return

        val mergedList = lastPhotosFromService.map { photo ->
            photo.copy(
                id = photo.id,
                author = photo.author,
                downloadUrl = photo.downloadUrl,
                isFavorite = photo.isFavorite
            )
        }
        navigationCardsAdapter.updateList(mergedList, favoriteIds)
    }
}
