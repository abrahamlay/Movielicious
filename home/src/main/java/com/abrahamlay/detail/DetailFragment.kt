package com.abrahamlay.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abrahamlay.base.constant.Constants
import com.abrahamlay.base.presentation.BaseActivity
import com.abrahamlay.base.presentation.BaseFragment
import com.abrahamlay.base.subscriber.BaseViewModel
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.base.util.DateFormater
import com.abrahamlay.base.util.GlideHelper
import com.abrahamlay.detail.favorite.DeleteFavoriteMovieViewModel
import com.abrahamlay.detail.favorite.GetFavoriteMovieViewModel
import com.abrahamlay.detail.favorite.InsertFavoriteMovieViewModel
import com.abrahamlay.detail.reviews.ReviewAdapter
import com.abrahamlay.detail.reviews.ReviewViewModel
import com.abrahamlay.detail.video.VideoViewModel
import com.abrahamlay.domain.entities.DetailMovieModel
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.domain.entities.ReviewModel
import com.abrahamlay.domain.entities.VideoModel
import com.abrahamlay.home.R
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.movie_fragment.errorView
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_review.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Created by Abraham Lay on 2020-06-09.
 */
class DetailFragment : BaseFragment<BaseViewModel>() {

    private var menu: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var adapter: ReviewAdapter
    private var detailMovie: MovieModel? = null

    override val viewModel by viewModel<ReviewViewModel>()

    private val viewModelVideo by viewModel<VideoViewModel>()
    private val favoriteMovieViewModel by viewModel<GetFavoriteMovieViewModel>()
    private val insertFavoriteMovieViewModel by viewModel<InsertFavoriteMovieViewModel>()
    private val deleteFavoriteMovieViewModel by viewModel<DeleteFavoriteMovieViewModel>()
    private val detailViewModel by viewModel<DetailViewModel>()

    companion object {
        const val PARAM_DETAIL_MOVIE = "detailMovie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            detailMovie = arguments?.getParcelable(PARAM_DETAIL_MOVIE)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // create ContextThemeWrapper from the original Activity Context with the custom theme
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.AppTheme_NoActionBar)

        // clone the inflater using the ContextThemeWrapper
        val localInflater = inflater.cloneInContext(contextThemeWrapper)

        // Inflate the layout for this fragment
        return localInflater.inflate(R.layout.fragment_detail, container, false)
    }


    override fun onInitViews() {
        super.onInitViews()
        initToolbar()
        if (detailMovie != null) {
            initDetail()
        }
    }

    private fun initDetail() {
        detailOverview.text = detailMovie?.overview
        detailRating.text = detailMovie?.popularity.toString()
        val releaseDate = DateFormater.changeDateFormat(
            detailMovie?.releaseDate
        )
        detailDateRelease.text = releaseDate
        val url =
            String.format(
                Constants.MOVIE_THUMBNAIL_BASE_URL_EXTRA_LARGE,
                detailMovie?.backdropPath
            )
        GlideHelper.showBackDrop(url, ivMovie, context!!)
        getDetailData()
    }

    override fun onInitObservers() {
        super.onInitObservers()
        btnRetry.setOnClickListener {
            getDetailData()
        }
        viewModelVideo.videoData.observe(this, Observer {
            initVideo(it)
        })
        viewModel.reviewData.observe(this, Observer {
            onResultLoaded(it)
        })
        favoriteMovieViewModel.favoriteMovieData.observe(this, Observer {
            checkingMovieFavoriteStatus(it)
        })
        insertFavoriteMovieViewModel.favoriteMovieData.observe(this, Observer {
            afterInsertFavoriteMovie(it)
        })
        deleteFavoriteMovieViewModel.favoriteMovieData.observe(this, Observer {
            afterDeleteFavoriteMovie(it)
        })
        detailViewModel.detailData.observe(this, Observer {
            setDetail(it)
        })
    }

    private fun setDetail(it: ResultState<DetailMovieModel>?) {
        when (it) {
            is ResultState.Success -> {
               setDetailData(it.data)
            }
            is ResultState.Error -> {
                Toast.makeText(context, it.throwable.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setDetailData(data: DetailMovieModel?) {
        detailOverview.text = data?.overview
        detailRating.text = data?.popularity.toString()
        val releaseDate = DateFormater.changeDateFormat(
            data?.releaseDate
        )
        detailDateRelease.text = releaseDate
        val url =
            String.format(
                Constants.MOVIE_THUMBNAIL_BASE_URL_EXTRA_LARGE,
                data?.backdropPath
            )
        GlideHelper.showBackDrop(url, ivMovie, context!!)
    }

    private fun afterDeleteFavoriteMovie(it: ResultState<Int>?) {
        when (it) {
            is ResultState.Success -> {
                isFavorite = !isFavorite
                Toast.makeText(context, getString(R.string.favorite_deleted), Toast.LENGTH_SHORT)
                    .show()
            }
            is ResultState.Error -> {
                Toast.makeText(context, it.throwable.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
        setFavoriteIcon()
    }

    private fun afterInsertFavoriteMovie(it: ResultState<Long>?) {
        when (it) {
            is ResultState.Success -> {
                isFavorite = !isFavorite
                Toast.makeText(context, getString(R.string.favorite_added), Toast.LENGTH_SHORT)
                    .show()
            }
            is ResultState.Error -> {
                Toast.makeText(context, it.throwable.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
        setFavoriteIcon()
    }

    private fun checkingMovieFavoriteStatus(it: ResultState<MovieModel?>?) {
        it?.let {
            when (it) {
                is ResultState.Success -> {
                    isFavorite = true
                }
            }

            setFavoriteIcon()
        }
    }

    private fun setFavoriteIcon() {
        menu?.getItem(0)?.icon = getFavoriteIcon()
    }


    private fun onResultLoaded(resultState: ResultState<List<ReviewModel>>?) {
        hideLoading()
        when (resultState) {
            is ResultState.Success -> {
                initAdapter(resultState)
            }
            is ResultState.Error -> {
                showError(resultState.throwable)
            }
        }

    }

    private fun initAdapter(resultState: ResultState.Success<List<ReviewModel>>) {
        adapter = ReviewAdapter()
        adapter.data = resultState.data
        rvReviewsList.adapter = adapter
        rvReviewsList.layoutManager = getLayoutManager()
    }

    private fun initToolbar() {

        (activity as BaseActivity<*>).setSupportActionBar(toolbar)

        val supportActionBar = (activity as BaseActivity<*>).supportActionBar

        if (supportActionBar != null) {
            supportActionBar.title = detailMovie?.title
            supportActionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initVideo(state: ResultState<List<VideoModel>>) {
        if (state is ResultState.Success) {
            initWebView(state.data[0].key)
        }
    }

    private fun initWebView(key: String?) {
        //build your own src link with your video ID
        val videoStr =
            "<html><head><style>body{margin:0} iframe{margin:0;width:100%}</style></head><body><iframe style=\"margin: 0;\" width=\"100%\" height=\"240\" src=\"https://www.youtube.com/embed/$key\" autoplay=\"1\" allowfullscreen/></body></html>"

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }
        }
        val ws: WebSettings = webView.settings
        ws.javaScriptEnabled = true
        webView.loadData(videoStr, "text/html", "utf-8")
    }

    private fun getLayoutManager(): RecyclerView.LayoutManager? {
        return LinearLayoutManager(context)
    }

    private fun showError(throwable: Throwable) {
        errorView.visibility = View.VISIBLE
        tvErrorMessage.text = throwable.localizedMessage
    }

    private fun hideError() {
        errorView.visibility = View.GONE
    }

    private fun getDetailData() {
        detailMovie?.id?.let {
            hideError()
            showLoading()
            viewModel.refreshReviews(it)
            viewModelVideo.refreshVideo(it)
            favoriteMovieViewModel.getFavoriteMovie(it)
            detailViewModel.refreshDetail(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu
        activity?.menuInflater?.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        setFavoriteMovie()
        return super.onOptionsItemSelected(item)
    }

    private fun setFavoriteMovie() {
        detailMovie?.let {
            if (!isFavorite) {
                insertFavoriteMovieViewModel.insertMovie(it)
            } else {
                deleteFavoriteMovieViewModel.deleteMovie(it)
            }
        }
    }

    private fun getFavoriteIcon(): Drawable? {
        return if (isFavorite) ContextCompat.getDrawable(
            context!!,
            R.drawable.ic_favorite_filled
        ) else ContextCompat.getDrawable(context!!, R.drawable.ic_favorite_unfilled)
    }

    private fun showLoading() {
        progressBarView.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBarView.visibility = View.GONE
    }
}