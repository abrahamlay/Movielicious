package com.abrahamlay.domain.interactors

import com.abrahamlay.domain.BaseDomainTestClass
import com.abrahamlay.domain.PostExecutionThread
import com.abrahamlay.domain.entities.MovieModel
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
class GetPopularMoviesDomainTest : BaseDomainTestClass() {

    // SUT
    private lateinit var interactor: GetPopularMovies

    //Mocks
    @Mock
    private lateinit var mockPostExecutionThread: PostExecutionThread

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var mockFlowable: Flowable<List<MovieModel>?>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        interactor = GetPopularMovies(repository, mockPostExecutionThread)
    }

    @Test
    fun test_GivenAPIKey_WhenGetPopularMoviesExecuted() {
        val params = GetPopularMovies.Params(API_KEY)
        BDDMockito.given(repository.getPopularMovies(API_KEY)).willReturn(mockFlowable)

        //when
        interactor.build(params)

        //then
        BDDMockito.then<PostExecutionThread>(mockPostExecutionThread).shouldHaveZeroInteractions()
    }
}