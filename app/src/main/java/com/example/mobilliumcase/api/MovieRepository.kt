package com.example.mobilliumcase.api

import com.example.mobilliumcase.BuildConfig
import com.example.mobilliumcase.model.MoviesDataModel
import com.example.mobilliumcase.model.MoviesResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MovieRepository {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_TOKEN
    ) : Call<MoviesResponse>

    @GET("movie/upcoming")
    fun getUpComingMovies(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_TOKEN)
    : Call<MoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id")id:Int,
        @Query("api_key")apiKey: String = BuildConfig.API_TOKEN
    ): Response<MoviesDataModel>

}