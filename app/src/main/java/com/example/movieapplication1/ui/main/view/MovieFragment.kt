package com.example.movieapplication1.ui.main.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapplication1.data.model.Movie
import com.example.movieapplication1.data.model.Resource
import com.example.movieapplication1.data.model.Result
import com.example.movieapplication1.ui.BaseFragment
import com.example.movieapplication1.ui.main.adapter.MovieListAdapter
import com.example.movieapplication1.ui.main.adapter.MovieListener
import com.example.movieapplication.ui.main.adapter.TabsAdapter
import com.example.movieapplication1.ui.main.viewModel.MovieVM
import com.example.movieapplication1.utils.handleApiError
import com.example.movieapplication1.R
import com.example.movieapplication1.databinding.FragmentMovieBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : BaseFragment<FragmentMovieBinding>(), MovieListener {

    //region variables
    private lateinit var movieVM: MovieVM
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var movieListAdapter: MovieListAdapter
    private var currentPage = 1
    private var result: ArrayList<Result> = arrayListOf()
    //endregion

    //region lifecycle
    override fun getViewBinding(): FragmentMovieBinding =
        FragmentMovieBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getMovieForSlider()
        getMovieList()
    }

    override fun onStart() {
        super.onStart()
        setObservers()
    }

    override fun onStop() {
        super.onStop()
        removeObservers()
    }
    //endregion

    //region init
    private fun init() {
        movieVM = ViewModelProvider(this)[MovieVM::class.java]
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rcyMovieList.layoutManager = layoutManager
        binding.rcyMovieList.addOnScrollListener(recyclerViewScrollListener)
    }

    private fun initViews(result: ArrayList<Result>) {
        binding.apply {
            val tabsAdapter = TabsAdapter(requireContext(), result)
            pager.adapter = tabsAdapter
            TabLayoutMediator(tabs, pager) { _, _ -> }.attach()
        }
    }
    //endregion

    //region tool
    private fun getMovieForSlider() {
        movieVM.getMovieForSlider()
    }

    private fun getMovieList() {
        movieVM.getMovieList(currentPage)
    }

    private fun fetchData() {
        currentPage++
        getMovieList()
    }

    override fun onMovieClicked(movieId: Int) {
        val bundle = Bundle()
        bundle.putInt("movieId", movieId)
        findNavController().navigate(R.id.action_nav_movie_to_nav_movie_details, bundle)
    }

    private fun setMovieList(data: ArrayList<Result>) {
        when (currentPage) {
            1 -> {
                movieListAdapter = MovieListAdapter(requireContext(), data, this)
                binding.rcyMovieList.adapter = movieListAdapter
            }
            else -> {
                result.addAll(data)
                movieListAdapter.notifyDataSetChanged()
            }
        }
    }
    //endregion

    //region listeners
    private val recyclerViewScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val currentItems = layoutManager.childCount
            val totalItems = layoutManager.itemCount
            val scrollOutItems = layoutManager.findFirstVisibleItemPosition()
            if (currentItems + scrollOutItems == totalItems && dy > 0) {
                fetchData()
            }
        }
    }

    private fun setObservers() {
        movieVM.movieForSliderData.observe(viewLifecycleOwner, movieForSliderDataObserver)
        movieVM.movieListData.observe(viewLifecycleOwner, movieListDataObserver)
    }

    private fun removeObservers() {
        movieVM.movieForSliderData.removeObservers(viewLifecycleOwner)
        movieVM.movieListData.removeObservers(viewLifecycleOwner)
    }
    //endregion

    //region data
    private val movieForSliderDataObserver =
        Observer<Resource<Movie>> { response ->
            response?.let { responseBody ->
                when (responseBody) {
                    is Resource.Success -> {
                        responseBody.value.results?.let { initViews(it) }
                    }
                    is Resource.Failure -> handleApiError(responseBody, customToast)
                }
                movieVM.movieForSliderData.value = null
            }
        }

    private val movieListDataObserver =
        Observer<Resource<Movie>> { response ->
            response?.let { responseBody ->
                when (responseBody) {
                    is Resource.Success -> {
                        if (currentPage == 1) result.clear()
                        responseBody.value.results?.let { setMovieList(it) }
                    }
                    is Resource.Failure -> handleApiError(responseBody, customToast)
                }
                movieVM.movieListData.value = null
            }
        }
    //endregion
}
