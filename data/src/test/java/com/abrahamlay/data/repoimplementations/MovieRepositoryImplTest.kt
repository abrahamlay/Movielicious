package com.abrahamlay.data.repoimplementations

import com.abrahamlay.data.BaseDataTestClass
import com.abrahamlay.data.apis.MovieAPI
import com.abrahamlay.data.db.MovieDao
import com.abrahamlay.data.dtos.DetailMovieDto
import com.abrahamlay.data.dtos.MovieDto
import com.abrahamlay.data.dtos.ReviewDto
import com.abrahamlay.data.dtos.VideoDto
import com.abrahamlay.data.mapper.DetailMovieMapper
import com.abrahamlay.data.mapper.MovieMapper
import com.abrahamlay.data.mapper.ReviewMapper
import com.abrahamlay.data.mapper.VideoMapper
import com.abrahamlay.domain.entities.DetailMovieModel
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.domain.entities.ReviewModel
import com.abrahamlay.domain.entities.VideoModel
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
class MovieRepositoryImplTest : BaseDataTestClass() {
    //SUT
    private lateinit var repositoryImpl: MovieRepositoryImpl

    @Mock
    private lateinit var service: MovieAPI

    @Mock
    private lateinit var movieMapper: MovieMapper

    @Mock
    private lateinit var reviewMapper: ReviewMapper

    @Mock
    private lateinit var videoMapper: VideoMapper

    @Mock
    private lateinit var detailMovieMapper: DetailMovieMapper

    @Mock
    private lateinit var movieDtoFlowable: Flowable<MovieDto>

    @Mock
    private lateinit var movieModelsFlowable: Flowable<List<MovieModel>?>

    @Mock
    private lateinit var movieModelFlowable: Flowable<MovieModel?>

    @Mock
    private lateinit var detailMovieDtoFlowable: Flowable<DetailMovieDto>

    @Mock
    private lateinit var detailMovieModelFlowable: Flowable<DetailMovieModel?>

    @Mock
    private lateinit var videoDtoFlowable: Flowable<VideoDto>

    @Mock
    private lateinit var videoModelFlowable: Flowable<List<VideoModel>?>

    @Mock
    private lateinit var reviewDtoFlowable: Flowable<ReviewDto>

    @Mock
    private lateinit var reviewModelFlowable: Flowable<List<ReviewModel>?>

    @Mock
    private lateinit var movieDao: MovieDao


    @Before
    fun setUp() {
        repositoryImpl =
            MovieRepositoryImpl(
                service,
                movieDao,
                movieMapper,
                reviewMapper,
                videoMapper,
                detailMovieMapper
            )
    }

    @Test
    fun test_GivenAPIKey_WhenGetNowPlayingMovie_ThenShouldCallingGetNowPlayingMovieService() {

        //given
        BDDMockito.given(service.getNowPlayingMovies(API_KEY)).willReturn(movieDtoFlowable)
        BDDMockito.given(movieDtoFlowable.map(movieMapper)).willReturn(movieModelsFlowable)

        //when
        repositoryImpl.getNowPlayingMovies(API_KEY)

        //then
        BDDMockito.then(service).should().getNowPlayingMovies(API_KEY)
        BDDMockito.then(service).shouldHaveZeroInteractions()
        BDDMockito.then(movieDtoFlowable).should().map(movieMapper)
    }

    @Test
    fun test_GivenAPIKey_WhenGetTopRatedMovie_ThenShouldCallingGetTopRatedMovieService() {

        //given
        BDDMockito.given(service.getTopRatedMovies(API_KEY)).willReturn(movieDtoFlowable)
        BDDMockito.given(movieDtoFlowable.map(movieMapper)).willReturn(movieModelsFlowable)

        //when
        repositoryImpl.getTopRatedMovies(API_KEY)

        //then
        BDDMockito.then(service).should().getTopRatedMovies(API_KEY)
        BDDMockito.then(service).shouldHaveZeroInteractions()
        BDDMockito.then(movieDtoFlowable).should().map(movieMapper)
    }

    @Test
    fun test_GivenAPIKey_WhenGetPopularMovie_ThenShouldCallingGetPopularMovieService() {

        //given
        BDDMockito.given(service.getPopularMovies(API_KEY)).willReturn(movieDtoFlowable)
        BDDMockito.given(movieDtoFlowable.map(movieMapper)).willReturn(movieModelsFlowable)

        //when
        repositoryImpl.getPopularMovies(API_KEY)

        //then
        BDDMockito.then(service).should().getPopularMovies(API_KEY)
        BDDMockito.then(service).shouldHaveZeroInteractions()
        BDDMockito.then(movieDtoFlowable).should().map(movieMapper)
    }

    @Test
    fun test_GivenAPIKeyAndMovieId_WhenGetReviewsMovie_ThenShouldCallingGetReviewsMovieService() {

        //given
        BDDMockito.given(service.getReviews(dummyMovieDto.id, API_KEY))
            .willReturn(reviewDtoFlowable)
        BDDMockito.given(reviewDtoFlowable.map(reviewMapper)).willReturn(reviewModelFlowable)

        //when
        repositoryImpl.getReviews(API_KEY, dummyMovieDto.id)

        //then
        BDDMockito.then(service).should().getReviews(dummyMovieDto.id, API_KEY)
        BDDMockito.then(service).shouldHaveZeroInteractions()
        BDDMockito.then(reviewDtoFlowable).should().map(reviewMapper)
    }

    @Test
    fun test_GivenAPIKeyAndMovieId_WhenGetVideoMovie_ThenShouldCallingGetVideoMovieService() {

        //given
        BDDMockito.given(service.getVideo(dummyMovieDto.id, API_KEY))
            .willReturn(videoDtoFlowable)
        BDDMockito.given(videoDtoFlowable.map(videoMapper)).willReturn(videoModelFlowable)

        //when
        repositoryImpl.getVideo(API_KEY, dummyMovieDto.id)

        //then
        BDDMockito.then(service).should().getVideo(dummyMovieDto.id, API_KEY)
        BDDMockito.then(service).shouldHaveZeroInteractions()
        BDDMockito.then(videoDtoFlowable).should().map(videoMapper)
    }

    @Test
    fun test_GivenAPIKeyAndMovieId_WhenGetDetailMovie_ThenShouldCallingGetDetailMovieService() {

        //given
        BDDMockito.given(service.getMovieDetails(dummyMovieDto.id, API_KEY))
            .willReturn(detailMovieDtoFlowable)
        BDDMockito.given(detailMovieDtoFlowable.map(detailMovieMapper))
            .willReturn(detailMovieModelFlowable)

        //when
        repositoryImpl.getMovieDetails(API_KEY, dummyMovieDto.id)

        //then
        BDDMockito.then(service).should().getMovieDetails(dummyMovieDto.id, API_KEY)
        BDDMockito.then(service).shouldHaveZeroInteractions()
        BDDMockito.then(detailMovieDtoFlowable).should().map(detailMovieMapper)
    }

    @Test
    fun test_GivenNull_WhenGetFavoriteMovies_ThenShouldCallingGetFavoriteMoviesService() {
        //given
        BDDMockito.given(movieDao.selectFavoriteMovie())
            .willReturn(movieModelsFlowable)

        //when
        repositoryImpl.getFavoriteMovies()

        //then
        BDDMockito.then(movieDao).should().selectFavoriteMovie()
        BDDMockito.then(movieDao).shouldHaveZeroInteractions()
    }

    @Test
    fun test_GivenMovieId_WhenGetFavoriteMovie_ThenShouldCallingGetFavoriteMovieService() {
        //given
        BDDMockito.given(movieDao.select(dummyMovieDto.id))
            .willReturn(movieModelFlowable)

        //when
        repositoryImpl.getFavoriteMovie(dummyMovieDto.id)

        //then
        BDDMockito.then(movieDao).should().select(dummyMovieDto.id)
        BDDMockito.then(service).shouldHaveZeroInteractions()
    }

    @Test
    fun test_GivenNull_WhenInsertFavoriteMovie_ThenShouldCallingInsertFavoriteMovieService() {
        //given
        BDDMockito.given(movieDao.insert(dummyMovieModel))
            .willReturn(0.toLong())

        //when
        repositoryImpl.insertFavoriteMovie(dummyMovieModel)

        //then
        BDDMockito.then(movieDao).should().insert(dummyMovieModel)
        BDDMockito.then(movieDao).shouldHaveZeroInteractions()
    }

    @Test
    fun test_GivenNull_WhenDeleteFavoriteMovie_ThenShouldCallingDeleteFavoriteMovieService() {
        //given
        BDDMockito.given(movieDao.delete(dummyMovieModel))
            .willReturn(0)

        //when
        repositoryImpl.deleteFavoriteMovie(dummyMovieModel)

        //then
        BDDMockito.then(movieDao).should().delete(dummyMovieModel)
        BDDMockito.then(movieDao).shouldHaveZeroInteractions()
    }


}