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
    factory { GetReviews(get(), get()) }
    factory { GetDetailMovie(get(), get()) }
    factory { GetVideos(get(), get()) }
    factory { GetNowPlayingMovies(get(), get()) }
    factory { GetTopRatedMovies(get(), get()) }
    factory { GetPopularMovies(get(), get()) }
    factory { GetFavoriteMovie(get(), get()) }
    factory { GetFavoriteMovies(get(), get()) }
    factory { InsertFavoriteMovie(get(), get()) }
    factory { DeleteFavoriteMovie(get(), get()) }
}