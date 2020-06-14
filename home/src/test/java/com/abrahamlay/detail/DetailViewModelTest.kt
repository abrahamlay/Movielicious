package com.abrahamlay.detail

import com.abrahamlay.BaseHomeTestClass
import com.abrahamlay.MockitoHelper
import com.abrahamlay.domain.interactors.GetDetailMovie
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
class DetailViewModelTest : BaseHomeTestClass() {
    //SUT
    lateinit var viewModel: DetailViewModel

    @Mock
    lateinit var command: GetDetailMovie

    @Before
    fun setUp() {
        viewModel = DetailViewModel(command)
    }

    @Test
    fun givenMovieId_whenGetDetailMovie_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            //when
            viewModel.refreshDetail(dummyMovieModel.id)

            //then
            viewModel.detailData.observeForTesting {
                BDDMockito.then(command).should()
                    .execute(MockitoHelper.anyObject(), MockitoHelper.anyObject())
            }
        }
    }
}