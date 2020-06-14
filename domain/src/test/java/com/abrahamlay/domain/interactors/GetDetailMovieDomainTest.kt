package com.abrahamlay.domain.interactors

import com.abrahamlay.domain.BaseDomainTestClass
import com.abrahamlay.domain.PostExecutionThread
import com.abrahamlay.domain.entities.DetailMovieModel
import com.abrahamlay.domain.repositories.MovieRepository
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Abraham Lay on 14/06/20.
 */
@RunWith(MockitoJUnitRunner::class)
class GetDetailMovieDomainTest : BaseDomainTestClass() {

    // SUT
    private lateinit var interactor: GetDetailMovie

    //Mocks
    @Mock
    private lateinit var mockPostExecutionThread: PostExecutionThread

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var mockFlowable: Flowable<DetailMovieModel>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        interactor = GetDetailMovie(repository, mockPostExecutionThread)
    }

    @Test
    fun test_GivenMovieModelId_WhenGetFavoriteMovieExecuted() {

        val params = GetDetailMovie.Params(API_KEY, dummyMovieModel.id)

        BDDMockito.given(repository.getMovieDetails(API_KEY, dummyMovieModel.id))
            .willReturn(mockFlowable)

        //when
        interactor.build(params)

        //then
        BDDMockito.then<PostExecutionThread>(mockPostExecutionThread).shouldHaveZeroInteractions()
    }
}