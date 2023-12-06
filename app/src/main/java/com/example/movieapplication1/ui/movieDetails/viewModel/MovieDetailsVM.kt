package com.example.movieapplication1.ui.movieDetails.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplication1.data.model.MovieDetail
import com.example.movieapplication1.data.model.Resource
import com.example.movieapplication1.data.repository.ApplicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsVM @Inject constructor(private val repository: ApplicationRepository) :
    ViewModel() {

    var movieDetailsData: MutableLiveData<Resource<MovieDetail>> = MutableLiveData()

    fun getMovieDetails(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getMovieDetails(id)
            movieDetailsData.postValue(response)
        }
    }
}
