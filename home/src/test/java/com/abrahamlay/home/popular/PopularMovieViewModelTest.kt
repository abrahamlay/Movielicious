package com.abrahamlay.home.popular

import com.abrahamlay.BaseHomeTestClass
import com.abrahamlay.MockitoHelper
import com.abrahamlay.domain.interactors.GetPopularMovies
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
class PopularMovieViewModelTest : BaseHomeTestClass() {
    //SUT
    lateinit var viewModel: PopularMovieViewModel

    @Mock
    lateinit var command: GetPopularMovies

    @Before
    fun setUp() {
        viewModel = PopularMovieViewModel(command)
    }

    @Test
    fun givenMovieId_whenGetPopularMovie_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            //when
            viewModel.refreshMovie()

            //then
            viewModel.movieData.observeForTesting {
                BDDMockito.then(command).should()
                    .execute(MockitoHelper.anyObject(), MockitoHelper.anyObject())
            }
        }
    }
}