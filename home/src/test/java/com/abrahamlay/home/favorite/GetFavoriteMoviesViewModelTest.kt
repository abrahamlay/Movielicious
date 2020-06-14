package com.abrahamlay.home.favorite

import com.abrahamlay.BaseHomeTestClass
import com.abrahamlay.MockitoHelper
import com.abrahamlay.domain.interactors.GetFavoriteMovies
import com.abrahamlay.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Abraham Lay on 14/06/20.
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetFavoriteMoviesViewModelTest : BaseHomeTestClass() {
    //SUT
    lateinit var viewModel: GetFavoriteMoviesViewModel

    @Mock
    lateinit var command: GetFavoriteMovies

    @Before
    fun setUp() {
        viewModel = GetFavoriteMoviesViewModel(command)
    }

    @Test
    fun givenMovieId_whenGetFavoritesMovie_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            //when
            viewModel.refreshMovie()

            //then
            viewModel.favoriteMovieData.observeForTesting {
                BDDMockito.then(command).should()
                    .execute(MockitoHelper.anyObject(), MockitoHelper.anyObject())
            }
        }
    }
}