package com.example.mobilliumcase.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobilliumcase.base.BaseViewModel
import com.example.mobilliumcase.base.SchedulerProvider
import com.example.mobilliumcase.model.MoviesDataModel
import com.example.mobilliumcase.ui.list.paging.MovieListUseCase
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieDetailViewModel(private var movieId: Int) : BaseViewModel() {

    var useCase= MovieListUseCase()

    private val _movieDetail = MutableLiveData<MoviesDataModel?>()
    val movieDetail: LiveData<MoviesDataModel?> by lazy {
        getMovieDetail()
        _movieDetail
    }

    private fun getMovieDetail() = viewModelScope.launch {
        // Firstly we are posting
        updateProgress(true)
        _movieDetail.postValue(useCase.fetchMovieDetail(movieId).data)
        updateProgress(false)
    }
}