package com.abrahamlay.home

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.abrahamlay.base.constant.Constants
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.detail.DetailFragment
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.home.favorite.GetFavoriteMoviesViewModel
import com.abrahamlay.home.nowplaying.NowPlayingMovieViewModel
import com.abrahamlay.home.popular.PopularMovieViewModel
import com.abrahamlay.home.toprated.TopRatedMovieViewModel
import kotlinx.android.synthetic.main.view_error.view.*
import kotlinx.android.synthetic.main.view_header.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject


/**
 * Created by Abraham Lay on 12/06/20.
 */
class HeaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), KoinComponent, LifecycleOwner,
    MovieAdapter.OnClickListener {

    private var type: Int = 0
    private val popularViewModel by inject<PopularMovieViewModel>()
    private val topRatedMovieViewModel by inject<TopRatedMovieViewModel>()
    private val nowPlayingMovieViewModel by inject<NowPlayingMovieViewModel>()
    private val favoriteMoviesViewModel by inject<GetFavoriteMoviesViewModel>()
    private val lifecycle: LifecycleRegistry = LifecycleRegistry(this)
    private var adapter: MovieAdapter? = null

    var doneLoadingListener: OnDoneLoading? = null

    init {
        lifecycle.markState(Lifecycle.State.RESUMED)
        adapter = MovieAdapter()
        adapter?.onClickListener = this
        adapter?.isHeader = true
        val array: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.HeaderView
        )

        val type = array.getInt(R.styleable.HeaderView_hvModule, Constants.POPULAR)
        val label = array.getString(R.styleable.HeaderView_hvLabel)
        array.recycle()
        LayoutInflater.from(context).inflate(R.layout.view_header, this)

        tvLabelSection.text = label
        this.type = type
        btnRetry.setOnClickListener {
            fetchData()
        }

        setAdapter()
        popularViewModel.movieData.observe(this, Observer {
            setView(it)
        })
        topRatedMovieViewModel.movieData.observe(this, Observer {
            setView(it)
        })
        nowPlayingMovieViewModel.movieData.observe(this, Observer {
            setView(it)
        })
        favoriteMoviesViewModel.favoriteMovieData.observe(this, Observer {
            setView(it)
        })
    }

    fun fetchData() {
        showLoading()
        hideError()
        when (type) {
            Constants.POPULAR -> {
                popularViewModel.refreshMovie()
            }
            Constants.TOP_RATED -> {
                topRatedMovieViewModel.refreshMovie()
            }
            Constants.NOW_PLAYING -> {
                nowPlayingMovieViewModel.refreshMovie()
            }
            Constants.FAVORITE -> {
                favoriteMoviesViewModel.refreshMovie()
            }
        }
    }

    private fun setView(resultState: ResultState<List<MovieModel>?>?) {
        hideLoading()
        when (resultState) {
            is ResultState.Success -> {
                setData(resultState.data)
            }
            is ResultState.Error -> {
                showError(resultState.throwable)
            }
            is ResultState.Loading -> {
                setData(resultState.data)
            }
        }
    }

    private fun setData(data: List<MovieModel>?) {
        if (data.isNullOrEmpty()) {
            visibility = GONE
            return
        }
        adapter?.data = data
    }

    private fun setAdapter() {
        rvMovieList.adapter = adapter
        val helper: SnapHelper = PagerSnapHelper()
        val linearLayoutManager = LinearLayoutManager(context)
        helper.attachToRecyclerView(rvMovieList)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvMovieList.layoutManager = linearLayoutManager
    }

    override fun getLifecycle(): Lifecycle = lifecycle


    private fun showError(throwable: Throwable) {
        errorView.visibility = View.VISIBLE
        tvErrorMessage.text = throwable.localizedMessage
        Log.d("MainFragmentSV", "showError + ${throwable.localizedMessage}")
    }

    private fun hideError() {
        errorView.visibility = View.GONE
    }

    private fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        doneLoadingListener?.doneLoading()
        progress_bar.visibility = View.GONE
    }

    override fun onItemClicked(data: Any) {
        Toast.makeText(context, (data as MovieModel).title, Toast.LENGTH_SHORT).show()
        val bundle = bundleOf(Pair(DetailFragment.PARAM_DETAIL_MOVIE, data))
        findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
    }

    interface OnDoneLoading {
        fun doneLoading()
    }
}