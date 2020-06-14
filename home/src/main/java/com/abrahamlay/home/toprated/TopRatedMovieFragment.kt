package com.abrahamlay.home.toprated

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.detail.DetailFragment
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.home.MovieAdapter
import com.abrahamlay.home.MovieFragment
import com.abrahamlay.home.R
import com.abrahamlay.home.ViewContract
import kotlinx.android.synthetic.main.movie_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Abraham Lay on 2019-10-06.
 */

class TopRatedMovieFragment : MovieFragment<TopRatedMovieViewModel>(),
    ViewContract,
    MovieAdapter.OnClickListener {
    override fun onRefresh() {
        showLoading()
        viewModel.refreshMovie()
    }


    override val viewModel by viewModel<TopRatedMovieViewModel>()


    override fun onInitObservers() {
        super.onInitObservers()
        adapter = MovieAdapter()
        (adapter as? MovieAdapter)?.onClickListener = this
        viewModel.movieData.observe(this, Observer {
            onMovieLoaded(it)
        })
    }


    override fun onMovieLoaded(list: ResultState<List<MovieModel>?>) {
        hideLoading()
        when (list) {
            is ResultState.Success -> {
                initAdapter(list.data)
            }
            is ResultState.Error -> {
                showError(list.throwable)
            }
            is ResultState.Loading -> {
                initAdapter(list.data)
            }
        }
    }

    private fun initAdapter(list: List<MovieModel>?) {
        list?.let {
            (adapter as? MovieAdapter)?.data = list
            rvList.adapter = adapter
            rvList.layoutManager = getLayoutManager()
        }
    }

    override fun onItemClicked(data: Any) {
        Toast.makeText(context, (data as MovieModel).title, Toast.LENGTH_SHORT).show()
        val bundle = bundleOf(Pair(DetailFragment.PARAM_DETAIL_MOVIE, data))
        findNavController(this).navigate(R.id.action_mainFragment_to_detailFragment, bundle)
    }
}