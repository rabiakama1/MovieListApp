package com.example.mobilliumcase.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mobilliumcase.api.MovieRepository
import com.example.mobilliumcase.base.BaseViewModel
import com.example.mobilliumcase.base.SchedulerProvider
import com.example.mobilliumcase.model.MoviesDataModel
import com.example.mobilliumcase.ui.list.paging.MovieListPagingSource
import com.example.mobilliumcase.ui.list.paging.MovieListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MovieViewModel() :BaseViewModel() {

    /** Holds now playing movies response */
    private val _nowPlayingMovies by lazy { MutableLiveData<List<MoviesDataModel>>() }
    val nowPlayingMovies: LiveData<List<MoviesDataModel>> by lazy {
        viewModelScope.launch {
            initNowPlayingMovies()
        }
        _nowPlayingMovies
    }

    var useCase = MovieListUseCase()

    val moviesPagingFlowData  : Flow<PagingData<MoviesDataModel>> =
        Pager(PagingConfig(pageSize = 1)) {
            MovieListPagingSource(
                useCase
            )
        }.flow

    suspend fun initNowPlayingMovies() {
       updateProgress(true)
        val movies = useCase.getNowPlayingMovies(1)
        if(movies.isNotEmpty()) {
            _nowPlayingMovies.postValue(movies)
        }
       updateProgress(false)
    }

}