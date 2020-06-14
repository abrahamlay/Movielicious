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
class VideoMapperTest : BaseDataTestClass() {
    private lateinit var videoMapper: VideoMapper

    @Before
    fun setUp() {
        videoMapper = VideoMapper()
    }

    @Test
    @Throws(Exception::class)
    fun test_TransformPropertyDto() {
        val properties = videoMapper.apply(dummyVideoDto)

        assertThat<String>(properties?.get(0)?.id, Is.`is`<String>(dummyResultVideoDto.id))
        assertThat<String>(properties?.get(0)?.iso31661, Is.`is`<String>(dummyResultVideoDto.iso31661))
        assertThat<String>(properties?.get(0)?.iso6391, Is.`is`<String>(dummyResultVideoDto.iso6391))
        assertThat<String>(properties?.get(0)?.key, Is.`is`<String>(dummyResultVideoDto.key))
        assertThat<String>(properties?.get(0)?.name, Is.`is`<String>(dummyResultVideoDto.name))
        assertThat<String>(properties?.get(0)?.site, Is.`is`<String>(dummyResultVideoDto.site))
        assertThat<String>(properties?.get(0)?.type, Is.`is`<String>(dummyResultVideoDto.type))

    }

    @Test
    fun test_TransformNullPropertyDto() {
        try {
            //when
            videoMapper.apply(null)
        } catch (e: Exception) {
            //then
            Assert.assertTrue(e is NullPointerException)
        }

    }
}