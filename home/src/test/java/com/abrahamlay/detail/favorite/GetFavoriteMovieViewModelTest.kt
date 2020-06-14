package com.abrahamlay.detail.favorite

import com.abrahamlay.BaseHomeTestClass
import com.abrahamlay.MockitoHelper
import com.abrahamlay.domain.interactors.GetFavoriteMovie
import com.abrahamlay.domain.interactors.InsertFavoriteMovie
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
class GetFavoriteMovieViewModelTest : BaseHomeTestClass() {
    //SUT
    lateinit var viewModel: GetFavoriteMovieViewModel

    @Mock
    lateinit var command: GetFavoriteMovie

    @Before
    fun setUp() {
        viewModel = GetFavoriteMovieViewModel(command)
    }

    @Test
    fun givenMovieId_whenGetFavoriteMovie_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            //when
            viewModel.getFavoriteMovie(dummyMovieModel.id)

            //then
            viewModel.favoriteMovieData.observeForTesting {
                BDDMockito.then(command).should()
                    .execute(MockitoHelper.anyObject(), MockitoHelper.anyObject())
            }
        }
    }
}