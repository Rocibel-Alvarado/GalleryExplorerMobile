package com.galleryExplorerMobile

import com.galleryExplorerMobile.domain.model.Favorite
import com.galleryExplorerMobile.domain.repository.FavoriteRepository
import com.galleryExplorerMobile.domain.usecase.DeleteFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class DeleteFavoriteUseCaseTest {

    @Mock
    lateinit var repository: FavoriteRepository

    private lateinit var deleteFavoriteUseCase: DeleteFavoriteUseCase

    @Before
    fun onBefore() {
        //inicializa el mock
        MockitoAnnotations.openMocks(this)
        deleteFavoriteUseCase = DeleteFavoriteUseCase(repository)
    }

    @Test
    fun  `when delete favorite` () = runBlocking {
        val favorite = Favorite(
            id = 1,
            name = "Google",
            url = "https://www.google.com"
        )

        deleteFavoriteUseCase(
            favorite = favorite,
            dispatcher = Dispatchers.Unconfined
        )

        //Comprueba que el mock repository recibió una llamada a setFavorite con este favorite
        verify(repository).deleteFavorite(favorite)
    }
}
