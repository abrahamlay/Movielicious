package com.abrahamlay.movielicious.module

import com.abrahamlay.base.subscriber.BaseViewModel
import com.abrahamlay.detail.DetailViewModel
import com.abrahamlay.detail.favorite.DeleteFavoriteMovieViewModel
import com.abrahamlay.detail.favorite.GetFavoriteMovieViewModel
import com.abrahamlay.detail.favorite.InsertFavoriteMovieViewModel
import com.abrahamlay.detail.reviews.ReviewViewModel
import com.abrahamlay.detail.video.VideoViewModel
import com.abrahamlay.home.favorite.GetFavoriteMoviesViewModel
import com.abrahamlay.home.nowplaying.NowPlayingMovieViewModel
import com.abrahamlay.home.popular.PopularMovieViewModel
import com.abrahamlay.home.toprated.TopRatedMovieViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Abraham Lay on 2020-06-09.
 */

val viewModelModule = module {
    viewModel<BaseViewModel>()
    viewModel<ReviewViewModel>()
    viewModel<VideoViewModel>()
    viewModel<DetailViewModel>()
    viewModel<NowPlayingMovieViewModel>()
    viewModel<TopRatedMovieViewModel>()
    viewModel<PopularMovieViewModel>()
    viewModel<GetFavoriteMovieViewModel>()
    viewModel<GetFavoriteMoviesViewModel>()
    viewModel<InsertFavoriteMovieViewModel>()
    viewModel<DeleteFavoriteMovieViewModel>()
}