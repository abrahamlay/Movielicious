package com.abrahamlay.home.nowplaying

import com.abrahamlay.BaseHomeTestClass
import com.abrahamlay.MockitoHelper
import com.abrahamlay.domain.interactors.GetNowPlayingMovies
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
class NowPlayingMovieViewModelTest : BaseHomeTestClass() {
    //SUT
    lateinit var viewModel: NowPlayingMovieViewModel

    @Mock
    lateinit var command: GetNowPlayingMovies

    @Before
    fun setUp() {
        viewModel = NowPlayingMovieViewModel(command)
    }

    @Test
    fun givenMovieId_whenGetNowPlayingMovie_shouldReturnSuccess() {
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