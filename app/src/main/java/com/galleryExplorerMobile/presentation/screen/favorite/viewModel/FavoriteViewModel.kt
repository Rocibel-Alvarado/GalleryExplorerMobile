package com.galleryExplorerMobile.presentation.screen.favorite.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galleryExplorerMobile.di.IoDispatcher
import com.galleryExplorerMobile.domain.model.Favorite
import com.galleryExplorerMobile.domain.usecase.AddFavoriteUseCase
import com.galleryExplorerMobile.domain.usecase.DeleteFavoriteUseCase
import com.galleryExplorerMobile.domain.usecase.GetFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,

    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _favorites = MutableLiveData<List<Favorite>>(emptyList())
    val favorites: LiveData<List<Favorite>> = _favorites

    fun loadFavorites() {
        viewModelScope.launch {
            getFavoritesUseCase(dispatcher).collect { favorites ->
                _favorites.value = favorites
            }
        }
    }


    fun deleteFavorite(favorite: Favorite) {
        viewModelScope.launch {
            deleteFavoriteUseCase(favorite, dispatcher)
        }
    }

    fun addFavorite(favorite: Favorite) {
        viewModelScope.launch {
            addFavoriteUseCase(favorite, dispatcher)
        }
    }
}