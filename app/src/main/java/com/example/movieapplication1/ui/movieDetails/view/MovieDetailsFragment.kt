package com.example.movieapplication1.ui.movieDetails.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movieapplication1.data.model.MovieDetail
import com.example.movieapplication1.data.model.Resource
import com.example.movieapplication1.ui.BaseFragment
import com.example.movieapplication1.ui.movieDetails.viewModel.MovieDetailsVM
import com.example.movieapplication.utils.Utils
import com.example.movieapplication.utils.Utils.capitalizeWords
import com.example.movieapplication1.utils.handleApiError
import com.example.movieapplication1.R
import com.example.movieapplication1.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {

    //region variables
    private lateinit var movieDetailsVM: MovieDetailsVM
    private var movieDetail: MovieDetail? = null
    private var movieId: Int? = null
    //endregion

    //region lifecycle
    override fun getViewBinding(): FragmentMovieDetailsBinding =
        FragmentMovieDetailsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        movieId?.let { getMovieDetails(it) }
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
        movieDetailsVM = ViewModelProvider(this)[MovieDetailsVM::class.java]
        movieId = arguments?.getInt("movieId", -1)
    }

    private fun initViews() {
        binding.apply {
            Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + movieDetail?.poster_path)
                .placeholder(R.drawable.ic_default_icon)
                .into(imgMovie)
            movieDetail?.vote_average?.let {
                val formattedVoteAverage = DecimalFormat("0.#").format(it)
                txtVoteAverage.text = "$formattedVoteAverage/10"
            }
            movieDetail?.release_date.let {
                txtDate.text =
                    it?.let { it1 -> Utils.getFormattedDate(it1) }
            }
            movieDetail?.overview.let { txtMovieDescription.text = it }

            movieDetail?.title.let { title ->
                var movieTitle = title?.capitalizeWords()
                movieDetail?.release_date.let { releaseDate ->
                    movieTitle += " ${"  (" + releaseDate?.let { Utils.getFormattedYear(it) } + ")".capitalizeWords()}"
                }
                txtMovieTitle.text = movieTitle
            }

            binding.imgLogoImdb.setOnClickListener {
                movieDetail?.imdb_id?.let { imdbId ->
                    val imdbUrl = "https://www.imdb.com/title/$imdbId"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(imdbUrl))
                    startActivity(intent)
                }
            }
        }
    }
    //endregion

    //region tool
    private fun getMovieDetails(id: Int) {
        movieDetailsVM.getMovieDetails(id)
    }
    //endregion

    //region listeners
    private fun setObservers() {
        movieDetailsVM.movieDetailsData.observe(viewLifecycleOwner, movieListDataObserver)
    }

    private fun removeObservers() {
        movieDetailsVM.movieDetailsData.removeObservers(viewLifecycleOwner)
    }
    //endregion


    //region data
    private val movieListDataObserver =
        Observer<Resource<MovieDetail>> { response ->
            response?.let { responseBody ->
                when (responseBody) {
                    is Resource.Success -> {
                        movieDetail = responseBody.value
                        initViews()
                    }
                    is Resource.Failure -> handleApiError(responseBody, customToast)
                }
                movieDetailsVM.movieDetailsData.value = null
            }
        }
    //endregion
}