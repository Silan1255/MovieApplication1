package com.example.movieapplication1.ui.movieDetails.view

import android.os.Bundle
import android.view.View
import com.example.movieapplication1.databinding.FragmentMovieDetailsBinding
import com.example.movieapplication1.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {

    //region lifecycle
    override fun getViewBinding(): FragmentMovieDetailsBinding =
        FragmentMovieDetailsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    //endregion
}