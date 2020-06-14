package com.abrahamlay.domain.interactors

import com.abrahamlay.domain.BaseDomainTestClass
import com.abrahamlay.domain.PostExecutionThread
import com.abrahamlay.domain.entities.ReviewModel
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
class GetReviewsDomainTest : BaseDomainTestClass() {

    // SUT
    private lateinit var interactor: GetReviews

    //Mocks
    @Mock
    private lateinit var mockPostExecutionThread: PostExecutionThread

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var mockFlowable: Flowable<List<ReviewModel>>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        interactor = GetReviews(repository, mockPostExecutionThread)
    }

    @Test
    fun test_GivenAPIKeyAndMovieId_WhenGetReviewsExecuted() {
        val params = GetReviews.Params(API_KEY, dummyMovieModel.id)
        BDDMockito.given(repository.getReviews(API_KEY, dummyMovieModel.id))
            .willReturn(mockFlowable)

        //when
        interactor.build(params)

        //then
        BDDMockito.then<PostExecutionThread>(mockPostExecutionThread).shouldHaveZeroInteractions()
    }
}