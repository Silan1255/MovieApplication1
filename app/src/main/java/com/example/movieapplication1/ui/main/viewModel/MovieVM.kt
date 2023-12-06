package com.example.movieapplication1.ui.main.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplication1.data.model.Movie
import com.example.movieapplication1.data.model.Resource
import com.example.movieapplication1.data.repository.ApplicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieVM @Inject constructor(private val repository: ApplicationRepository) : ViewModel() {

    var movieForSliderData: MutableLiveData<Resource<Movie>> = MutableLiveData()
    var movieListData: MutableLiveData<Resource<Movie>> = MutableLiveData()

    fun getMovieForSlider() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getMovieForSlider()
            movieForSliderData.postValue(response)
        }
    }

    fun getMovieList(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getMovieList(page)
            movieListData.postValue(response)
        }
    }
}
