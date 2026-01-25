package com.galleryExplorerMobile.presentation.screen.gallery.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.galleryExplorerMobile.databinding.NavigationCardsItemBinding
import com.galleryExplorerMobile.domain.model.Photo

class PhotosCardsAdapter(
    private var cards: MutableList<Photo?>,
    private var favoriteIds: Set<String>,
    private val onFavoriteClick: (Photo?) -> Unit,
    private val onNotFavoriteClick: (Photo?) -> Unit
) : RecyclerView.Adapter<PhotosCardsAdapter.NavigationCardsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationCardsViewHolder {
        val binding = NavigationCardsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NavigationCardsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NavigationCardsViewHolder, position: Int) {
        val item = cards[position]
        holder.bind(item, favoriteIds.contains(item?.id))
    }

    override fun getItemId(position: Int): Long {
        return cards[position]?.id?.toLong() ?: 0
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    fun updateList(newList: List<Photo>, newFavoriteIds: Set<String>) {
        cards = newList.toMutableList()
        favoriteIds = newFavoriteIds
        notifyDataSetChanged()
    }

    fun updateItem(position: Int, photo: Photo, newFavoriteIds: Set<String>) {
        if (position != -1) {
            cards[position] = photo
            favoriteIds = newFavoriteIds
            notifyItemChanged(position)
        }
    }

    inner class NavigationCardsViewHolder(private val binding: NavigationCardsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(card: Photo?, isFavorite: Boolean) {
            card?.let { section ->
                binding.apply {
                    lottieFavorite.cancelAnimation()
                    lottieFavorite.visibility = View.GONE

                    cardTitle.text = section.author
                    binding.bindCardImage(section)
                    isFavorite(isFavorite)

                    binding.cardFavorite.setOnClickListener {
                        binding.cardNotFavorite.visibility = View.VISIBLE
                        onNotFavoriteClick(section)
                    }
                    binding.cardNotFavorite.setOnClickListener {
                        animationFavorite()
                        lottieFavorite.addAnimatorListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                lottieFavorite.visibility = View.GONE
                                onFavoriteClick(section)
                            }
                        })

                    }
                }
            }
        }

        private fun isFavorite(isFavorite: Boolean) {
            if (isFavorite) {
                binding.cardFavorite.visibility = View.VISIBLE
                binding.cardNotFavorite.visibility = View.GONE
            } else {
                binding.cardFavorite.visibility = View.GONE
                binding.cardNotFavorite.visibility = View.VISIBLE
            }
        }

        private fun animationFavorite() {
            binding.lottieFavorite.visibility = View.VISIBLE
            binding.lottieFavorite.speed = 3.5f
            binding.lottieFavorite.repeatCount = 0
            binding.lottieFavorite.playAnimation()
            binding.cardFavorite.visibility = View.VISIBLE
        }

        private fun NavigationCardsItemBinding.bindCardImage(
            section: Photo
        ) {
            if (section.downloadUrl.isNotEmpty()) {
                Glide.with(cardImage.context)
                    .load(section.downloadUrl)
                    .into(cardImage)
            }
        }
    }
}