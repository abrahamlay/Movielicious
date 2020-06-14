package com.abrahamlay.movielicious.module

import androidx.room.Room
import com.abrahamlay.data.db.AppDatabase
import com.abrahamlay.data.db.MovieDao
import com.abrahamlay.data.mapper.DetailMovieMapper
import com.abrahamlay.data.mapper.MovieMapper
import com.abrahamlay.data.mapper.ReviewMapper
import com.abrahamlay.data.mapper.VideoMapper
import com.abrahamlay.data.repoimplementations.MovieRepositoryImpl
import com.abrahamlay.domain.repositories.MovieRepository
import com.abrahamlay.movielicious.BuildConfig
import org.koin.dsl.module

/**
 * Created by Abraham Lay on 2020-06-09.
 */

val dataModule = module {
    single<MovieRepository> {
        MovieRepositoryImpl(
            get(), get(), MovieMapper(), ReviewMapper(),
            VideoMapper(), DetailMovieMapper()
        )
    }
    single<AppDatabase> {
        Room.databaseBuilder(get(), AppDatabase::class.java, BuildConfig.APPLICATION_ID)
            .build()
    }
    single<MovieDao> {
        val database: AppDatabase = get()
        return@single database.movieDao()
    }
}