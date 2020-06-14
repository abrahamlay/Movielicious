package com.abrahamlay.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.abrahamlay.base.presentation.BaseActivity
import com.abrahamlay.base.presentation.BaseFragment
import com.abrahamlay.base.subscriber.BaseViewModel
import com.abrahamlay.home.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_detail.toolbar
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Created by Abraham Lay on 2020-06-09.
 */
class MainFragment : BaseFragment<BaseViewModel>(), SwipeRefreshLayout.OnRefreshListener,
    SectionView.OnDoneLoading, HeaderView.OnDoneLoading {

    override val viewModel by viewModel<BaseViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onInitViews() {
        super.onInitViews()
        (activity as BaseActivity<*>).setSupportActionBar(toolbar)

        val supportActionBar = (activity as BaseActivity<*>).supportActionBar

        supportActionBar?.title = getString(R.string.app_name)
    }

    override fun onInitObservers() {
        super.onInitObservers()
        initListener()
        fetchData()
        Log.d("MainFragment", "onInitObserver")
    }

    private fun initListener() {
        refresh.setOnRefreshListener(this)
        headerView.doneLoadingListener = this
        favoriteView.doneLoadingListener = this
        topRatedView.doneLoadingListener = this
        popularView.doneLoadingListener = this
    }

    private fun fetchData() {
        headerView.fetchData()
        favoriteView.fetchData()
        topRatedView.fetchData()
        popularView.fetchData()
    }

    override fun onRefresh() {
        fetchData()
    }

    private fun hideLoading() {
        refresh.isRefreshing = false
    }

    override fun doneLoading() {
        if (isAdded) hideLoading()
    }
}