package com.abrahamlay.detail.reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.abrahamlay.base.constant.Constants
import com.abrahamlay.base.subscriber.BaseViewModel
import com.abrahamlay.base.subscriber.DefaultSubscriber
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.domain.entities.ReviewModel
import com.abrahamlay.domain.interactors.GetReviews

/**
 * Created by Abraham Lay on 2019-12-28.
 */
class ReviewViewModel(repositoryImpl: GetReviews) : BaseViewModel() {
    private var movieId: Int = 28
    private val mutableRepo = MutableLiveData<ResultState<List<ReviewModel>>>()
    private val triggerFetch = MutableLiveData<Boolean>()
    val reviewData: LiveData<ResultState<List<ReviewModel>>> =
        Transformations.switchMap(triggerFetch) {
            fetchMovie(repositoryImpl)
            return@switchMap mutableRepo
        }

    init {

    }

    private fun fetchMovie(repositoryImpl: GetReviews) {
        repositoryImpl.execute(object : DefaultSubscriber<List<ReviewModel>>() {
            override fun onError(error: ResultState<List<ReviewModel>>) {
                mutableRepo.postValue(error)
            }

            override fun onSuccess(data: ResultState<List<ReviewModel>>) {
                mutableRepo.postValue(data)
            }
        }, GetReviews.Params(Constants.API_KEY, movieId))
    }

    fun refreshReviews(movieId: Int) {
        this.movieId = movieId
        triggerFetch.value = true
    }
}