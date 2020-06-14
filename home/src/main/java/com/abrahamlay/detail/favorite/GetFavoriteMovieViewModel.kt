package com.abrahamlay.detail.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.abrahamlay.base.subscriber.BaseViewModel
import com.abrahamlay.base.subscriber.DefaultSubscriber
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.domain.interactors.GetFavoriteMovie

/**
 * Created by Abraham Lay on 09/06/20.
 */
class GetFavoriteMovieViewModel(repositoryImpl: GetFavoriteMovie) : BaseViewModel() {
    private var movieId: Int = 28
    private val mutableRepo = MutableLiveData<ResultState<MovieModel?>>()
    private val triggerFetch = MutableLiveData<Boolean>()
    val favoriteMovieData: LiveData<ResultState<MovieModel?>> =
        Transformations.switchMap(triggerFetch) {
            fetchVideo(repositoryImpl)
            return@switchMap mutableRepo
        }

    private fun fetchVideo(repositoryImpl: GetFavoriteMovie) {
        repositoryImpl.execute(object : DefaultSubscriber<MovieModel?>() {

            override fun onError(error: ResultState<MovieModel?>) {
                mutableRepo.postValue(error)
            }

            override fun onSuccess(data: ResultState<MovieModel?>) {
                mutableRepo.postValue(data)
            }
        }, GetFavoriteMovie.Params(movieId))
    }

    fun getFavoriteMovie(movieId: Int) {
        this.movieId = movieId
        triggerFetch.value = true
    }
}