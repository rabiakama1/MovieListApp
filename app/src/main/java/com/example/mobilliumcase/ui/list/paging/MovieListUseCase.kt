package com.example.mobilliumcase.ui.list.paging

import android.util.Log
import com.example.mobilliumcase.api.ApiClient
import com.example.mobilliumcase.api.BaseRepo
import com.example.mobilliumcase.api.MovieRepository
import com.example.mobilliumcase.api.MovieResource
import com.example.mobilliumcase.model.MoviesDataModel
import com.example.mobilliumcase.model.MoviesResponse
import retrofit2.Call
import retrofit2.Callback
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

import retrofit2.Response

class MovieListUseCase : BaseRepo(){

    private var repository : MovieRepository = ApiClient.getClient().create(MovieRepository::class.java)

    suspend fun getUpComingMovies(position: Int, loadSize: Int): List<MoviesDataModel> {
        return fetchUpComingMovies(position, loadSize)
    }

    private suspend fun fetchUpComingMovies(position: Int, loadSize: Int) = suspendCoroutine<List<MoviesDataModel>>{ continuation ->
        val post = repository.getUpComingMovies(position,loadSize)
        post.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    continuation.resume(response.body()!!.movies)
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.e("fail","onfailure")
            }

        })
    }

    suspend fun getNowPlayingMovies(position: Int): List<MoviesDataModel> {
        return fetchNowPlayingMovies(position)
    }

    private suspend fun fetchNowPlayingMovies(position: Int) = suspendCoroutine<List<MoviesDataModel>>{ continuation ->
        val post = repository.getNowPlayingMovies(position)
        post.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    continuation.resume(response.body()!!.movies)
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.e("fail","onfailure")
            }

        })
    }

    suspend fun fetchMovieDetail(id: Int) : MovieResource<MoviesDataModel> {
        return safeApiCall { repository.getMovieDetail(id) }
    }

}