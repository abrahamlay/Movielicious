package com.abrahamlay.movielicious.module

import com.abrahamlay.domain.AndroidUIThread
import com.abrahamlay.domain.PostExecutionThread
import com.abrahamlay.domain.interactors.*
import org.koin.dsl.module

/**
 * Created by Abraham Lay on 2020-06-09.
 */

val useCaseModule = module {
    single<PostExecutionThread> { return@single AndroidUIThread() }
    factory { GetGenresInteractor(get(), get()) }
    factory { GetDiscoverMoviesByGenre(get(), get()) }
    factory { GetReviews(get(), get()) }
    factory { GetDetailMovie(get(), get()) }
    factory { GetVideos(get(), get()) }
}