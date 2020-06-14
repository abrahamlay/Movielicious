package com.abrahamlay.home.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.abrahamlay.base.subscriber.BaseViewModel
import com.abrahamlay.base.subscriber.DefaultSubscriber
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.domain.interactors.GetFavoriteMovies

/**
 * Created by Abraham Lay on 09/06/20.
 */
class GetFavoriteMoviesViewModel(repositoryImpl: GetFavoriteMovies) : BaseViewModel() {
    private val mutableRepo = MutableLiveData<ResultState<List<MovieModel>?>>()
    private val triggerFetch = MutableLiveData<Boolean>()
    val favoriteMovieData: LiveData<ResultState<List<MovieModel>?>> =
        Transformations.switchMap(triggerFetch) {
            fetchVideo(repositoryImpl)
            return@switchMap mutableRepo
        }

    private fun fetchVideo(repositoryImpl: GetFavoriteMovies) {
        repositoryImpl.execute(object : DefaultSubscriber<List<MovieModel>?>() {

            override fun onError(error: ResultState<List<MovieModel>?>) {
                mutableRepo.postValue(error)
            }

            override fun onSuccess(data: ResultState<List<MovieModel>?>) {
                mutableRepo.postValue(data)
            }
        }, params = null)
    }

    fun refreshMovie() {
        triggerFetch.value = true
    }
}