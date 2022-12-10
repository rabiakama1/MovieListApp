package com.example.mobilliumcase.ui.detail

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mobilliumcase.BuildConfig
import com.example.mobilliumcase.R
import com.example.mobilliumcase.base.BaseFragment
import com.example.mobilliumcase.base.SchedulerProvider
import com.example.mobilliumcase.model.MoviesDataModel
import com.google.android.material.button.MaterialButton
import java.lang.Exception
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MovieDetailFragment : BaseFragment<MovieDetailViewModel>() {
    override fun getLayoutRes(): Int = R.layout.fragment_movie_detail
    override fun setViewModel() = MovieDetailViewModel(args.movieId)

    // get the arguments from the movie list fragment
    private val args: MovieDetailFragmentArgs by navArgs()

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    /** init view */
    private val movieDetailProgress by lazy { view?.findViewById<ProgressBar>(R.id.detail_progress) }
    private val image by lazy { view?.findViewById<ImageView>(R.id.detail_item_image) }
    private val average by lazy { view?.findViewById<TextView>(R.id.detail_item_movie_average) }
    private val releaseDate by lazy { view?.findViewById<TextView>(R.id.detail_item_movie_release_date) }
    private val movieName by lazy { view?.findViewById<TextView>(R.id.detail_item_movie_name) }
    private val description by lazy { view?.findViewById<TextView>(R.id.detail_item_movie_description) }
    private val detailCard by lazy { view?.findViewById<CardView>(R.id.detail_item_list_card_view) }

    override fun init(context: Context) {
        observeLiveData()
        //initClickListener()
        initNavigation()
    }

    //  startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.url)))

    private fun initNavigation() {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            "onBackPressed",
            args.movieId
        )
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(MovieDetailFragmentDirections.actionDetailToMain())
                }
            })
    }

    private fun observeLiveData() {
        viewModel.apply {
            // Observes progress changes
            viewModel.progress.observe(viewLifecycleOwner) { inProgress ->
                movieDetailProgress?.visibility = if (inProgress) View.VISIBLE else View.GONE
            }
            // Observes detail item
            movieDetail.observe(viewLifecycleOwner) {
                setData(it)
            }
            // Observes alert messages
            alertMessage.observe(viewLifecycleOwner) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setData(model: MoviesDataModel?) {
        model.let {
            if (model != null) {
                detailCard?.visibility = View.VISIBLE
                movieName?.text = model.title.ifEmpty { "" }
                releaseDate?.text = prepareDateFormat(model.releaseDate)
                val formatter = DecimalFormat("#.#")
                val averageText = "${formatter.format(model.rating)} / ${"10"}"
                average?.text = averageText
                description?.text = model.overview
                Glide.with(this).load(BuildConfig.IMAGE_URL + model.backdropPath).into(image!!)
            }
        }
    }

    private fun prepareDateFormat(dateValue: String): String {
        //Example date : "2014-11-30"
        val parser = SimpleDateFormat("yyyy-MM-dd")
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        return formatter.format(parser.parse(dateValue))
    }
}