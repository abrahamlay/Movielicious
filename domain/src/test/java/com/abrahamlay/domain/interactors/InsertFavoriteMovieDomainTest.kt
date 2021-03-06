package com.abrahamlay.domain.interactors

import com.abrahamlay.domain.BaseDomainTestClass
import com.abrahamlay.domain.PostExecutionThread
import com.abrahamlay.domain.repositories.MovieRepository
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
class InsertFavoriteMovieDomainTest : BaseDomainTestClass() {

    // SUT
    private lateinit var interactor: InsertFavoriteMovie

    // Mocks
    @Mock
    private lateinit var mockPostExecutionThread: PostExecutionThread

    @Mock
    private lateinit var repository: MovieRepository

    private var mockInt: Long = 0.toLong()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        interactor = InsertFavoriteMovie(repository, mockPostExecutionThread)
    }

    @Test
    fun test_GivenMovieModel_WhenInsertFavoriteMovieExecuted() {

        val params = InsertFavoriteMovie.Params(dummyMovieModel)

        BDDMockito.given(repository.insertFavoriteMovie(dummyMovieModel)).willReturn(mockInt)

        //when
        interactor.build(params)

        //then
        BDDMockito.then<PostExecutionThread>(mockPostExecutionThread).shouldHaveZeroInteractions()
    }
}