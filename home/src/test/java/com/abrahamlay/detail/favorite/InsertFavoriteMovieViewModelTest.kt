package com.abrahamlay.detail.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.abrahamlay.BaseHomeTestClass
import com.abrahamlay.MockitoHelper
import com.abrahamlay.base.subscriber.DefaultSubscriber
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.observeForTesting
import com.abrahamlay.domain.interactors.DeleteFavoriteMovie
import com.abrahamlay.domain.interactors.InsertFavoriteMovie
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Abraham Lay on 14/06/20.
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class InsertFavoriteMovieViewModelTest : BaseHomeTestClass() {
    //SUT
    lateinit var viewModel: InsertFavoriteMovieViewModel

    @Mock
    lateinit var command: InsertFavoriteMovie

    @Before
    fun setUp() {
        viewModel = InsertFavoriteMovieViewModel(command)
    }

    @Test
    fun givenMovie_whenInsert_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {

            //when
            viewModel.insertMovie(dummyMovieModel)

            //then
            viewModel.favoriteMovieData.observeForTesting {
                BDDMockito.then(command).should().execute(MockitoHelper.anyObject(), MockitoHelper.anyObject())
            }
        }
    }
}