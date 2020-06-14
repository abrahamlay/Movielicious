package com.abrahamlay.domain.interactors

import com.abrahamlay.domain.FlowableUseCase
import com.abrahamlay.domain.PostExecutionThread
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.domain.repositories.MovieRepository
import io.reactivex.Flowable

/**
 * Created by Abraham Lay on 13/06/20.
 */
class GetFavoriteMovies constructor(
    private val repository: MovieRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<List<MovieModel>?, Void?>(postExecutionThread) {
    override fun build(params: Void?): Flowable<List<MovieModel>?> {
        return repository.getFavoriteMovies()
    }
}