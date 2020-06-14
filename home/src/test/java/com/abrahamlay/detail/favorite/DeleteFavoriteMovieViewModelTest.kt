package com.abrahamlay.detail.favorite

import com.abrahamlay.BaseHomeTestClass
import com.abrahamlay.MockitoHelper
import com.abrahamlay.domain.interactors.DeleteFavoriteMovie
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
class DeleteFavoriteMovieViewModelTest : BaseHomeTestClass() {
    //SUT
    lateinit var viewModel: DeleteFavoriteMovieViewModel

    @Mock
    lateinit var command: DeleteFavoriteMovie

    @Before
    fun setUp() {
        viewModel = DeleteFavoriteMovieViewModel(command)
    }

    @Test
    fun givenMovieId_whenDelete_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            //when
            viewModel.deleteMovie(dummyMovieModel)

            //then
            viewModel.favoriteMovieData.observeForTesting {
                BDDMockito.then(command).should()
                    .execute(MockitoHelper.anyObject(), MockitoHelper.anyObject())
            }
        }
    }
}