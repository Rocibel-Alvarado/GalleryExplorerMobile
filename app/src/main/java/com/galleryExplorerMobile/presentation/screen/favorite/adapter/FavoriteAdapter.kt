package com.galleryExplorerMobile.presentation.screen.favorite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.galleryExplorerMobile.databinding.NavigationCardsItemBinding
import com.galleryExplorerMobile.domain.model.Favorite

class FavoriteAdapter(
    private var favorites: List<Favorite?>,
    private val onFavoriteClick: (Favorite?) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.NavigationCardsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationCardsViewHolder {
        val binding = NavigationCardsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NavigationCardsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NavigationCardsViewHolder, position: Int) {
        val item = favorites[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    fun updateListFavorites(newList: List<Favorite>) {
        favorites = newList
        notifyDataSetChanged()
    }

    inner class NavigationCardsViewHolder(private val binding: NavigationCardsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(card: Favorite?) {
            card?.let { section ->
                binding.apply {
                    cardTitle.text = section.name
                    binding.bindCardImage(section)
                    binding.cardFavorite.apply {
                        visibility = View.VISIBLE
                        setOnClickListener {
                            onFavoriteClick(section)
                        }
                    }
                }
            }
        }

        private fun NavigationCardsItemBinding.bindCardImage(
            section: Favorite
        ) {
            if (section.url.isNotEmpty()) {
                Glide.with(cardImage.context)
                    .load(section.url)
                    .into(cardImage)

            }
        }
    }
}