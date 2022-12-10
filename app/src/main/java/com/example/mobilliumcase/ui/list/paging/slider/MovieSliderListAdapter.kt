package com.example.mobilliumcase.ui.list.paging.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.mobilliumcase.BuildConfig
import com.example.mobilliumcase.databinding.MovieSliderItemBinding
import com.example.mobilliumcase.model.MoviesDataModel
import com.smarteist.autoimageslider.SliderViewAdapter

class MovieSliderListAdapter(
    private var imageUrls : ArrayList<MoviesDataModel>,
    private var onMovieClicked: (String) -> Unit
): SliderViewAdapter<MovieSliderListAdapter.MovieSliderVH>() {

    var nowPlayingMovies: ArrayList<MoviesDataModel> = imageUrls

    override fun getCount(): Int {
        return nowPlayingMovies.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): MovieSliderVH  = MovieSliderVH(
        MovieSliderItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false))

    override fun onBindViewHolder(viewHolder: MovieSliderVH?, position: Int) {
        viewHolder?.bindItems(nowPlayingMovies[position])
    }

    fun setNewItems(movies: List<MoviesDataModel>) {
        nowPlayingMovies = arrayListOf()
        nowPlayingMovies.addAll(movies)
    }

    inner class MovieSliderVH(private val binding: MovieSliderItemBinding) : SliderViewAdapter.ViewHolder(binding.root) {
        fun bindItems(movie: MoviesDataModel) {
            binding.movieDescription.text = movie.overview
            binding.movieTitle.text = movie.title
            Glide.with(this.itemView).load(BuildConfig.IMAGE_URL + movie.backdropPath).into(binding.sliderMoviePoster)
            binding.sliderRowCard.setOnClickListener { onMovieClicked(movie.id.toString()) }
        }
    }
}