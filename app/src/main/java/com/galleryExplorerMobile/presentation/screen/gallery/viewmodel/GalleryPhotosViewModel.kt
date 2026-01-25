package com.galleryExplorerMobile.presentation.screen.gallery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galleryExplorerMobile.di.IoDispatcher
import com.galleryExplorerMobile.domain.model.Photo
import com.galleryExplorerMobile.domain.usecase.GetPhotosUseCase
import com.galleryExplorerMobile.data.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryPhotosViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _getPhotosResult = MutableLiveData<List<Photo>?>()
    val getPhotosResult: LiveData<List<Photo>?> = _getPhotosResult

    fun onGetPhotosGallery() = viewModelScope.launch {
        getPhotosUseCase(dispatcher).collect { result ->
            when (result.status) {
                Resource.Status.ERROR -> {
                    // nothing
                }

                Resource.Status.API_ERROR -> {
                    // nothing
                }

                Resource.Status.SUCCESS -> {
                    _getPhotosResult.value = result.data
                }

                Resource.Status.LOADING -> {
                    // nothing
                }
            }
        }
    }

}