package com.abrahamlay.domain.interactors

import com.abrahamlay.domain.FlowableUseCase
import com.abrahamlay.domain.PostExecutionThread
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.domain.repositories.MovieRepository
import io.reactivex.Flowable

/**
 * Created by Abraham Lay on 13/06/20.
 */
class DeleteFavoriteMovie constructor(
    private val repository: MovieRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<Int, DeleteFavoriteMovie.Params>(postExecutionThread) {
    override fun build(params: Params): Flowable<Int> {
        return Flowable.just(repository.deleteFavoriteMovie(params.movieModel))
    }

    data class Params(val movieModel: MovieModel)
}

