package com.abrahamlay

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abrahamlay.data.dtos.*
import com.abrahamlay.detail.favorite.TestCoroutineRule
import com.abrahamlay.domain.entities.MovieModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
open class BaseHomeTestClass {
    @get: Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get: Rule
    val testCoroutineRule = TestCoroutineRule()

    val API_KEY = "e1364e3bc8f9d46c4a09586973081f96"
    val dummyMovieModel = MovieModel(
        2,
        1,
        false,
        2.0,
        "Spiderman",
        16.0,
        "123456.png",
        "en-us",
        "Spiderman",
        listOf(1, 2, 3),
        "678910.png",
        true,
        "lorem ipsum bla bla bla",
        "2020-06-18"
    )

    val dummyMovieDto = MovieDto.Movie(
        2,
        1,
        false,
        2.0,
        "Spiderman",
        16.0,
        "123456.png",
        "en-us",
        "Spiderman",
        listOf(1, 2, 3),
        "678910.png",
        true,
        "lorem ipsum bla bla bla",
        "2020-06-18"
    )

    val dummyDetailMovieDto = DetailMovieDto(
        true,
        "678910.png",
        false,
        1000000,
        listOf(DetailMovieDto.Genre(1, "Action")),
        "http://test.com",
        1,
        "123456",
        "en-us",
        "Spiderman",
        "lorem ipsum bla bla bla",
        14.606,
        "123456.png",
        listOf(DetailMovieDto.ProductionCompany(1, "213456.png", "Disney", "US")),
        listOf(DetailMovieDto.ProductionCountry("21314", "US")),
        "2020-06-18",
        12312,
        678687,
        listOf(DetailMovieDto.SpokenLanguage("21314", "EN")),
        "Released",
        "Tiada jaring lebih kuat dari jaringku",
        "Spiderman",
        true,
        12.312,
        1203021
    )

    val dummyMoviesDto = MovieDto(
        2,
        1,
        100,
        arrayListOf(dummyMovieDto)
    )
    val dummyResultReviewDto =
        ResultReview("Jim Begglin", "bla bla bla bla", "12345", "http://test.com")
    val dummyReviewDto = ReviewDto(1, 1, arrayListOf(dummyResultReviewDto), 100, 100)

    val dummyResultVideoDto =
        ResultVideo("123", "12341", "12341", "213123", "Spiderman", "http://test.com", 2, "trailer")
    val dummyVideoDto = VideoDto(1, arrayListOf(dummyResultVideoDto))


}
