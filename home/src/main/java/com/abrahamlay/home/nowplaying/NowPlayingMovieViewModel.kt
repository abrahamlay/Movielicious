package com.abrahamlay.home.nowplaying

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.abrahamlay.base.constant.Constants
import com.abrahamlay.base.subscriber.BaseViewModel
import com.abrahamlay.base.subscriber.DefaultSubscriber
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.domain.interactors.GetNowPlayingMovies

/**
 * Created by Abraham Lay on 2019-12-28.
 */
class NowPlayingMovieViewModel(repositoryImpl: GetNowPlayingMovies) : BaseViewModel() {
    private val mutableRepo = MutableLiveData<ResultState<List<MovieModel>?>>()
    private val triggerFetch = MutableLiveData<Boolean>()
    val movieData: LiveData<ResultState<List<MovieModel>?>> =
        Transformations.switchMap(triggerFetch) {
            fetchMovie(repositoryImpl)
            return@switchMap mutableRepo
        }


    private fun fetchMovie(repositoryImpl: GetNowPlayingMovies) {
        repositoryImpl.execute(object : DefaultSubscriber<List<MovieModel>?>() {
            override fun onError(error: ResultState<List<MovieModel>?>) {
                mutableRepo.postValue(error)
            }

            override fun onSuccess(data: ResultState<List<MovieModel>?>) {
                mutableRepo.postValue(data)
            }
        }, GetNowPlayingMovies.Params(Constants.API_KEY))
    }

    fun refreshMovie() {
        triggerFetch.value = true
    }
}