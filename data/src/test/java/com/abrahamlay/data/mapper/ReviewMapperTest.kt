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
class ReviewMapperTest : BaseDataTestClass() {
    private lateinit var reviewMapper: ReviewMapper

    @Before
    fun setUp() {
        reviewMapper = ReviewMapper()
    }

    @Test
    @Throws(Exception::class)
    fun test_TransformPropertyDto() {
        val properties = reviewMapper.apply(dummyReviewDto)

        assertThat<String>(properties?.get(0)?.author, Is.`is`<String>(dummyResultReviewDto.author))
        assertThat<String>(
            properties?.get(0)?.content,
            Is.`is`<String>(dummyResultReviewDto.content)
        )
        assertThat<String>(properties?.get(0)?.id, Is.`is`<String>(dummyResultReviewDto.id))
        assertThat<String>(properties?.get(0)?.url, Is.`is`<String>(dummyResultReviewDto.url))

    }

    @Test
    fun test_TransformNullPropertyDto() {
        try {
            //when
            reviewMapper.apply(null)
        } catch (e: Exception) {
            //then
            Assert.assertTrue(e is NullPointerException)
        }

    }
}