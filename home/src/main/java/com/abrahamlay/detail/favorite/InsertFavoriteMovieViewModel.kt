package com.abrahamlay.detail.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.abrahamlay.base.subscriber.BaseViewModel
import com.abrahamlay.base.subscriber.DefaultSubscriber
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.domain.interactors.InsertFavoriteMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * Created by Abraham Lay on 09/06/20.
 */
class InsertFavoriteMovieViewModel(repositoryImpl: InsertFavoriteMovie) : BaseViewModel() {
    private var movieModel: MovieModel? = null
    private val mutableRepo = MutableLiveData<ResultState<Long>>()
    private val triggerFetch = MutableLiveData<Boolean>()
    val favoriteMovieData: LiveData<ResultState<Long>> =
        Transformations.switchMap(triggerFetch) {
            runBlocking {
                fetchVideo(repositoryImpl)
            }
            return@switchMap mutableRepo
        }

    private suspend fun fetchVideo(repositoryImpl: InsertFavoriteMovie) {
        withContext(Dispatchers.IO) {
            movieModel?.let {
                repositoryImpl.execute(object : DefaultSubscriber<Long>() {

                    override fun onError(error: ResultState<Long>) {
                        mutableRepo.postValue(error)
                    }

                    override fun onSuccess(data: ResultState<Long>) {
                        mutableRepo.postValue(data)
                    }
                }, InsertFavoriteMovie.Params(movieModel!!))
            }
        }
    }

    fun insertMovie(movieId: MovieModel) {
        this@InsertFavoriteMovieViewModel.movieModel = movieId
        triggerFetch.value = true
    }
}