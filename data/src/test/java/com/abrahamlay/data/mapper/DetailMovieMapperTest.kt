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
class DetailMovieMapperTest : BaseDataTestClass() {
    private lateinit var detailMovieMapper: DetailMovieMapper

    @Before
    fun setUp() {
        detailMovieMapper = DetailMovieMapper()
    }

    @Test
    @Throws(Exception::class)
    fun test_TransformPropertyDto() {
        val properties = detailMovieMapper.apply(dummyDetailMovieDto)

        assertThat<Int>(properties?.voteCount, Is.`is`<Int>(dummyDetailMovieDto.voteCount))
        assertThat<Int>(properties?.id, Is.`is`<Int>(dummyDetailMovieDto.id))
        assertThat<Boolean>(properties?.video, Is.`is`<Boolean>(dummyDetailMovieDto.video))
        assertThat<Double>(properties?.voteAverage, Is.`is`<Double>(dummyDetailMovieDto.voteAverage))
        assertThat<String>(properties?.title, Is.`is`<String>(dummyDetailMovieDto.title))
        assertThat<Double>(properties?.popularity, Is.`is`<Double>(dummyDetailMovieDto.popularity))
        assertThat<String>(properties?.posterPath, Is.`is`<String>(dummyDetailMovieDto.posterPath))
        assertThat<String>(
            properties?.originalLanguage,
            Is.`is`<String>(dummyDetailMovieDto.originalLanguage)
        )
        assertThat<String>(
            properties?.originalTitle,
            Is.`is`<String>(dummyDetailMovieDto.originalTitle)
        )
        assertThat<Int>(properties?.genres?.size, Is.`is`<Int>(dummyDetailMovieDto.genres?.size))
        assertThat<String>(properties?.backdropPath, Is.`is`<String>(dummyDetailMovieDto.backdropPath))
        assertThat<Boolean>(properties?.adult, Is.`is`<Boolean>(dummyDetailMovieDto.adult))
        assertThat<String>(properties?.overview, Is.`is`<String>(dummyDetailMovieDto.overview))
        assertThat<String>(properties?.releaseDate, Is.`is`<String>(dummyDetailMovieDto.releaseDate))

    }

    @Test
    fun test_TransformNullPropertyDto() {
        try {
            //when
            detailMovieMapper.apply(null)
        } catch (e: Exception) {
            //then
            Assert.assertTrue(e is NullPointerException)
        }

    }
}