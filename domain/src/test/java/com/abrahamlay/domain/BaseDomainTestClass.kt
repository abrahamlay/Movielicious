package com.abrahamlay.domain

import com.abrahamlay.domain.entities.MovieModel

open class BaseDomainTestClass {
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
}
