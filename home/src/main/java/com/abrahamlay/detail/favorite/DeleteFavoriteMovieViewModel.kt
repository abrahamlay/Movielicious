package com.abrahamlay.detail.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.abrahamlay.base.subscriber.BaseViewModel
import com.abrahamlay.base.subscriber.DefaultSubscriber
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.domain.interactors.DeleteFavoriteMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * Created by Abraham Lay on 09/06/20.
 */
class DeleteFavoriteMovieViewModel(repositoryImpl: DeleteFavoriteMovie) : BaseViewModel() {
    private var movieModel: MovieModel? = null
    private val mutableRepo = MutableLiveData<ResultState<Int>>()
    private val triggerFetch = MutableLiveData<Boolean>()
    val favoriteMovieData: LiveData<ResultState<Int>> =
        Transformations.switchMap(triggerFetch) {
            runBlocking {
                deleteMovie(repositoryImpl)
            }
            return@switchMap mutableRepo
        }

    private suspend fun deleteMovie(repositoryImpl: DeleteFavoriteMovie) {
        withContext(Dispatchers.IO) {
            movieModel?.let {
                repositoryImpl.execute(object : DefaultSubscriber<Int>() {

                    override fun onError(error: ResultState<Int>) {
                        mutableRepo.postValue(error)
                    }

                    override fun onSuccess(data: ResultState<Int>) {
                        mutableRepo.postValue(data)
                    }
                }, DeleteFavoriteMovie.Params(movieModel!!))
            }
        }
    }

    fun deleteMovie(movieModel: MovieModel) {
        this@DeleteFavoriteMovieViewModel.movieModel = movieModel
        triggerFetch.value = true
    }
}