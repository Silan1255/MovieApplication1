package com.example.movieapplication1.ui.main.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.movieapplication.ui.main.adapter.TabsAdapter
import com.example.movieapplication1.data.model.Movie
import com.example.movieapplication1.data.model.Resource
import com.example.movieapplication1.data.model.Result
import com.example.movieapplication1.databinding.FragmentMovieBinding
import com.example.movieapplication1.ui.BaseFragment
import com.example.movieapplication1.ui.main.viewModel.MovieVM
import com.example.movieapplication1.utils.handleApiError
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : BaseFragment<FragmentMovieBinding>() {

    //region variables
    private lateinit var movieVM: MovieVM
    private var currentPage = 1
    private var result: ArrayList<Result> = arrayListOf()
    //endregion

    //region lifecycle
    override fun getViewBinding(): FragmentMovieBinding =
        FragmentMovieBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    //endregion

    private fun setObservers() {
        movieVM.movieForSliderData.observe(viewLifecycleOwner, movieForSliderDataObserver)
    }

    private fun removeObservers() {
        movieVM.movieForSliderData.removeObservers(viewLifecycleOwner)
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
    //endregion
}
