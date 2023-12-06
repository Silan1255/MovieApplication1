package com.example.movieapplication1.ui.movieDetails.viewModel

import androidx.lifecycle.ViewModel
import com.example.movieapplication1.data.repository.ApplicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsVM @Inject constructor(private val repository: ApplicationRepository) :
    ViewModel() {

}