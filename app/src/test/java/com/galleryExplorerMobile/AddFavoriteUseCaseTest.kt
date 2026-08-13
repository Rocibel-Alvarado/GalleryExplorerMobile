package com.galleryExplorerMobile

import com.galleryExplorerMobile.domain.model.Favorite
import com.galleryExplorerMobile.domain.repository.FavoriteRepository
import com.galleryExplorerMobile.domain.usecase.AddFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class AddFavoriteUseCaseTest {

    @Mock
    lateinit var repository: FavoriteRepository

    private lateinit var addFavoriteUseCase: AddFavoriteUseCase

    @Before
    fun onBefore() {
        //inicializa el mock
        MockitoAnnotations.openMocks(this)
        addFavoriteUseCase = AddFavoriteUseCase(repository)
    }

    @Test
    fun  `when add favorite` () = runBlocking {
        val favorite = Favorite(
            id = 1,
            name = "Google",
            url = "https://www.google.com"
        )

        addFavoriteUseCase(
            favorite = favorite,
            dispatcher = Dispatchers.Unconfined
        )

        //Comprueba que el mock repository recibió una llamada a setFavorite con este favorite
        verify(repository).setFavorite(favorite)
    }
}