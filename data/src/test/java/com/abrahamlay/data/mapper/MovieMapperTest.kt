package com.abrahamlay.data.mapper

import com.abrahamlay.data.BaseDataTestClass
import org.hamcrest.core.Is
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

/**
 * Created by Abraham Lay on 14/06/20.
 */
class MovieMapperTest : BaseDataTestClass() {
    private lateinit var movieMapper: MovieMapper

    @Before
    fun setUp() {
        movieMapper = MovieMapper()
    }

    @Test
    @Throws(Exception::class)
    fun test_TransformPropertyDto() {
        val properties = movieMapper.apply(dummyMoviesDto)

        assertThat<Int>(properties?.get(0)?.voteCount, Is.`is`<Int>(dummyMovieDto.voteCount))
        assertThat<Int>(properties?.get(0)?.id, Is.`is`<Int>(dummyMovieDto.id))
        assertThat<Boolean>(properties?.get(0)?.video, Is.`is`<Boolean>(dummyMovieDto.video))
        assertThat<Double>(properties?.get(0)?.voteAverage, Is.`is`<Double>(dummyMovieDto.voteAverage))
        assertThat<String>(properties?.get(0)?.title, Is.`is`<String>(dummyMovieDto.title))
        assertThat<Double>(properties?.get(0)?.popularity, Is.`is`<Double>(dummyMovieDto.popularity))
        assertThat<String>(properties?.get(0)?.posterPath, Is.`is`<String>(dummyMovieDto.posterPath))
        assertThat<String>(
            properties?.get(0)?.originalLanguage,
            Is.`is`<String>(dummyMovieDto.originalLanguage)
        )
        assertThat<String>(
            properties?.get(0)?.originalTitle,
            Is.`is`<String>(dummyMovieDto.originalTitle)
        )
        assertThat<Int>(properties?.get(0)?.genreIds?.size, Is.`is`<Int>(dummyMovieDto.genreIds.size))
        assertThat<String>(properties?.get(0)?.backdropPath, Is.`is`<String>(dummyMovieDto.backdropPath))
        assertThat<Boolean>(properties?.get(0)?.adult, Is.`is`<Boolean>(dummyMovieDto.adult))
        assertThat<String>(properties?.get(0)?.overview, Is.`is`<String>(dummyMovieDto.overview))
        assertThat<String>(properties?.get(0)?.releaseDate, Is.`is`<String>(dummyMovieDto.releaseDate))

    }

    @Test
    fun test_TransformNullPropertyDto() {
        try {
            //when
            movieMapper.apply(null)
        } catch (e: Exception) {
            //then
            Assert.assertTrue(e is NullPointerException)
        }

    }
}