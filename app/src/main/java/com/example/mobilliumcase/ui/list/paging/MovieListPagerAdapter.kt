package com.example.mobilliumcase.ui.list.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobilliumcase.BuildConfig
import com.example.mobilliumcase.R
import com.example.mobilliumcase.base.BasePagingDataAdapter
import com.example.mobilliumcase.databinding.MovieListItemBinding
import com.example.mobilliumcase.model.MoviesDataModel
import java.text.SimpleDateFormat

class MovieListPagerAdapter(
    private var onMovieClicked: (MoviesDataModel) -> Unit
): BasePagingDataAdapter<MoviesDataModel, MovieListPagerAdapter.VerticalVH>(ITEM_COMPARATOR) {
    override val layoutRes: Int = R.layout.movie_list_item

    override fun onBind(holder: VerticalVH, position: Int) {
        getItem(position)?.let {  items ->
            holder.bindItems(items)
        }
    }

    override fun onCreateVH(parent: ViewGroup, viewType: Int, itemView: View): VerticalVH
            = VerticalVH(MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    inner class VerticalVH(private val binding: MovieListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItems(model: MoviesDataModel) {
            binding.movieName.text = model.title.ifEmpty { "" }
            binding.movieDescription.text = model.overview
            binding.movieReleaseDate.text = formatDate(model.releaseDate)
            Glide.with(this.itemView).load(BuildConfig.IMAGE_URL + model.posterPath).into(binding.imageView)
            // When item clicked to view detail
            binding.verticalMovieListCard.setOnClickListener { onMovieClicked(model) }
        }
    }

    private fun formatDate(modelDate: String): String {
        //Example date : "2014-11-30"
        val parser = SimpleDateFormat("yyyy-MM-dd")
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        return formatter.format(parser.parse(modelDate))
    }

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<MoviesDataModel>() {
            override fun areItemsTheSame(oldItem: MoviesDataModel, newItem: MoviesDataModel) =
                oldItem === newItem

            override fun areContentsTheSame(oldItem: MoviesDataModel, newItem: MoviesDataModel) =
                oldItem.id == newItem.id
        }
    }

}